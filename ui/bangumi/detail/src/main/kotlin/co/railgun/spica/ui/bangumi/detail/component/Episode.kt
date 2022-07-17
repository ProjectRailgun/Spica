package co.railgun.spica.ui.bangumi.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.railgun.spica.data.bangumi.Episode
import co.railgun.spica.ui.component.EllipsisText
import co.railgun.spica.ui.component.HeightSpacer

@Composable
internal fun Episode(
    modifier: Modifier = Modifier,
    number: Int,
    episode: Episode,
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
            Column(
                modifier = run {
                    Modifier
                        .weight(weight = 3f)
                        .fillMaxWidth()
                        .padding(16.dp)
                },
            ) {
                EllipsisText(
                    text = "第 $number 话  ${episode.title}",
                    style = MaterialTheme.typography.subtitle1,
                )
                HeightSpacer(4.dp)
                EllipsisText(
                    text = episode.subTitle,
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
    }
}
