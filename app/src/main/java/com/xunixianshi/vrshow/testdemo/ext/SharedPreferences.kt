package com.xunixianshi.vrshow.testdemo.ext

import android.content.Context
import android.content.SharedPreferences

/**
 * @ClassName:      SharedPreferences$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/20$ 10:28$
 * @Version:        1.0
 */


fun getSharedPreferences(shardname:String,context: Context):SharedPreferences{
    return context?.getSharedPreferences(shardname, Context.MODE_PRIVATE)
}
//
//fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit){
//    val editor = edit()
//    editor.block()
//    editor.apply()
//}
//
fun SharedPreferences.read(action :SharedPreferences.()->Unit){
        action()
}


