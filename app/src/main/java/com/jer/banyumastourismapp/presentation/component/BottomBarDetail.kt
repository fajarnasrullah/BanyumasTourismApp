package com.jer.banyumastourismapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.presentation.detaildestination.DetailDestination

@Composable
fun BottomBarDetail(
    modifier: Modifier = Modifier,
    headline: String? = null,
    price: String,
    textButton: String,
    onClick: () -> Unit,
    isActiveButton: Boolean,
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RectangleShape,
    ) {

        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.priceicon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (headline != null) {
                        Text(text = headline, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                    }

                    Text(
                        text = price,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }

            Button(
                enabled = isActiveButton,
                modifier = Modifier.size(width = 130.dp, height = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = MaterialTheme.shapes.medium,
                onClick = { onClick() },

                ) {
                Text(
                    text = textButton,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.background
                )
            }

        }

    }


}