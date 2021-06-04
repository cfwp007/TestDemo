package com.xunixianshi.vrshow.testdemo.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.model.ScopeDemoModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.shard_flow_demo_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @ClassName:      ShardflowDemoFragment$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/6/3$ 17:25$
 * @Version:        1.0
 */
@AndroidEntryPoint
class ScopeDemoFragment :Fragment(R.layout.shard_flow_demo_main){

    val mainScope = MainScope()
    val ioScope = MainScope() + Dispatchers.IO


    private val viewModel: ScopeDemoModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showSomeData()

        delayToExecute(3000){
            test_demo.text = "333333333333333"
        }
        startcheckBoolean()
    }

    fun startcheckBoolean() = mainScope.launch {
        activity?.let {
           var boolean =  checkBoolean(it,"demotest")

            MLog.d("checkBoolean------->$boolean")
        }
    }

    fun showSomeData() =mainScope.launch {
        MLog.d("showSomeData--------------")
        showData()
    }


    /**
    * @method
    * @description 运行在子线程上，里面进行一些耗时处理，一段时间后返回处理结果，当且仅当所有这些函数全都返回了结果之后，才进行后续的处理。
    * @date: 2021/6/4 9:34
    * @author: wangp
    * @param
    * @return
    */
    suspend fun showData() = coroutineScope {
            val data = async(Dispatchers.IO) { // <- extension on current scope
//              load some UI data for the Main thread ...
                MLog.d("doSomeWork load some UI data for the Main thread --------------")
                "1111111111"
                "22222222"
             }

           withContext(Dispatchers.Main) {
               MLog.d("doSomeWork--------------")
              val result = data.await()
               test_demo.text = result
               MLog.d("doSomeWork 111--------------$result")
          }
      }

    private suspend fun checkBoolean(context: Context,vararg permissions: String )= suspendCoroutine<Boolean> {
        MLog.d("doSomeWork-------------->$permissions")
        it.resume(true)

        
    }

    /**
    * @method
    * @description launch 用法：延迟duration毫秒后执行
    * @date: 2021/6/4 9:21
    * @author: wangp
    * @param
    * @return
    */
    private fun delayToExecute(duration: Long, execution: () -> Unit) = mainScope.launch {
        delay(duration)
        execution()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}