package com.xunixianshi.vrshow.testdemo.repository

import androidx.annotation.AnyThread
import androidx.lifecycle.*
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.http.Results
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import com.xunixianshi.vrshow.testdemo.obj.defaultObj
import com.xunixianshi.vrshow.testdemo.remote.LivedataRemoteDataSource
import com.xunixianshi.vrshow.testdemo.utils.CacheOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @ClassName:      Mydata$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/2/5$ 11:28$
 * @Version:        1.0
 */

class MyRepository @Inject constructor(livedataRemoteDataSource: LivedataRemoteDataSource) :DataSource{


    override fun getCurrentTime(): LiveData<Long> = liveData {
            while (true) {
                emit(System.currentTimeMillis())
                delay(1000)
            }
        }


     val _cachedData =MutableLiveData<String>()

    override val cachedData: MediatorLiveData<String> = MediatorLiveData()

    override suspend fun fetchNewData() {
        withContext(Dispatchers.Main) {
            _cachedData.value = "Fetching new data..."
            _cachedData.value = simulateNetworkDataFetch()
        }
    }

    override fun getListData(): Flow<List<PersonLive>> {
        val list = mutableListOf<PersonLive>()
        return flow {

            list.addAll(initData())
            emit(list)
        }
    }


    private fun initData() : List<PersonLive>{
        var list  = mutableListOf<PersonLive>()
        for (index in 1..6){
            var personLive = PersonLive(2,"张三", "杭州$index")
            list.add(personLive)
        }
        return list
    }



    private var counter = 0

    private suspend fun simulateNetworkDataFetch(): String = withContext(Dispatchers.IO) {
        delay(1000)
        counter++
        "New data from request #$counter"
    }

    // 缓存细节 获取用户信息
    private var listCache  = CacheOnSuccess(onErrorFallback = { PersonLive(id = 0,name = "张三",address = "深圳")}){

        livedataRemoteDataSource.getUserInfo()
    }

    val dataSource  =  listCache::getOrAwait.asFlow()
        .onStart {

        }.map {
            retult->
            withContext(Dispatchers.Default) {

                retult
            }

        }




    val cachedSuccess: CacheOnSuccess<Int> = CacheOnSuccess(onErrorFallback = { 3 }) {
            //这里可添加网络请求逻辑
           5
      }

    //这可以定义一个 Flow，该 flow 会在被收集时调用 getOrAwait，然后 emit
//    private val customSortFlow = flow { emit(cachedSuccess.getOrAwait()) }

    //此代码会创建一个新的 Flow ,该 flow 调用 getOrAwait 并将结果作为其第一个和唯一的值发出。
    private val customSortFlow = cachedSuccess::getOrAwait.asFlow()
    //如需探索 combine 运算符的工作方式，请修改 customSortFlow，在 onStart 中发出两次数据（需包含长时间延迟），
        //当某个观察器先于其他运算符进行监听时，将会发生转换 onStart
        //因此您可以在网络请求 flow 中使用它来发出 Loading 状态。如下所示：
        .onStart {
            emit(88)
            delay(1500)
        }
          //可以通过从 flow 内部调用常规挂起函数来确保网络和数据库调用的主线程安全性，以及安排多个异步操作。
         //使用挂起函数安排异步工作,Map转换：返回原流中的数据源，此 map 依赖常规挂起函数处理异步工作，因此具有主线程安全性
        .map { result ->
            //do something

           val resultNext  = result.applyMainSafeSort(200)

            resultNext

            MLog.d("customSortFlow result--->$result  resultNext--->$resultNext")
        }
    /**
     * The same sorting function as [applySort], but as a suspend function that can run on any thread
     * (main-safe)
     */
    @AnyThread
    private suspend fun Int.applyMainSafeSort(value: Int) =
        withContext(Dispatchers.Default) {
            value
//            this@applyMainSafeSort.applySort(customSortOrder)
        }

    ///////////////////////////

    /**
     *  TODO 以声明的方式合并多个 flow 两个 flow 都在自己的协程中运行，此为Demo,正式代码需用Flow<List<XXX>>返回监听
     */
    val flow : Flow<Unit> =
        //   fun getPlantsFlow(): Flow<List<Plant>>
        flow {
        for (i in 1..30) {
            delay(100)
            emit(i)
        }
    }
        //通过使用 map、combine 或 mapLatest 等转换，我们能够以声明的方式描述当每个元素在 flow 中移动
        .combine(customSortFlow){ //并发进行 customSortFlow
        //以声明的方式合并多个 flow 两个 flow 都在自己的协程中运行，然后，每当一个
        // flow 生成一个新值时，将使用另一个 flow 中的最新值调用转换。  转换 combine 将为每个要合并的 flow 启动一个协程。这样您便可以用并发形式合并两个 flow。
//        flow 将以一种“公平”的方式合并，这意味着每一个 flow 都有机会生成值（即使其中一个 flow 由紧密循环生成）。
            a,b->

            MLog.d("combine  a----->$a  b----->$b")

    }.flowOn(Dispatchers.Default)

        //在执行 flowOn 之后，将该缓冲区的值发出到 Flow。 运算符 flowOn 会启动新的协程来收集其中的 flow，并引入一个缓冲区写入结果。
        // 可以使用更多运算符控制缓冲区，例如，conflate 表示仅存储缓冲区中生成的最后一个值。
        .conflate()



}


interface DataSource{
    fun getCurrentTime():LiveData<Long>
    val cachedData: MediatorLiveData<String>
    suspend fun fetchNewData()
    fun getListData(): Flow<List<PersonLive>>

}


