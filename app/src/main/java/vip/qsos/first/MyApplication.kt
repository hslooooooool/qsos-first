package vip.qsos.first

import android.app.Application
import android.util.Log
import vip.qsos.utils_exception.GlobalExceptionHelper

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**全局异常捕获处理*/
        GlobalExceptionHelper.init(this, Log.ASSERT, Log.ERROR)
    }

}