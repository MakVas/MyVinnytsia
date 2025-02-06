package com.makvas.myvinnytsia.ui

import com.makvas.myvinnytsia.data.LocalPlacesDataProvider
import com.makvas.myvinnytsia.model.Place
import com.makvas.myvinnytsia.model.PlaceType

data class MyVinnytsiaUiState(
    val places: Map<PlaceType, List<Place>> = emptyMap(),
    val currentPlaceType: PlaceType = PlaceType.Sights,
    val currentSelectedPlace: Place = LocalPlacesDataProvider.defaultPlace,
    val isShowingHomepage: Boolean = true
){
    val currentPlaceList: List<Place> by lazy { places[currentPlaceType]!! }
}