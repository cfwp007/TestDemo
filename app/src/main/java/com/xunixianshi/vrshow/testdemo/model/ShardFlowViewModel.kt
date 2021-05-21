package com.xunixianshi.vrshow.testdemo.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullAs
import com.xunixianshi.vrshow.testdemo.eventbus.Event
import com.xunixianshi.vrshow.testdemo.eventbus.LocalEventBus
import com.xunixianshi.vrshow.testdemo.repository.ShardFlowRepository
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @ClassName:      ShardFlowViewModel$
 * @Description:     ShardFlowViewModel
 * @Author:         wpeng
 * @CreateDate:     2021/5/21$ 13:55$
 * @Version:        1.0
 */
class ShardFlowViewModel @ViewModelInject constructor(private val shardFlowRepository: ShardFlowRepository) :
    ViewModel() {

    private lateinit var job: Job

    fun startShard() {

        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                LocalEventBus.postEvent(Event(System.currentTimeMillis()))
            }
        }

    }

    fun stopShard() {
        if (job.isActive) {
            job.cancel()
        }

    }
}