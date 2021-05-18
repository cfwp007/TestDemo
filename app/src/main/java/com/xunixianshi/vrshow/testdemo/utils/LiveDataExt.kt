package com.weier.vrshow.ext

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@AnyThread//被注解的元素可以在任意线程被调用
inline fun <reified T> MutableLiveData<T>.postNext(map: (T) -> T) {
    postValue(map(verifyLiveDataNotEmpty()))
}


@MainThread//被注解的元素只能在主线程被调用
inline fun <reified T> MutableLiveData<T>.setNext(map: (T) -> T) {
    value = map(verifyLiveDataNotEmpty())
}

@AnyThread
inline fun <reified T> LiveData<T>.verifyLiveDataNotEmpty(): T {
    return value
            ?: throw NullPointerException("MutableLiveData<${T::class.java}> not contain value.")
}