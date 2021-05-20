package com.xunixianshi.vrshow.testdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun getdata(str: Int){
        lifecycleScope.launch {
            flow {

                emit(str) // Ok
                MLog.d("---flow emit-----Thread name：" + Thread.currentThread().name)

            }.flowOn(Dispatchers.IO)
                .onStart {

                    MLog.d("---flow start-----Thread name：" + Thread.currentThread().name)

                }.onCompletion{
//                        emit(str +1) // onCompletion
                    MLog.d("---flow onCompletion----->" + Thread.currentThread().name)
                }.catch{
                        ex->
                    ex.printStackTrace()
                    MLog.d("---flow catch ----->" + ex.message)

                }.collect{

                    MLog.d("---flow collect----->" + it +  "Thread name:" + Thread.currentThread().name)
                }
        }

    }
}