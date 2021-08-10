package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.weier.vrshow.ext.observe
import com.xunixianshi.vrshow.testdemo.MLog
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.model.NetworkViewModle
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import com.xunixianshi.vrshow.testdemo.room.DBUser
import com.xunixianshi.vrshow.testdemo.utils.Resource
import com.xunixianshi.vrshow.testdemo.utils.Status
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class NetWorlDemo : Fragment(R.layout.fragment_net_worl_demo) {

    private var param1: String? = null
    private var param2: String? = null


    private val viewModel: NetworkViewModle by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.getUsers(),this::getdata)

    }

    fun getdata(data : Resource<List<DBUser>>){

        when(data.status){
            Status.SUCCESS->{
                data.data?.let {
                    it.forEach {
                        MLog.d("data SUCCESS name---------->" + it.name)
                    }

                }
            }
            Status.LOADING->{
                MLog.d("data LOADING--->")
            }
            Status.ERROR->{
                MLog.d("data ERROR--->")
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NetWorlDemo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}