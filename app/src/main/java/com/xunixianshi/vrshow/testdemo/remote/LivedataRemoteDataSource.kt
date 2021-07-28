package com.xunixianshi.vrshow.testdemo.remote

import androidx.lifecycle.LiveData
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.http.Results
import com.xunixianshi.vrshow.testdemo.http.processApiResponse
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import com.xunixianshi.vrshow.testdemo.obj.defaultObj
import com.xunixianshi.vrshow.testdemo.service.ServiceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @ClassName:      LivedataRemoteDataSource$
 * @Description:     这是测试，获取网络数据
 * @Author:         wpeng
 * @CreateDate:     2021/5/19$ 9:19$
 * @Version:        1.0
 */
class LivedataRemoteDataSource @Inject constructor(private val mServiceManager: ServiceManager) {

        suspend fun getUserInfo() = withContext(Dispatchers.Default){
                val params = mutableMapOf<String, String>(
                        "test" to "demo"
                )
              var result = processApiResponse{
                      mServiceManager.apiService.getUser(params)
              }
            when(result){
                is Results.Success ->
                {
                    MLog.d("Success-----------------")
                    result.data
                }

                is Results.Failure ->  {

                    MLog.d("error-----------------------")
                    defaultObj
                }

            }
        }


        suspend fun allPlants(): Results<List<PersonLive>> {
               return processApiResponse(mServiceManager.apiService::getDemo)
        }

    suspend fun allPlantss(): List<PersonLive> = withContext(Dispatchers.Default) {
        val results =   processApiResponse(mServiceManager.apiService::getDemo)
        when(results){
            is Results.Success-> results.data.toList()
            is Results.Failure-> listOf()
        }

    }

}