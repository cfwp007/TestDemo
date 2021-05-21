package com.xunixianshi.vrshow.testdemo.model

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.weier.vrshow.ext.setNext
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.repository.MyRepository
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import com.xunixianshi.vrshow.testdemo.obj.defaultObj
import com.xunixianshi.vrshow.testdemo.utils.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @ClassName:      DetailsViewModel$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/17$ 17:31$
 * @Version:        1.0
 */
class LiveDataViewModel @ViewModelInject constructor(private val dataSource : MyRepository) : ViewModel() {

    val currentTime = dataSource.getCurrentTime()

    private var defaultPars = MutableStateFlow(defaultObj)

    /**
     * 使用 Flow 时，通常会根据需要收集 ViewModel、Repository 或其他数据层中的数据。

    由于 Flow 没有与界面关联，因此您不需要界面观察器来 collect 一个 flow。
    这与 LiveData 有很大不同，LiveData 始终需要运行界面观察器。
    最好不要尝试在您的 ViewModel 中 observe 一个 LiveData，因为它没有合适的观察生命周期。
     */
    init {
        //返回一个流，当原始流发出新值时，将取消对先前transform的计算(mapLatest 会为每个值使用此 map 函数。mapLatest 控制并发)
        defaultPars.mapLatest { personLive->


            MLog.d("Started computing ${personLive.name}")
               delay(200)
             "Computed $personLive.name"
            //可添加网络请求业务逻辑


        }.onEach {

            MLog.d("onEach 每当上面的 flow 发出一个值时，onEach 就会被调用。--->$it")
        }.catch {throwable ->

            MLog.d("catch--->${throwable.message}")

        }
            //创建了一个新协程，并从 flow 中收集每个值
            .launchIn(viewModelScope)


    }

    val currentTimeTransformed = currentTime.switchMap {
        liveData {
            emit(timeStampToTime(it))
        }
    }


    private suspend fun timeStampToTime(timestamp: Long): String {
        delay(500)  // Simulate long operation
        return TimeUtils.millis2String(timestamp)
    }


    fun fetchNewData(){
        viewModelScope.launch {
            dataSource.fetchNewData()

        }
    }

    val flowdemo = dataSource.flow.asLiveData()


    val cachedValue = dataSource.cachedData

    fun startObserVer(){
        dataSource.cachedData.addSource(dataSource._cachedData,Observer{
            dataSource.cachedData.value = it
        })

        //激活MediatorLiveData，observeForever全局监听（observe根据activity生命周期去监听）
        dataSource.cachedData.observeForever({})
    }







    fun stopObserVer(){
        dataSource.cachedData.removeSource(dataSource._cachedData)
    }


    /*
 StateFlow(热流,与LiveData 的行为类似：其只保留最后一个值，并允许您观察对其的更改。)

  是一个状态容器式可观察数据流，可以向其收集器发出当前状态更新和新状态更新。
 还可通过其 value 属性读取当前状态值。如需更新状态并将其发送到数据流，
 请为 MutableStateFlow类的 value 属性分配一个新值。通过这段描述，你会发现它和我们常用的LiveData很相似。
 StateFlow 是热数据流：从数据流收集数据不会触发任何提供方代码。StateFlow 始终处于活跃状态并存于内存中，
 而且只有在垃圾回收根中未涉及对它的其他引用时，它才符合垃圾回收条件。
  */

    val uiListData :LiveData<List<PersonLive>> = defaultPars.flatMapLatest {

        dataSource.getListData()

    }.asLiveData()


    //////////////////////////////////////

    private val stateFlow = MutableStateFlow(defaultObj)


    val personLiveFlow :LiveData<List<PersonLive>> = stateFlow
        //每次原始流发出一个值时，返回一个流，该流切换到[transform]函数产生的新流。
        //  *当原始流发出新值时，由“ transform”块产生的先前流将被取消。
        //通过 flow 的 flatMapLatest 扩展程序，您可以在多个 flow 之间切换。此代码与 LiveData.switchMap 版本几乎完全相同，
        // 唯一的区别是它返回 Flows，而不是 LiveDatas。
        .flatMapLatest {
            personLiveFlow->

        MLog.d("personLiveFlow---->" + personLiveFlow.name)

            //可添加本地数据获取结果需为: Flow<List<PersonLive>>
            /**
             * 例：

            if(personLiveFlow.name =="王五"){
                dataSource.本地数据获取接口()
            }else{
            dataSource.getListData()
            }

             */
        dataSource.getListData()

    }.asLiveData() //asLiveData 运算符会将 Flow 转换为具有可配置超时的 LiveData。与 liveData 构建器一样，
    // 超时将有助于 Flow 在重启后继续运行。如果在超时之前观察到另一个屏幕，则 Flow 不会取消。
//    asLiveData 运算符会将 Flow 转换为具有可配置超时的 LiveData。
//    与 liveData 构建器一样，超时将通过旋转使 flow 保持活动状态，这样您的收集就不会重启。

    /**
     例：
     private val _countState = MutableStateFlow(0)
    val countState: StateFlow<Int> = _countState

    fun incrementCount() {
        _countState.value++
    }

    fun decrementCount() {
        _countState.value--
    }

    lifecycleScope.launchWhenStarted {
        viewModel.countState.collect { value ->
            mBinding.tvCount.text = "$value"
        }
    }*/


    /* val cacheOnSuccessValue :LiveData<Int> = liveData {
         val customSortOrder = cachedSuccess.getOrAwait()

          emit(customSortOrder)
     }*/


    private val _loading = MutableLiveData<Boolean>()

    private val _demodata = MutableLiveData<Int>()
    val demodata: LiveData<Int> = _demodata //流收集时修改此livedata,主线程中 可调用此参数更新UI

    /**
     * @method
     * @description flow链式调用
     * @date: 2021/5/21 16:02
     * @author: wangp
     * @param
     * @return
     */
    fun getDemo(key: String) {
        viewModelScope.launch {
            flow {
                Log.d("Flow", "Emit on ${Thread.currentThread().name}")
                //此处可以添加网络请求操作
                emit(11)
            }.flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                    Log.d("Flow", "onStart on ${Thread.currentThread().name}")
                }.onCompletion {
                    _loading.value = false
                    Log.d("Flow", "onComplete on ${Thread.currentThread().name}")
                }.catch { ex ->
                    ex.printStackTrace()
//                    _toastMsg.setValue(ex.message)
                }.collect {
                    Log.d("Flow", "Collect on ${Thread.currentThread().name}")
                    _demodata.setValue(it)
                }
        }
    }

}


//object LiveDataVMFactory : ViewModelProvider.Factory {
//    private val mydata = MyRepository(Dispatchers.IO)
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        return LiveDataViewModel(mydata) as T
//    }
//}