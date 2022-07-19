package co.railgun.spica.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.railgun.spica.data.bangumi.Bangumi
import co.railgun.spica.ui.component.HeightSpacer
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Bangumi(
    modifier: Modifier = Modifier,
    bangumi: Bangumi,
    onclick: () -> Unit,
) {
    Card(
        modifier = run {
            modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp,
                )
                .clickable(onClick = onclick)
        },
    ) {
        Row {
            AsyncImage(
                modifier = run {
                    Modifier
                        .weight(weight = 1f)
                        .fillMaxWidth()
                        .requiredHeight(120.dp)
                },
                model = run {
                    ImageRequest.Builder(LocalContext.current)
                        .placeholder(bangumi.placeholder)
                        .data(bangumi.coverUrl)
                        .crossfade(true)
                        .build()
                },
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
                },
            ) {
                EllipsisText(
                    text = bangumi.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                HeightSpacer(4.dp)
                EllipsisText(
                    text = bangumi.subTitle,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
