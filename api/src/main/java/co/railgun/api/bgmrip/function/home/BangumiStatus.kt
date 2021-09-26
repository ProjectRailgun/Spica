package co.railgun.api.bgmrip.function.home

enum class BangumiStatus(
    val value: Int,
) {
    ALL(-1),
    WISH(1),
    COLLECT(2),
    DO(3),
    ON_HOLD(4),
    DROPPED(5),
}
