package com.xunixianshi.vrshow.testdemo.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEmpty

/**
 * @ClassName:      LocalEventBus$
 * @Description:     本地总栈（共享流使用）
 * @Author:         wpeng
 * @CreateDate:     2021/5/21$ 13:47$
 * @Version:        1.0
 */
object LocalEventBus {
    //共享流使用
    private val localEvents = MutableSharedFlow<Event>()

    val event = localEvents.asSharedFlow()

    suspend fun postEvent(mEvent :Event){

        localEvents.emit(mEvent)
    }
}