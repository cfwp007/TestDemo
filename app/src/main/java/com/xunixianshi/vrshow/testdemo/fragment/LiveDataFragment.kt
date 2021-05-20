package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.weier.vrshow.ext.observe
import com.xunixianshi.vrshow.testdemo.MLog

import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.adapter.LiveDataAdapter
import com.xunixianshi.vrshow.testdemo.model.LiveDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argstv.text = "navArgs : \n name:" + name + " position : "  + position + " isshow :" + isshow


        viewModel.currentTimeTransformed.observe(viewLifecycleOwner){
            newText->  detalesTv.text = newText
        }


        observe(viewModel.cachedValue,this::setMediatorlivedata)

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

        viewModel.flowdemo.observe(viewLifecycleOwner){
            demo->
            flowDemo.text  = demo.toString()
        }

    }




    private fun setMediatorlivedata(statusStr:String){
        mediatorlivedata.text = statusStr
    }

    override fun onDestroy() {
        viewModel.stopObserVer()
        super.onDestroy()
    }

}