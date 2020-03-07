package vip.qsos.first

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable
import qsos.core.exception.GlobalException
import qsos.core.exception.GlobalExceptionHelper
import qsos.core.lib.config.CoreConfig
import qsos.lib.base.base.BaseApplication
import qsos.lib.base.utils.LogUtil
import qsos.lib.base.utils.rx.RxBus

open class Application(
    override var debugARouter: Boolean = true,
    override var debugTimber: Boolean = true
) : BaseApplication(), LifecycleOwner {

    private var disposable: Disposable? = null

    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }

    override fun onCreate() {
        super.onCreate()

        CoreConfig.DEBUG = true
        /**BASE_URL配置*/
        CoreConfig.BASE_URL = "http://192.168.0.1:8080"
        CoreConfig.PROVIDER = "vip.qsos.first.provider"
        /**Timber 日志*/
        LogUtil.open(true, GlobalExceptionHelper.CrashReportingTree())
        /**全局异常捕获处理*/
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHelper)
        disposable = RxBus.toFlow(GlobalExceptionHelper.ExceptionEvent::class.java).subscribe {
            dealGlobalException(it.exception)
        }
    }

    /**TODO 统一处理异常，如重新登录、强制下线、异常反馈、网络检查*/
    private fun dealGlobalException(ex: GlobalException) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        disposable?.dispose()
    }
}