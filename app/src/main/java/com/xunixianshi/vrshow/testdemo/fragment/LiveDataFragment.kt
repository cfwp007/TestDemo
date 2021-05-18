package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.weier.vrshow.ext.observe

import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.model.LiveDataVMFactory
import com.xunixianshi.vrshow.testdemo.model.LiveDataViewModel
import kotlinx.android.synthetic.main.fragment_details.*


/**
* @method  
* @description LiveData
* @date: 2021/5/17 15:52
* @author: wangp
* @param 
* @return 
*/
class LiveDataFragment : Fragment(R.layout.fragment_details) {

    val argsValue : LiveDataFragmentArgs by navArgs()

    private val name :String by lazy { argsValue.name }
    private val position :Int by lazy { argsValue.positon }
    private val isshow :Boolean by lazy { argsValue.isshow }

    private val viewModel:LiveDataViewModel by viewModels { LiveDataVMFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argstv.text = "navArgs : \n name:" + name + " position : "  + position + " isshow :" + isshow

        val textObserver  = Observer<String>{
                newText->
            detalesTv?.let {
                detalesTv.text = newText
            }

        }
        viewModel.currentTimeTransformed.observe(requireActivity(),textObserver)


        observe(viewModel.cachedValue,this::setMediatorlivedata)
        viewModel.startObserVer()

        refresh.setOnClickListener {
            viewModel.fetchNewData()
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