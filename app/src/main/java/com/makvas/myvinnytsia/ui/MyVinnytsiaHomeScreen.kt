package com.makvas.myvinnytsia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.makvas.myvinnytsia.ui.theme.MyVinnytsiaTheme

@Composable
fun MyVinnytsiaHomeScreen(
    modifier: Modifier = Modifier,
    places: List<Place>,
    onPlaceClick: (Place) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(
            minSize = dimensionResource(id = R.dimen.card_image_height)
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        items(places, key = { place -> place.id }) { place ->
            PlaceListItem(
                place = place,
                onPlaceClick = onPlaceClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
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
            onPlaceClick = {},
        )
    }
}
