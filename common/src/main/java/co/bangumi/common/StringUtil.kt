package co.bangumi.common

import android.text.TextUtils
import co.bangumi.common.model.Bangumi
import co.bangumi.common.model.Episode
import co.bangumi.common.model.EpisodeDetail
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
            return dayFormatter.format(day * oneDay + 3 * oneDay)
        }

        private fun addPadding(string: String): String {
            return (if (string.length < 2) "0" else "") + string
        }

        fun microsecondFormat(ms: Long): String {
            return addPadding("" + TimeUnit.MILLISECONDS.toMinutes(ms)) +
                    ":" +
                    addPadding("" + (TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))))

        }

        fun getName(bangumi: Bangumi): String {
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

        fun getName(episode: Episode): String {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (TextUtils.isEmpty(episode.name_cn)) episode.name else episode.name_cn
            } else {
                if (TextUtils.isEmpty(episode.name)) episode.name_cn else episode.name
            }
        }

        fun getName(episodeDetail: EpisodeDetail): String {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (TextUtils.isEmpty(episodeDetail.name_cn)) episodeDetail.name else episodeDetail.name_cn
            } else {
                if (TextUtils.isEmpty(episodeDetail.name)) episodeDetail.name_cn else episodeDetail.name
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