package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.eventbus.LocalEventBus
import kotlinx.android.synthetic.main.shard_test_value_fragment.*
import kotlinx.coroutines.flow.collect

/**
 * @ClassName:      ShardTestValueFragment$
 * @Description:     共享流数据展示
 * @Author:         wpeng
 * @CreateDate:     2021/5/21$ 14:05$
 * @Version:        1.0
 */

class ShardTestValueFragment :Fragment(R.layout.shard_test_value_fragment){

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenStarted {
            LocalEventBus.event.collect {
                timeTV.text = it.timestamp.toString()
            }
        }

    }

}