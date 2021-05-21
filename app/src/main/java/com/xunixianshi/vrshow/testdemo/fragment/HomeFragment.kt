package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.xunixianshi.vrshow.testdemo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blank.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_blank) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        livedatademo.setOnClickListener {

            findNavController().navigate(
                HomeFragmentDirections.actionBlankFragmentToDetailsFragment("张三")
                    .setPositon(20)
                    .setIsshow(true))
        }

        shardflow.setOnClickListener {

            findNavController().navigate(R.id.action_blankFragment_to_shardflowFragment)

        }

    }


}