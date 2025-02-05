package com.makvas.myvinnytsia.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place (
    val id: Long,
    @StringRes val placeLocation: Int = -1,
    @StringRes val placeName: Int = -1,
    @StringRes val placeDescription: Int = -1,
    var placeType: PlaceType,
    @DrawableRes val placeImage: Int = -1
)