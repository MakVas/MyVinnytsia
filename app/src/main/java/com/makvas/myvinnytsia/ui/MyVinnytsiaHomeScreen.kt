package com.makvas.myvinnytsia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makvas.myvinnytsia.R
import com.makvas.myvinnytsia.data.LocalPlacesDataProvider
import com.makvas.myvinnytsia.model.Place
import com.makvas.myvinnytsia.model.PlaceType
import com.makvas.myvinnytsia.ui.theme.MyVinnytsiaTheme

@Composable
fun MyVinnytsiaHomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    selectedTab: PlaceType,
    onTabPressed: (PlaceType) -> Unit,
    places: List<Place>,
    onPlaceClick: (Place) -> Unit,
) {
    Column(modifier = modifier) {
        NavigationTabs(
            selectedTab = selectedTab,
            onTabPressed = onTabPressed,
        )
        LazyVerticalGrid(
            contentPadding = contentPadding,
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                ),
            columns = GridCells.Adaptive(
                minSize = dimensionResource(id = R.dimen.card_image_height)
            ),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            items(places, key = { place -> place.id }) { place ->
                PlaceListItem(
                    place = place,
                    onPlaceClick = onPlaceClick,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_medium))
                        .fillMaxWidth()
                )
            }
            item {
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationTabs(
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
                width = dimensionResource(id = R.dimen.tab_indicator_width),
                modifier = Modifier
                    .tabIndicatorOffset(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_small),
                        horizontal = dimensionResource(id = R.dimen.padding_small)
                    )
                    .clip(MaterialTheme.shapes.extraLarge)
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

@Composable
private fun PlaceListItem(
    place: Place,
    onPlaceClick: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        ),
        onClick = { onPlaceClick(place) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(id = R.dimen.card_height))
        ) {
            Image(
                painter = painterResource(id = place.placeImage),
                contentDescription = stringResource(id = place.placeName),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_small)
                    )
            ) {
                Text(
                    text = stringResource(place.placeLocation),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = stringResource(place.placeName),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )

            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff1f0f0)
@Composable
fun PlaceListItemPreview() {
    MyVinnytsiaTheme {
        PlaceListItem(
            place = LocalPlacesDataProvider.defaultPlace,
            onPlaceClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff1f0f0, device = Devices.PIXEL_7_PRO)
@Composable
fun PlaceGridPreview() {
    MyVinnytsiaTheme {
        MyVinnytsiaHomeScreen(
            places = LocalPlacesDataProvider.getPlacesData(),
            selectedTab = PlaceType.Sights,
            onTabPressed = {},
            onPlaceClick = {},
        )
    }
}

private data class NavigationItemContent(
    val placeType: PlaceType,
    val icon: ImageVector,
    val text: String
)
