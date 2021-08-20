package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.tencent.mmkv.MMKV
import com.weier.vrshow.ext.observe
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.adapter.LiveDataAdapter
import com.xunixianshi.vrshow.testdemo.ext.getSharedPreferences
import com.xunixianshi.vrshow.testdemo.ext.read
import com.xunixianshi.vrshow.testdemo.model.LiveDataViewModel
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import com.xunixianshi.vrshow.testdemo.obj.TestPersen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*


/**
* @method  
* @description LiveData
* @date: 2021/5/17 15:52
* @author: wangp
* @param 
* @return 
*/
@AndroidEntryPoint
class LiveDataFragment : Fragment(R.layout.fragment_details) {

    val argsValue : LiveDataFragmentArgs by navArgs()

    private val name :String by lazy { argsValue.name }
    private val position :Int by lazy { argsValue.positon }
    private val isshow :Boolean by lazy { argsValue.isshow }

    private val viewModel:LiveDataViewModel by viewModels()

    private val mAdapter by lazy { LiveDataAdapter() }

    private var testList = mutableListOf<TestPersen>()

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argstv.text = "navArgs : \n name:" + name + " position : "  + position + " isshow :" + isshow

        testList.add {

        }
        viewModel.currentTimeTransformed.observe(viewLifecycleOwner){ newText->  detalesTv.text = newText
        }

       lifecycleScope.launch {
           viewModel.getPersonLive().collect{

               MLog.d("data--->" + it.name)

           }
       }

        observe(viewModel.cachedValue, this::setMediatorlivedata)

        viewModel.startObserVer()

        refresh.setOnClickListener {
            viewModel.fetchNewData()
        }

        recyclerview.adapter = mAdapter

        viewModel.uiListData.observe(viewLifecycleOwner) { list ->
            mAdapter.addData(list)
        }

//        lifecycleScope.launch(Dispatchers.IO){
//
//            val flow = flow {
//                for (i in 1..30) {
//                    delay(100)
//                    emit(i)
//                }
//            }
//
//            val result = flow.conflate()
//                .onEach{
//
//                    delay(timeMillis = 1000)
//
//                }.toList()
//
//            MLog.d("result--->$result")
//        }

        viewModel.flowdemo.observe(viewLifecycleOwner){ demo->
            flowDemo.text  = demo.toString()
        }

        MMKV_Start()

        MLog.d("test   ")

//  高阶函数练习
//        getSharedPreferences("demo",requireActivity()).open {
//            putString("haha","张三--")
//        }
//
//        getSharedPreferences("demo",requireActivity()).read {
//            var string  = getString("haha","王五")
//            MLog.d("preference--->$string")
//        }
        getSharedPreferences("demo",requireActivity()).edit {
            putString("haha","张三--")
        }

        getSharedPreferences("demo",requireActivity()).read {
            var string  = getString("haha","王五")
            MLog.d("preference--->$string")
        }

        show(6) {
            MLog.d("函数参数类型  :$it")
        }
        thread {
            MLog.d("start thread-----")

            test{
                MLog.d("name---------->" + it.email)
            }
        }

        printString("aaa"){
            MLog.d("printString---------->$it")

        }


    }

/*
        //kotlin高阶函数

//block1 无参数无返回值函数类型
    val block1: () -> Unit
    // block2 无参数返回值为 String 的函数类型
    val block2: () -> String
    // block2 有一个 Int 类型的参数无返回值的函数类型
    val block3: (Int) -> Unit
    // block4 有2个参数返回值为 String 的函数类型
    val block4: (Int, String) -> String*/

    fun test(test:(PersonLive)->Unit){
        test(PersonLive(2,"aaa","上海","test"))
    }



    fun show(index:Int,block:(Int) ->Unit){
        MLog.d("start---")

        block(index)

        MLog.d("end---")

    }

    fun thread(start: Boolean = true,
               isDaemon: Boolean = false,
               contextClassLoader: ClassLoader? = null,
               name: String? = null,
               priority: Int = -1,
               block: () -> Unit
    ) :Thread {

        val thread = object :Thread(){
            override fun run() {
                 block()
            }
        }

        if (isDaemon)
            thread.isDaemon = true
        if (priority > 0)
            thread.priority = priority
        if (name != null)
            thread.name = name
        if (contextClassLoader != null)
            thread.contextClassLoader = contextClassLoader
        if (start)
            thread.start()

        return thread

    }

    fun MMKV_Start(){
       var kv =  MMKV.defaultMMKV();
        kv.encode("bool", true);
        System.out.println("bool: " + kv.decodeBool("bool"));

        kv.encode("int", Integer.MIN_VALUE);
        System.out.println("int: " + kv.decodeInt("int"));

        kv.encode("long", Long.MAX_VALUE);
        System.out.println("long: " + kv.decodeLong("long"));

        kv.encode("float", -3.14f);
        System.out.println("float: " + kv.decodeFloat("float"));

        kv.encode("double", Double.MIN_VALUE);
        System.out.println("double: " + kv.decodeDouble("double"));

        kv.encode("string", "Hello from mmkv");
        System.out.println("string: " + kv.decodeString("string"));

        val bytes = byteArrayOf('m'.toByte(), 'm'.toByte(), 'k'.toByte(), 'v'.toByte())
        kv.encode("bytes", bytes);

        System.out.println("bytes: " + kv.decodeBytes("bytes").toString())



    }

    fun MMKVDeleteOrSelect(){
      var mmkv =   MMKV.defaultMMKV()
        mmkv.removeValueForKey("bool")

        mmkv.removeValuesForKeys(arrayOf("int", "long"))
        System.out.println("allKeys: " + Arrays.toString(mmkv.allKeys()))

        val hasBool: Boolean = mmkv.containsKey("bool") //查询


        //多进程访问
        val kv = MMKV.mmkvWithID("InterProcessKV", MMKV.MULTI_PROCESS_MODE)
        kv.encode("bool", true)
    }

    fun cleanMMKV(){
      var mmkv =   MMKV.defaultMMKV()
//        mmkv.clearAll()
        mmkv.removeValueForKey("bool")
        mmkv.clearMemoryCache()

    }


    fun main() {
        val num1 = 100
        val num2 = 80
        val result1 = num1AndNum2(num1, num2) { n1, n2 ->
            n1 + n2
        }
        val result2 = num1AndNum2(num1, num2) { n1, n2 ->
            n1 - n2
        }
        println("result1:$result1")
        println("result2:$result2")


    }

    inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
        val result = operation(num1, num2)
        return result
    }

    inline fun printString(str:String,block: (String) ->Unit){
        println("printString begain")

        block(str)

        println("printString end")
    }

    inline fun runRunnable(crossinline block: () -> Unit){
        val runnable = Runnable{ //匿名类的实现 block: () -> Unit需添加 crossinline关键字:此关键字相当于契约他用于保证在内关联的
                                // lambda表达式中一定不会使用return关键字,这样冲突就不存在了
            block()
        }
        runnable.run()
    }


    fun StringBuilder.build(block: StringBuilder.() -> Unit):StringBuilder{
        block()
        return this
    }


    private fun setMediatorlivedata(statusStr: String){
        mediatorlivedata.text = statusStr
    }

    override fun onDestroy() {
        viewModel.stopObserVer()
        super.onDestroy()
    }

}