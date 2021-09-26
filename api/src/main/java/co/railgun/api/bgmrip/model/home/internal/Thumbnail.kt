package co.railgun.api.bgmrip.model.home.internal

import co.railgun.api.bgmrip.model.home.Image

internal interface Thumbnail {
    val thumbnail: String
    val thumbnailColor: String
    val thumbnailImage: Image
}
