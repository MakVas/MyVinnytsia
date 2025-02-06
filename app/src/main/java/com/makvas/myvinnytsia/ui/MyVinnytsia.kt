package com.makvas.myvinnytsia.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makvas.myvinnytsia.R
import com.makvas.myvinnytsia.model.Place
import com.makvas.myvinnytsia.model.PlaceType
import com.makvas.myvinnytsia.ui.utils.MyVinnytsiaContentType

@Composable
fun MyVinnytsia(
    windowSize: WindowWidthSizeClass,
    viewModel: MyVinnytsiaViewModel = viewModel()
) {
    val myVinnytsiaUiState = viewModel.uiState.collectAsState().value

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium ->
            MyVinnytsiaContentType.LIST_ONLY

        WindowWidthSizeClass.Expanded ->
            MyVinnytsiaContentType.LIST_AND_DETAIL

        else -> MyVinnytsiaContentType.LIST_ONLY
    }

    Scaffold(
        topBar = {
            MyVinnytsiaAppBar(
                contentType = contentType,
                isShowingHomepage = myVinnytsiaUiState.isShowingHomepage,
                onBackPressed = { viewModel.resetHomeScreenStates() }
            )
        }
    ) { innerPadding ->
        MyVinnytsiaMainScreen(
            contentType = contentType,
            selectedPlace = myVinnytsiaUiState.currentSelectedPlace,
            selectedTab = myVinnytsiaUiState.currentPlaceType,
            isShowingHomepage = myVinnytsiaUiState.isShowingHomepage,
            places = myVinnytsiaUiState.currentPlaceList,
            onTabPressed = { placeType: PlaceType ->
                viewModel.updateCurrentPlaceType(placeType = placeType)
                viewModel.resetHomeScreenStates()
            },
            onPlaceClick = { place: Place ->
                viewModel.updateDetailsScreenStates(place = place)
            },
            contentPadding = innerPadding,
            onBackPressed = { viewModel.resetHomeScreenStates() }
        )
    }
}

@Composable
private fun MyVinnytsiaMainScreen(
    modifier: Modifier = Modifier,
    selectedTab: PlaceType,
    selectedPlace: Place,
    isShowingHomepage: Boolean,
    contentType: MyVinnytsiaContentType,
    places: List<Place>,
    onTabPressed: (PlaceType) -> Unit,
    onPlaceClick: (Place) -> Unit,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    if (contentType == MyVinnytsiaContentType.LIST_AND_DETAIL) {
        MyVinnytsiaListAndDetailsScreen(
            selectedTab = selectedTab,
            selectedPlace = selectedPlace,
            places = places,
            onTabPressed = onTabPressed,
            onPlaceClick = onPlaceClick,
            contentPadding = contentPadding,
        )
    } else {
        AnimatedVisibility(
            visible = isShowingHomepage,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            MyVinnytsiaHomeScreen(
                places = places,
                selectedTab = selectedTab,
                onTabPressed = onTabPressed,
                onPlaceClick = onPlaceClick,
                modifier = modifier.padding(contentPadding)
            )
        }
        AnimatedVisibility(
            visible = !isShowingHomepage,
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut()
        ) {
            MyVinnytsiaDetailsScreen(
                place = selectedPlace,
                contentPadding = contentPadding,
                onBackPressed = onBackPressed,
            )
        }
    }
}


@Composable
private fun MyVinnytsiaListAndDetailsScreen(
    modifier: Modifier = Modifier,
    selectedTab: PlaceType,
    selectedPlace: Place,
    places: List<Place>,
    onTabPressed: (PlaceType) -> Unit,
    onPlaceClick: (Place) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(modifier = modifier) {
        MyVinnytsiaHomeScreen(
            contentPadding = PaddingValues(
                bottom = contentPadding.calculateBottomPadding()
            ),
            places = places,
            selectedTab = selectedTab,
            onTabPressed = onTabPressed,
            onPlaceClick = onPlaceClick,
            modifier = Modifier
                .padding(
                    top = contentPadding.calculateTopPadding()
                )
                .weight(2f)

        )

        val activity = LocalContext.current as Activity

        MyVinnytsiaDetailsScreen(
            place = selectedPlace,
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            modifier = Modifier.weight(3f),
            onBackPressed = { activity.finish() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyVinnytsiaAppBar(
    contentType: MyVinnytsiaContentType,
    isShowingHomepage: Boolean,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navigationIcon = {
            if (isShowingHomepage || contentType == MyVinnytsiaContentType.LIST_AND_DETAIL) {
                Image(
                    painter = painterResource(id = R.drawable.gerb),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.app_bar_icon_padding))
                        .size(dimensionResource(id = R.dimen.app_bar_icon_size))
                )
            } else {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}