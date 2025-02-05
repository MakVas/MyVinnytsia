package com.makvas.myvinnytsia.data

import com.makvas.myvinnytsia.R
import com.makvas.myvinnytsia.model.Place
import com.makvas.myvinnytsia.model.PlaceType

object LocalPlacesDataProvider {
    val allPlaces = listOf(
        Place(
            id = 0L,
            placeLocation = R.string.place_0_location,
            placeName = R.string.place_0_name,
            placeDescription = R.string.place_0_description,
            placeType = PlaceType.Sights,
            placeImage = R.drawable.photo_0
        ),
        Place(
            id = 1L,
            placeLocation = R.string.place_1_location,
            placeName = R.string.place_1_name,
            placeDescription = R.string.place_1_description,
            placeType = PlaceType.Sights,
            placeImage = R.drawable.photo_1
        ),
        Place(
            id = 2L,
            placeLocation = R.string.place_2_location,
            placeName = R.string.place_2_name,
            placeDescription = R.string.place_2_description,
            placeType = PlaceType.Sights,
            placeImage = R.drawable.photo_2
        ),
        Place(
            id = 3L,
            placeLocation = R.string.place_3_location,
            placeName = R.string.place_3_name,
            placeDescription = R.string.place_3_description,
            placeType = PlaceType.Sights,
            placeImage = R.drawable.photo_3
        ),
        Place(
            id = 4L,
            placeLocation = R.string.place_4_location,
            placeName = R.string.place_4_name,
            placeDescription = R.string.place_4_description,
            placeType = PlaceType.Lodging,
            placeImage = R.drawable.photo_4
        ),
        Place(
            id = 5L,
            placeLocation = R.string.place_5_location,
            placeName = R.string.place_5_name,
            placeDescription = R.string.place_5_description,
            placeType = PlaceType.Lodging,
            placeImage = R.drawable.photo_5
        ),
        Place(
            id = 6L,
            placeLocation = R.string.place_6_location,
            placeName = R.string.place_6_name,
            placeDescription = R.string.place_6_description,
            placeType = PlaceType.Lodging,
            placeImage = R.drawable.photo_6
        ),
        Place(
            id = 7L,
            placeLocation = R.string.place_7_location,
            placeName = R.string.place_7_name,
            placeDescription = R.string.place_7_description,
            placeType = PlaceType.Lodging,
            placeImage = R.drawable.photo_7
        ),
        Place(
            id = 8L,
            placeLocation = R.string.place_8_location,
            placeName = R.string.place_8_name,
            placeDescription = R.string.place_8_description,
            placeType = PlaceType.Taste,
            placeImage = R.drawable.photo_8
        ),
        Place(
            id = 9L,
            placeLocation = R.string.place_9_location,
            placeName = R.string.place_9_name,
            placeDescription = R.string.place_9_description,
            placeType = PlaceType.Taste,
            placeImage = R.drawable.photo_9
        ),
        Place(
            id = 10L,
            placeLocation = R.string.place_10_location,
            placeName = R.string.place_10_name,
            placeDescription = R.string.place_10_description,
            placeType = PlaceType.Taste,
            placeImage = R.drawable.photo_10
        ),
        Place(
            id = 11L,
            placeLocation = R.string.place_11_location,
            placeName = R.string.place_11_name,
            placeDescription = R.string.place_11_description,
            placeType = PlaceType.Taste,
            placeImage = R.drawable.photo_11
        )
    )

    fun get(id: Long): Place? {
        return allPlaces.firstOrNull { it.id == id }
    }

    val defaultPlace = Place(
        id = -1,
        placeType = PlaceType.Sights
    )
}