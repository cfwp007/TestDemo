package com.xunixianshi.vrshow.testdemo.model

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullAs
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.eventbus.Event
import com.xunixianshi.vrshow.testdemo.eventbus.LocalEventBus
import com.xunixianshi.vrshow.testdemo.repository.ShardFlowRepository
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @ClassName:      ShardFlowViewModel$
 * @Description:     ShardFlowViewModel
 * @Author:         wpeng
 * @CreateDate:     2021/5/21$ 13:55$
 * @Version:        1.0
 */
class ScopeDemoModel @ViewModelInject constructor(private val shardFlowRepository: ShardFlowRepository) :
    ViewModel() {



//    val original: Flow<String> = TODO("get original flow")
//
//    val sharedFlow = original.shareIn(scope, SharingStarted.Lazily)




    fun stopShard() {

    }
}