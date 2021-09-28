package co.railgun.spica.api.model.home.internal

import co.railgun.spica.api.model.home.Image

internal interface Thumbnail {
    val thumbnail: String
    val thumbnailColor: String
    val thumbnailImage: Image
}
