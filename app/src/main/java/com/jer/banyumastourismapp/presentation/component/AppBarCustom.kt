package com.jer.banyumastourismapp.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


    var customColor = MaterialTheme.colorScheme.onBackground
    var customPadding = 0.dp
    var customPadding2 = 0.dp

    if (title != null) {
        customColor = MaterialTheme.colorScheme.onPrimary
        customPadding = 5.dp
        customPadding2 = 15.dp
    }


    Row (
        horizontalArrangement =  Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor ?: Color.Transparent)
            .padding(vertical = customPadding, horizontal = customPadding2)

    ) {
        IconButtonAppBar(
            icon = painterResource(id = R.drawable.backicon),
            onClick = navigateBack,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (title != null) {
            Text(
                text = title,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        if (actionIcon != null && action != null) {
            IconButtonAppBar(
                icon = actionIcon,
                onClick = action,
                color = customColor
            )
        }
    }


}



@Composable
fun IconButtonAppBar(
    icon: Painter,
    color: Color,
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
                tint = color,
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
            backgroundColor = MaterialTheme.colorScheme.onPrimary,
            title = "ABcd efg",
            navigateBack = {},

        )
    }
}