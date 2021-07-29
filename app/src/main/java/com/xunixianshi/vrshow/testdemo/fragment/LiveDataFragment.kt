package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import android.view.View
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
import com.xunixianshi.vrshow.testdemo.model.LiveDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argstv.text = "navArgs : \n name:" + name + " position : "  + position + " isshow :" + isshow


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



    private fun setMediatorlivedata(statusStr: String){
        mediatorlivedata.text = statusStr
    }

    override fun onDestroy() {
        viewModel.stopObserVer()
        super.onDestroy()
    }

}