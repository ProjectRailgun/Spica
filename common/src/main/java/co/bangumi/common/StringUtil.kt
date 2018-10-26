package co.bangumi.common

import android.text.TextUtils
import co.bangumi.common.model.Bangumi
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by roya on 2017/7/20.
 */

class StringUtil {

    companion object {
        private var dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        private const val oneDay = 86400000

        fun dayOfWeek(day: Int): String {
            return co.bangumi.common.StringUtil.Companion.dayFormatter.format(day * co.bangumi.common.StringUtil.Companion.oneDay + 3 * co.bangumi.common.StringUtil.Companion.oneDay)
        }

        private fun addPadding(string: String): String {
            return (if (string.length < 2) "0" else "") + string
        }

        fun microsecondFormat(ms: Long): String {
            return co.bangumi.common.StringUtil.Companion.addPadding("" + TimeUnit.MILLISECONDS.toMinutes(ms)) +
                    ":" +
                    co.bangumi.common.StringUtil.Companion.addPadding("" + (TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))))

        }

        fun mainTitle(bangumi: Bangumi): String {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (!TextUtils.isEmpty(bangumi.name_cn)) {
                    bangumi.name_cn
                } else {
                    bangumi.name
                }
            } else {
                if (!TextUtils.isEmpty(bangumi.name)) {
                    bangumi.name
                } else {
                    bangumi.name_cn
                }
            }
        }

        fun subTitle(bangumi: Bangumi): String {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (!TextUtils.isEmpty(bangumi.name)) {
                    bangumi.name
                } else {
                    bangumi.name_cn
                }
            } else {
                if (!TextUtils.isEmpty(bangumi.name_cn)) {
                    bangumi.name_cn
                } else {
                    bangumi.name
                }
            }
        }

    }
}