package co.bangumi.common

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import com.kaopiz.kprogresshud.KProgressHUD

/**
 * Created by roya on 2017/8/9.
 */

class DisplayUtil {
    companion object {
        fun dp2px(res: Resources, dp: Int): Int {
            return (res.displayMetrics.density * dp).toInt()
        }

        fun px2dp(res: Resources, px: Float): Int {
            return (px / res.displayMetrics.density).toInt()
        }

        fun createHud(context: Context, label: String): KProgressHUD {
            return KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(label)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
        }

        fun createCancellableHud(context: Context, label: String, listener: DialogInterface.OnCancelListener): KProgressHUD {
            return KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(label)
                    .setCancellable(listener)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
        }

        fun createCancellableHud(context: Context, label: String): KProgressHUD {
            return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(label)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
        }
    }
}
