package co.railgun.common.activity

import android.util.Log
import android.widget.Toast
import co.railgun.common.BuildConfig
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.*

/**
 * Created by roya on 2017/5/21.
 */
open class BaseActivity : co.railgun.common.activity.RxLifecycleActivity() {

    private val runningMap by lazy { IdentityHashMap<String, Disposable>() }

    fun showToast(s: String, t: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, s, t).show()
    }

    protected fun <T> Observable<T>.withLifecycle(
        subscribeOn: Scheduler = Schedulers.io(),
        observeOn: Scheduler = AndroidSchedulers.mainThread(),
        untilEvent: ActivityEvent = ActivityEvent.DESTROY
    ): Observable<T> = subscribeOn(subscribeOn)
        .observeOn(observeOn)
        .compose(bindUntilEvent(untilEvent))

    protected fun <T> Observable<T>.onlyRunOneInstance(
        taskId: String,
        displace: Boolean = true
    ): Observable<T> {
        if (runningMap.containsKey(taskId)) {
            if (!displace) {
                return Observable.create { wrapper -> wrapper.onComplete() }
            } else {
                runningMap[taskId]?.dispose()
                runningMap.remove(taskId)
            }
        }

        return Observable.create { wrapper ->
            val obs = subscribe(
                { wrapper.onNext(it!!) },
                {
                    runningMap.remove(taskId)
                    wrapper.onError(it)
                },
                {
                    runningMap.remove(taskId)
                    wrapper.onComplete()
                }
            )
            if (!obs.isDisposed) {
                runningMap[taskId] = obs
            }
        }
    }

    protected fun ignoreErrors(): Consumer<in Throwable> {
        return Consumer {
            if (BuildConfig.DEBUG) Log.w("toastErrors", it)
        }
    }

    protected fun toastErrors(): Consumer<in Throwable> {
        return Consumer {
            if (BuildConfig.DEBUG) Log.w("toastErrors", it)
            var errorMessage = getString(co.railgun.common.R.string.unknown_error)

            if (it is HttpException) {
                val body = it.response()?.errorBody()
                val message = body?.let { it1 ->
                    co.railgun.common.api.ApiClient.converterErrorBody(it1)
                }

                if (message?.message() != null) {
                    errorMessage = message.message().toString()
                }
            }

            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    protected fun toastErrors(e: Throwable) {
        if (BuildConfig.DEBUG) Log.w("toastErrors", e)
        var errorMessage = getString(co.railgun.common.R.string.unknown_error)

        if (e is HttpException) {
            val body = e.response()?.errorBody()
            val message = body?.let { it1 ->
                co.railgun.common.api.ApiClient.converterErrorBody(it1)
            }

            if (message?.message() != null) {
                errorMessage = message.message().toString()
            }
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}
