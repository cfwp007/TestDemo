package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skydoves.whatif.whatIfNotNull
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.model.ShardFlowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shardflow_layout.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShardflowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ShardflowFragment : Fragment(R.layout.fragment_shardflow_layout) {

    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: ShardFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_start.whatIfNotNull {
            it.setOnClickListener {
                viewModel.startShard()
            }

        }

        bt_stop.whatIfNotNull {

            it.setOnClickListener {
                viewModel.stopShard()
            }
        }

    }



}