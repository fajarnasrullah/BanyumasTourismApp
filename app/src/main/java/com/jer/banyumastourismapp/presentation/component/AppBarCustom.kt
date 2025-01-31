package com.jer.banyumastourismapp.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme

@Composable
fun AppBarCustom(
    modifier: Modifier = Modifier,
    title: String? = null,
    backgroundColor: Color? = null,
    actionIcon: Painter? = null,
    navigateBack: () -> Unit,
    action: (() -> Unit)? = null
) {

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor ?: Color.Transparent)
    ) {
        IconButtonAppBar(
            icon = painterResource(id = R.drawable.backicon),
            onClick = navigateBack
        ) 
        
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }

        if (actionIcon != null && action != null) {
            IconButtonAppBar(
                icon = actionIcon,
                onClick = action
            )
        }
    }

}



@Composable
fun IconButtonAppBar(
    icon: Painter,
    onClick: () -> Unit,
    ) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.onPrimary,
    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(45.dp),
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(25.dp)
            )
        }
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAppBarCustom() {
    BanyumasTourismAppTheme {
        AppBarCustom(
            actionIcon = painterResource(id = R.drawable.bookmarkbordericon),
            action = {},
            navigateBack = {},

        )
    }
}