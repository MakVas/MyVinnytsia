package com.makvas.myvinnytsia.ui

import androidx.lifecycle.ViewModel
import com.makvas.myvinnytsia.data.LocalPlacesDataProvider
import com.makvas.myvinnytsia.model.Place
import com.makvas.myvinnytsia.model.PlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyVinnytsiaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyVinnytsiaUiState())
    val uiState: StateFlow<MyVinnytsiaUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        val places: Map<PlaceType, List<Place>> =
            LocalPlacesDataProvider.getPlacesData().groupBy { it.placeType }
        _uiState.value =
            MyVinnytsiaUiState(
                places = places,
                currentSelectedPlace = places[PlaceType.Sights]?.get(0)
                    ?: LocalPlacesDataProvider.defaultPlace
            )
    }

    fun updateDetailsScreenStates(place: Place) {
        _uiState.update {
            it.copy(
                currentSelectedPlace = place,
                isShowingHomepage = false
            )
        }
    }

    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                currentSelectedPlace = it.places[it.currentPlaceType]?.get(0)
                    ?: LocalPlacesDataProvider.defaultPlace,
                isShowingHomepage = true
            )
        }
    }

    fun updateCurrentPlaceType(placeType: PlaceType) {
        _uiState.update {
            it.copy(
                currentPlaceType = placeType
            )
        }
    }
}