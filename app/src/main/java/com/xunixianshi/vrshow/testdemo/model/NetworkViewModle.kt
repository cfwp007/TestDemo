package com.xunixianshi.vrshow.testdemo.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.httpdemo.ApiHelperImpl
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import com.xunixianshi.vrshow.testdemo.room.DBUser
import com.xunixianshi.vrshow.testdemo.room.DatabaseHelper
import com.xunixianshi.vrshow.testdemo.room.DatabaseHelperImpl
import com.xunixianshi.vrshow.testdemo.service.ServiceManager
import com.xunixianshi.vrshow.testdemo.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @ClassName:      NetworkViewModle$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 10:46$
 * @Version:        1.0
 */
class NetworkViewModle @ViewModelInject constructor(private val mServiceManager: ServiceManager ,private val dbhelper: DatabaseHelper) :ViewModel(){

    private val users = MutableLiveData<Resource<List<DBUser>>>()

    init {
        fetchdata()
    }
    private fun fetchdata(){
        viewModelScope.launch {

            users.postValue(Resource.loading(null))

            try {
               var dbdata  =  dbhelper.getUsers()

                if(dbdata.isEmpty()){

                    val data = mServiceManager.apiService.getUsers()

                    val usersToInsertInDB = mutableListOf<DBUser>()

                    data.forEach {
                        val persion = DBUser(
                            it.id,
                            it.name,
                            it.email,
                            it.avatar
                        )

                        usersToInsertInDB.add(persion)
                    }

                    dbhelper.insertAll(usersToInsertInDB)
                    users.postValue(Resource.success(usersToInsertInDB))

                    MLog.d("dbdata  数据库中无数据 获取数据 name为：-->${usersToInsertInDB[0].name}")
                }else{
                    MLog.d("dbdata 数据库中有数据：-->${dbdata[0].name}")
                    users.postValue(Resource.success(dbdata))
                }



            }catch (e:Exception){
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<DBUser>>> {
        return users
    }


}