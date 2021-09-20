package co.railgun.common

import co.railgun.common.activity.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions


class PermissionUtil {
    companion object {
        fun checkOrRequestPermission(activity: BaseActivity, permission: String): Boolean {
            var flag = false
            val rxPermissions = RxPermissions(activity)
            rxPermissions
                .request(permission)
                .subscribe {
                    if (it) {
                        flag = true
                    } else {
                        activity.showToast(activity.getString(R.string.permission_denied))
                    }
                }
            return flag
        }
    }
}