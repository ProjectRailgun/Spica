package co.railgun.spica.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.railgun.spica.ui.common.resources.R
import co.railgun.spica.ui.theme.topAppBarBackground
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun SpicaTopAppBar(
    modifier: Modifier = Modifier,
    titleText: String = stringResource(id = R.string.app_name),
    title: @Composable () -> Unit = {
        Crossfade(targetState = titleText) {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = it,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    },
    backgroundColor: Color = MaterialTheme.colors.topAppBarBackground,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        elevation = elevation,
    )
}

@Composable
fun AppBarIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
        )
    }
}

@Composable
fun AppBarAction(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    AppBarIcon(
        imageVector = imageVector,
        onClick = onClick,
    )
}

@Composable
fun NavigateUpIcon(
    onClick: () -> Unit,
) {
    AppBarIcon(
        imageVector = Icons.Rounded.ArrowBack,
        onClick = onClick,
    )
}
