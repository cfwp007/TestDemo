package com.xunixianshi.vrshow.testdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.xunixianshi.vrshow.testdemo.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =   inflater.inflate(R.layout.fragment_blank, container, false)

        view.findViewById<TextView>(R.id.hello_home_fragment).setOnClickListener {

            findNavController().navigate(
                HomeFragmentDirections.actionBlankFragmentToDetailsFragment("张三")
                .setPositon(20)
                .setIsshow(true))
        }
        return view
    }


}