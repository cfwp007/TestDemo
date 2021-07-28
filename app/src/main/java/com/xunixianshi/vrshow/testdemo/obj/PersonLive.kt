package com.xunixianshi.vrshow.testdemo.obj

import android.content.LocusId

/**
 * @ClassName:      Person$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/18$ 13:41$
 * @Version:        1.0
 */
data class PersonLive(var id: Int, var name:String, var address:String)

val defaultObj = PersonLive(1,"王久","上海")