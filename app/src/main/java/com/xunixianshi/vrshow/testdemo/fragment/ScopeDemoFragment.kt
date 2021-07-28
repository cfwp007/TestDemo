package com.xunixianshi.vrshow.testdemo.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xunixianshi.vrshow.testdemo.BaseData
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.model.ScopeDemoModel
import com.xunixianshi.vrshow.testdemo.parse.ResultPaser
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


    private var JSON = "{\n" +
            "    \"result\":true,\n" +
            "    \"code\":200,\n" +
            "    \"message\":\"请求成功！\",\n" +
            "    \"data\":{\n" +
            "        \"list\":[\n" +
            "            {\n" +
            "                \"id\":138496,\n" +
            "                \"code\":\"PN0001\",\n" +
            "                \"type\":\"7\",\n" +
            "                \"name\":\"单虚拟件组装\",\n" +
            "                \"isbaremetaloper\":0,\n" +
            "                \"childs\":[\n" +
            "                    {\n" +
            "                        \"id\":138496,\n" +
            "                        \"code\":\"PN0001\",\n" +
            "                        \"type\":\"7\",\n" +
            "                        \"name\":\"单虚拟件组装\",\n" +
            "                        \"isbaremetaloper\":0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\":138496,\n" +
            "                        \"code\":\"PN0001\",\n" +
            "                        \"type\":\"7\",\n" +
            "                        \"name\":\"单虚拟件组装\",\n" +
            "                        \"isbaremetaloper\":0\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\":138508,\n" +
            "                \"code\":\"PN0002\",\n" +
            "                \"type\":\"7\",\n" +
            "                \"name\":\"单虚拟件裸机工序\",\n" +
            "                \"isbaremetaloper\":1,\n" +
            "                \"childs\":[\n" +
            "\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\":138557,\n" +
            "                \"code\":\"PN0014\",\n" +
            "                \"type\":\"1\",\n" +
            "                \"name\":\"普通作业\",\n" +
            "                \"isbaremetaloper\":0,\n" +
            "                \"childs\":[\n" +
            "\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\":138558,\n" +
            "                \"code\":\"PN0015\",\n" +
            "                \"type\":\"1\",\n" +
            "                \"name\":\"普通作业裸机\",\n" +
            "                \"isbaremetaloper\":1,\n" +
            "                \"childs\":[\n" +
            "\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\":138559,\n" +
            "                \"code\":\"PN0016\",\n" +
            "                \"type\":\"5\",\n" +
            "                \"name\":\"卡板\",\n" +
            "                \"isbaremetaloper\":1,\n" +
            "                \"childs\":[\n" +
            "\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\":138560,\n" +
            "                \"code\":\"PN0017\",\n" +
            "                \"type\":\"5\",\n" +
            "                \"name\":\"检验作业\",\n" +
            "                \"isbaremetaloper\":0,\n" +
            "                \"childs\":[\n" +
            "\n" +
            "                ]\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"timespan\":{\n" +
            "        \"start\":\"2021-06-30 16:37:38.231\",\n" +
            "        \"stop\":\"2021-06-30 16:37:38.236\",\n" +
            "        \"span\":6\n" +
            "    },\n" +
            "    \"cache\":false\n" +
            "}"

    private val viewModel: ScopeDemoModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showSomeData()

        delayToExecute(3000){
            test_demo.text = "333333333333333"
        }
        startcheckBoolean()


       var mBaseData = ResultPaser.paserObject(JSON, BaseData::class.java)
       var childs =  mBaseData.data.list[0].childs

        childs.forEach {
            MLog.d("name---------->" + it.name)

        }

    }

    fun startcheckBoolean() = mainScope.launch {
        activity?.let {
           var boolean =  checkBoolean(it,"demotest","demotest11111111111111")

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
    //vararg 可变数组 [数组:array]
    private suspend fun checkBoolean(context: Context,vararg permissions: String )= suspendCoroutine<Boolean> {

        permissions.forEach {
            MLog.d("doSomeWork-------------->$it")
        }
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