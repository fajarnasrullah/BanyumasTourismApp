package com.jer.banyumastourismapp.presentation.sosmed

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.CalendarContract.Colors
import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import com.jer.banyumastourismapp.ui.theme.OrangeNice

@Composable
fun StoryCard(
    modifier: Modifier = Modifier,
    story: Story,
//    onClick: () -> Unit
) {

    val minimumLineLength = 5

    var expandedState by rememberSaveable { mutableStateOf(false) }
    var showReadMoreButtonState by rememberSaveable { mutableStateOf(false) }
    val maxLines = if (expandedState) 50 else minimumLineLength


    Box (
        modifier = modifier
//            .height(300.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
//            .clickable {
//                onClick()
//            }
        ,
//        onClick = onClick,
//        shape = ShapeDefaults.ExtraSmall,
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,

            ) {


            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){

                Row {
                    if (story.photoUrl == null) {
                        Image(
                            painter = painterResource(id = R.drawable.viewdefault),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(35.dp)
                        )
                    } else {
                        AsyncImage(
                            model = story.photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(35.dp)

                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        story.name?.let {
                            Text(
                                text = it,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Text(
                            text = "Shared for: ${story.destination}",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Text(
                    text = "${story.time?.let { DateUtils.getRelativeTimeSpanString(it) }}" ,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.outline
                )

            }

            Spacer(modifier = Modifier.height(15.dp))

            Column(
                modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
            ) {
                story.message?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Justify,
                        maxLines = maxLines,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground,
                        onTextLayout = { textLayoutResult: TextLayoutResult ->
                            if (textLayoutResult.lineCount > minimumLineLength - 1) {
                                if (textLayoutResult.isLineEllipsized(minimumLineLength - 1)) showReadMoreButtonState =
                                    true
                            }
                        },

                    )
                }
                if (showReadMoreButtonState) {
                    Text(
                        text = if (expandedState) "Read Less" else "Read More",
                        color = MaterialTheme.colorScheme.primaryContainer,

                        modifier = Modifier.clickable {
                            expandedState = !expandedState
                        },

                        fontSize = 12.sp

                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)

            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = OrangeNice,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = story.rating.toString(), fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
                }
            }

            Spacer(modifier = Modifier.height(15.dp))


            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

    data class Sosmed(
        val title: String,
        val author: String,
        val description: String ,
        val imageUrl: String? = null
    )


//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//private fun PrevSosmedCard() {
//    BanyumasTourismAppTheme {
//        StoryCard(story =
//            Story (
//                title = "Huru Hara 5 Hari di Bali",
//                name = "Fajar",
//                message = "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya",
//                category = "Waterfall",
//                destination = "Curug Telu",
//                rating = 4.5,
//                time = 5
//            )
//        ) {
//
//        }
//    }
//
//}