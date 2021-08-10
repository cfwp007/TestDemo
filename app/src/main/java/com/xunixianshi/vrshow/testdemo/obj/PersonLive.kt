package com.xunixianshi.vrshow.testdemo.obj

import com.google.gson.annotations.SerializedName

/**
 * @ClassName:      Person$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/18$ 13:41$
 * @Version:        1.0
 */
data class PersonLive(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)

val defaultObj = PersonLive(1, "王久", "77777@qq.com","asqqw")