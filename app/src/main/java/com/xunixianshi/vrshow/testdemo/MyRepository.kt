package com.xunixianshi.vrshow.testdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @ClassName:      Mydata$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/2/5$ 11:28$
 * @Version:        1.0
 */

class MyRepository(private val ioDispatcher: CoroutineDispatcher) :DataSource{


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

    private var counter = 0

    private suspend fun simulateNetworkDataFetch(): String = withContext(ioDispatcher) {
        delay(1000)
        counter++
        "New data from request #$counter"
    }

}

interface DataSource{
    fun getCurrentTime():LiveData<Long>
    val cachedData: MediatorLiveData<String>
    suspend fun fetchNewData()
}


