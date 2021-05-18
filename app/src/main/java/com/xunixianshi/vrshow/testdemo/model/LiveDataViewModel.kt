package com.xunixianshi.vrshow.testdemo.model

import androidx.lifecycle.*
import com.weier.vrshow.ext.setNext
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.MyRepository
import com.xunixianshi.vrshow.testdemo.utils.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName:      DetailsViewModel$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/17$ 17:31$
 * @Version:        1.0
 */
class LiveDataViewModel(private val dataSource : MyRepository) : ViewModel() {

    val currentTime = dataSource.getCurrentTime()

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



}


object LiveDataVMFactory : ViewModelProvider.Factory {
    private val mydata = MyRepository(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LiveDataViewModel(mydata) as T
    }
}