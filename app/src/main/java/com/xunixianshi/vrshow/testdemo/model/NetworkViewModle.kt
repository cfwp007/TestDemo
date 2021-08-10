package com.xunixianshi.vrshow.testdemo.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xunixianshi.vrshow.testdemo.httpdemo.ApiHelperImpl
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
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
class NetworkViewModle @ViewModelInject constructor(private val mServiceManager: ServiceManager) :ViewModel(){

    private val users = MutableLiveData<Resource<List<PersonLive>>>()

    init {
        fetchdata()
    }
    private fun fetchdata(){
        viewModelScope.launch {
            try {
                users.postValue(Resource.loading(null))
                val data = mServiceManager.apiService.getUsers()
                users.postValue(Resource.success(data))
            }catch (e:Exception){
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<PersonLive>>> {
        return users
    }


}