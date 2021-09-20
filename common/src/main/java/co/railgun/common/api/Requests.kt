package co.railgun.common.api

/**
 * Created by roya on 2017/5/22.
 */
data class LoginRequest(val name: String, val password: String, val remmember: Boolean)

const val FavoriteStatus_DEF = 0
const val FavoriteStatus_WISH = 1
const val FavoriteStatus_WATCHED = 2
const val FavoriteStatus_WATCHING = 3
const val FavoriteStatus_PAUSE = 4
const val FavoriteStatus_ABANDONED = 5

data class FavoriteChangeRequest(val status: Int)

data class HistoryChangeItem(val bangumi_id: String, val episode_id: String, val last_watch_time: Long, val last_watch_position: Long, val percentage: Float, val is_finished: Boolean)

data class HistoryChangeRequest(val records: List<HistoryChangeItem>)