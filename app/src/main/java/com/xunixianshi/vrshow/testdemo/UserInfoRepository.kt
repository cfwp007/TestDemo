package com.xunixianshi.vrshow.testdemo

import android.content.SharedPreferences
import com.xunixianshi.vrshow.testdemo.utils.SingletonHolderSingleArg
import com.xunixianshi.vrshow.testdemo.utils.boolean
import com.xunixianshi.vrshow.testdemo.utils.string

class UserInfoRepository(prefs: SharedPreferences) {

    var accessToken: String by prefs.string("user_access_token", "")

    var username by prefs.string("username", "")

    var password by prefs.string("password", "")

    var isAutoLogin: Boolean by prefs.boolean("auto_login", true)

    companion object :
            SingletonHolderSingleArg<UserInfoRepository, SharedPreferences>(::UserInfoRepository)
}