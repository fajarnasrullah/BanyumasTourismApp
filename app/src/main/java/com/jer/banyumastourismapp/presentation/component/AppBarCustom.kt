package com.jer.banyumastourismapp.presentation.component

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme

@Composable
fun AppBarCustom(
    modifier: Modifier = Modifier,
    title: String? = null,
    backgroundColor: Color? = null,
    actionVector: ImageVector? = null,
    navigateBack: () -> Unit,
    action: (() -> Unit)? = null
) {

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = backgroundColor ?: Color.Transparent)
            .fillMaxWidth()
    ) {
        IconButtonAppBar(
            imageVector = Icons.Default.ArrowBack,
            onClick = navigateBack
        ) 
        
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }

        if (actionVector != null && action != null) {
            IconButtonAppBar(
                imageVector = actionVector,
                onClick = action
            )
        }
    }

}



@Composable
fun IconButtonAppBar(
    imageVector: ImageVector,
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
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
    }

}

@Preview
@Composable
private fun PreviewAppBarCustom() {
    BanyumasTourismAppTheme {
        AppBarCustom(
            actionVector = Icons.Default.Favorite,
            action = {},
            navigateBack = {},

        )
    }
}