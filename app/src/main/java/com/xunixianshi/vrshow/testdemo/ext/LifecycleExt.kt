package com.xunixianshi.vrshow.testdemo.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
* @method
* @description 根据生命周期监听值变化
* @date: 2021/5/18 10:22
* @author: wangp
* @param 
* @return 
*/
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (t: T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t ->
            observer(t)
        }
    }
    )
}
/**
* @method
* @description 全局监听，不根据生命周期
* @date: 2021/5/18 10:22
* @author: wangp
* @param
* @return
*/
fun <T> observeForever(liveData: LiveData<T>, observer: (t: T) -> Unit) {
    liveData.observeForever( Observer {
        it?.let { t ->
            observer(t)
        }
    }
    )
}
