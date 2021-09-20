package co.railgun.common

import co.railgun.common.model.Bangumi
import co.railgun.common.model.Episode
import co.railgun.common.model.EpisodeDetail
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by roya on 2017/7/20.
 */
object StringUtil {

    private var dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
    private const val oneDay = 86400000

    fun dayOfWeek(day: Int): String {
        return dayFormatter.format(day * oneDay + 3 * oneDay)
    }

    private fun addPadding(string: String): String {
        return (if (string.length < 2) "0" else "") + string
    }

    fun microsecondFormat(ms: Long): String =
        addPadding("" + TimeUnit.MILLISECONDS.toMinutes(ms)) +
                ":" +
                addPadding(
                    "" + (TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)))
                )

    fun getName(bangumi: Bangumi): String = when {
        isDisplayLanguageChinese -> when {
            bangumi.name_cn.isNotEmpty() -> bangumi.name_cn
            else -> bangumi.name
        }
        else -> when {
            bangumi.name.isNotEmpty() -> bangumi.name
            else -> bangumi.name_cn
        }
    }

    fun getName(episode: Episode): String = when {
        isDisplayLanguageChinese -> when {
            episode.name_cn.isEmpty() -> episode.name
            else -> episode.name_cn
        }
        else -> when {
            episode.name.isEmpty() -> episode.name_cn
            else -> episode.name
        }
    }

    fun getName(episodeDetail: EpisodeDetail): String = when {
        isDisplayLanguageChinese -> when {
            episodeDetail.name_cn.isEmpty() -> episodeDetail.name
            else -> episodeDetail.name_cn
        }
        else -> when {
            episodeDetail.name.isEmpty() -> episodeDetail.name_cn
            else -> episodeDetail.name
        }
    }

    fun subTitle(bangumi: Bangumi): String = when {
        isDisplayLanguageChinese -> when {
            bangumi.name.isNotEmpty() -> bangumi.name
            else -> bangumi.name_cn
        }
        else -> when {
            bangumi.name_cn.isNotEmpty() -> bangumi.name_cn
            else -> bangumi.name
        }
    }

    private val isDisplayLanguageChinese: Boolean
        get() = Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage
}
