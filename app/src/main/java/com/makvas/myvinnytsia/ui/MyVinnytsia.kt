package com.makvas.myvinnytsia.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    if (contentType == MyVinnytsiaContentType.LIST_AND_DETAIL) {
        //TODO: Implement MyVinnytsiaListAndDetailsScreen
    } else {
        AnimatedVisibility(
            visible = isShowingHomepage,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column(modifier.padding(contentPadding)) {
                NavigationTabs(
                    selectedTab = selectedTab,
                    onTabPressed = onTabPressed,
                )
                MyVinnytsiaHomeScreen(
                    places = places,
                    onPlaceClick = onPlaceClick,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium),
                    )
                )
            }
        }
        AnimatedVisibility(
            visible = !isShowingHomepage,
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut()
        ) {
            MyVinnytsiaDetailsScreen(
                place = selectedPlace,
                contentPadding = contentPadding,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTabs(
    selectedTab: PlaceType,
    onTabPressed: ((PlaceType) -> Unit),
    modifier: Modifier = Modifier
) {
    val tabsItemContentList = listOf(
        NavigationItemContent(
            placeType = PlaceType.Sights,
            icon = Icons.Default.Place,
            text = stringResource(id = R.string.tab_sights)
        ),
        NavigationItemContent(
            placeType = PlaceType.Lodging,
            icon = Icons.Default.Hotel,
            text = stringResource(id = R.string.tab_lodging)
        ),
        NavigationItemContent(
            placeType = PlaceType.Taste,
            icon = Icons.Default.Restaurant,
            text = stringResource(id = R.string.tab_taste)
        ),
    )

    val color = when (selectedTab) {
        PlaceType.Sights -> MaterialTheme.colorScheme.primary
        PlaceType.Lodging -> MaterialTheme.colorScheme.secondary
        PlaceType.Taste -> MaterialTheme.colorScheme.tertiary
    }

    val tabIndex = tabsItemContentList.indexOfFirst { it.placeType == selectedTab }

    PrimaryTabRow(
        selectedTabIndex = tabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    selectedTabIndex = tabIndex,
                    matchContentSize = true
                ),
                shape = RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.tab_indicator_radius),
                    topEnd = dimensionResource(id = R.dimen.tab_indicator_radius),
                ),
                color = color,
            )
        },
        modifier = modifier
    ) {
        tabsItemContentList.forEachIndexed { _, itemContent ->
            TabItem(
                selected = selectedTab == itemContent.placeType,
                itemContent = itemContent,
                onTabPressed = onTabPressed,
                selectedContentColor = color,
            )
        }
    }

}

@Composable
private fun TabItem(
    selected: Boolean,
    onTabPressed: ((PlaceType) -> Unit),
    itemContent: NavigationItemContent,
    selectedContentColor: Color,
    modifier: Modifier = Modifier,
) {
    Tab(
        selected = selected,
        onClick = { onTabPressed(itemContent.placeType) },
        icon = {
            Icon(
                imageVector = itemContent.icon,
                contentDescription = itemContent.text
            )
        },
        text = {
            Text(
                text = itemContent.text
            )
        },
        selectedContentColor = selectedContentColor,
        unselectedContentColor = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyVinnytsiaAppBar(
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
            if (isShowingHomepage) {
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

private data class NavigationItemContent(
    val placeType: PlaceType,
    val icon: ImageVector,
    val text: String
)