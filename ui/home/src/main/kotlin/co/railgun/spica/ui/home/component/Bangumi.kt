package co.railgun.spica.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.railgun.spica.data.bangumi.Bangumi
import co.railgun.spica.ui.component.HeightSpacer
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun Bangumi(
    modifier: Modifier = Modifier,
    bangumi: Bangumi,
    onclick: () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp,
            )
            .clickable(onClick = onclick),
    ) {
        Row {
            Image(
                modifier = run {
                    Modifier
                        .weight(weight = 1f)
                        .fillMaxWidth()
                        .requiredHeight(120.dp)
                },
                painter = rememberImagePainter(
                    data = bangumi.coverUrl,
                    builder = {
                        crossfade(durationMillis = 300)
                    }
                ),
                contentDescription = "Cover",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = run {
                    Modifier
                        .weight(weight = 3f)
                        .fillMaxWidth()
                        .padding(16.dp)
                }
            ) {
                EllipsisText(
                    text = bangumi.title,
                    style = MaterialTheme.typography.subtitle1,
                )
                HeightSpacer(4.dp)
                EllipsisText(
                    text = bangumi.subTitle,
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
    }
}
