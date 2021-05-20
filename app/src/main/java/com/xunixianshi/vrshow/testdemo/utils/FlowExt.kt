package com.xunixianshi.vrshow.testdemo.utils

import kotlinx.coroutines.flow.StateFlow

/**
 * @ClassName:      FlowExt$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/20$ 11:38$
 * @Version:        1.0
 */
fun <T> loadDataFor(source: StateFlow<T>, block: suspend (T) -> Unit) {


}