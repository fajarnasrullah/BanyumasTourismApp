package com.jer.banyumastourismapp.presentation.ticket

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeDefaults.Spacing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.Transformation
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@Composable
fun TicketScreen(modifier: Modifier = Modifier,
//                 viewModel: TicketViewModel,
                 ) {

//    val shape = TicketShape()

//    val userData by viewModel.userData.collectAsState()
//    val ticket by viewModel.ticket.collectAsState()


    LaunchedEffect(Unit) {
//        viewModel.getUserData()
//        viewModel.getTicket(userData?.uid ?: "")
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 50.dp)

    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) {

//                if (ticket?.image != null) {
//                    AsyncImage(
//                        model = ticket?.image,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .clip(MaterialTheme.shapes.medium)
//                            .height(300.dp)
//                            .fillMaxWidth()
//                    )
//                } else {
                Image(
                    painter = painterResource(id = R.drawable.viewdefault),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
//                }

                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
//                ticket?.category?.let { Text(text = it, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimaryContainer) }
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ){
                        Text(
                            text = "Waterfall",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }


                    Spacer(modifier = Modifier.height(15.dp))

//                ticket?.title?.let {
//                    Text(text = it, fontSize = 30.sp, color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Bold)
//                }
                    Text(
                        text = "Baturraden Adventure",
                        fontSize = 24.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(15.dp))

                    Row() {

                        //date
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Date",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

//                        ticket?.date?.let { Text(text = it, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground) }
                            Text(
                                text = "25/05/2025",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        //qty
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = "Qty",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

//                        ticket?.qty?.let { Text(text = it.toString(), fontSize = 16.sp,
//                            fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground) }

                            Text(
                                text = "1",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )


                        }

                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row() {

                        //name
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = "Name",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

//                        ticket?.name?.let { Text(text = it, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground) }
                            Text(
                                text = "Muhammad Fajar Nasrullah",
                                fontSize = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        //id
                        Column(

                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = "ID",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

//                        ticket?.id?.let { Text(text = "BMT-00$it ", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground) }
                            Text(
                                text = "BMT-0053 ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )


                        }

                    }


                }

                Spacer(modifier = Modifier.height(30.dp))

                DashLineSpacer(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)
                ) {
                    if(BarcodeType.CODE_128.isValueValid("https://github.com/fajarnasrullah")) {
                        Barcode(
                            type = BarcodeType.CODE_128,
                            value = "https://github.com/fajarnasrullah",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        )
                    }
                    if (!BarcodeType.CODE_128.isValueValid("https://github.com/fajarnasrullah")) {
                        Text("this code is not compatible")
                    }
                }

            }





        }


    }
}


@Composable
fun DashLineSpacer(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primaryContainer
    val colorLine = MaterialTheme.colorScheme.onSurfaceVariant
    androidx.compose.foundation.Canvas(modifier.height(10.dp)) {
        val dashLength = 4.dp.toPx()
        val gapLength = 4.dp.toPx()
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        val halfHeight = size.height / 2f
        drawLine(
            color = colorLine,
            pathEffect = pathEffect,
            strokeWidth = 3.dp.toPx(),
            start = Offset(0f, halfHeight),
            end = Offset(size.width, halfHeight)
        )
        drawCircle(
            color = color,
            radius = 50f,
            center = Offset(0f, halfHeight)
        )

        drawCircle(
            color = color,
            radius = 50f,
            center = Offset(size.width, halfHeight)
        )
    }
}

    class ForwardingPainter(
        private val painter: Painter,
        private var colorFilter: ColorFilter?
    ) : Painter() {
        override val intrinsicSize
            get() = painter.intrinsicSize

        override fun DrawScope.onDraw() {
            with(painter) {
                draw(size, DefaultAlpha, colorFilter)
            }
        }
    }


    fun forwardingPainter(
        painter: Painter,
        colorFilter: ColorFilter? = null,
    ): Painter = ForwardingPainter(painter, colorFilter)

    @Composable
    fun RemoteImage(
        modifier: Modifier = Modifier,
        imageUrl: String,
        @StringRes contentDescription: Int,
        @DrawableRes placeHolderImage: Int = R.drawable.picturedummy,
        @DrawableRes errorImage: Int = R.drawable.picturedummy,
        transformations: List<Transformation> = emptyList(),
        contentScale: ContentScale = ContentScale.FillWidth,
        colorFilter: ColorFilter? = null
    ) {
        AsyncImage(
            modifier = modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .transformations(transformations = transformations)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build(),
            contentScale = contentScale,
            contentDescription = stringResource(id = contentDescription),
            placeholder = forwardingPainter(
                painter = painterResource(id = placeHolderImage),
                colorFilter = ColorFilter.tint(color = Color(0xFF0EA8B9))
            ),
            error = forwardingPainter(
                painter = painterResource(id = errorImage),
                colorFilter = ColorFilter.tint(color = Color(0xFFBA1A1A))
            ),
            colorFilter = colorFilter
        )
    }

//private val TICKET_INFO = TicketCardState.Content(
//    order = "",
//    name = "Sample Event",
//    attendee = "Alfredo Distéfano",
//    image = "",
//    type = "General Admission",
//    index = 7,
//    total = 19,
//    assignedSeat = SeatAssignment.Assigned("Row 25, Seat B"),
//    barcode = BarcodeState.Missing,
//)
//
//@Composable
//internal fun TicketCard(
//    modifier: Modifier = Modifier,
//    state: TicketCardState,
//) {
//    val shape = RoundedCornerShape(12.dp)
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .clip(shape)
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Column {
//            RemoteImage(
//                modifier = Modifier
//                    .height(180.dp)
//                    .fillMaxWidth()
//                    .loading(),
//                imageUrl = state.image,
//                contentDescription = null,
//                contentScale = ContentScale.Crop
//            )
//            Column(
//                modifier = Modifier
//                    .padding(15.dp)
//            ) {
//                Image(
//                    modifier = Modifier
//                        .height(16.dp)
//                        .loading(state),
//                    painter = painterResource(R.drawable.eventbrite_logo),
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                    colorFilter = ColorFilter.tint(color = Theme.colors.primaryBrand)
//                )
//                Spacer(modifier = Modifier.size(15.dp))
//                HeadingSmallText(text = state.name)
//            }
//            DashLineSpacer(modifier = Modifier.fillMaxWidth())
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(Spacing.normalLarge),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier.weight(1f),
//                    verticalArrangement = spacedBy(Spacing.small)
//                ) {
//                    BodyMediumBoldText(
//                        modifier = Modifier.loading(state),
//                        text = state.attendee
//                    )
//                    if (state.type.isNotBlank()) {
//                        BodySmallText(
//                            modifier = Modifier.loading(state),
//                            text = state.type
//                        )
//                    }
//                    state.assignedSeat.onAssigned { seat ->
//                        BodySmallText(text = seat)
//                    }
//                }
//                Spacer(modifier = Modifier.size(Spacing.normalLarge))
//                HeadingMediumText(
//                    modifier = Modifier
//                        .padding(Spacing.xSmall)
//                        .loading(state),
//                    text = stringResource(
//                        id = R.string.ticket_details_barcode_index_simplified,
//                        state.index,
//                        state.total
//                    )
//                )
//            }
//        }
//
//    }
//}




    class TicketShape(
        private val cornerRadius: Dp = 16.dp,
        private val cutoutRadius: Dp = 16.dp,
        private val cutoutHeight: Float = 1.0f
    ) : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val (width, height) = size
            val cornerRadius = this.cornerRadius.value
            val cornerRadiusSize = cornerRadius * 2
            val cutoutRadius = this.cutoutRadius.value
            val cutoutRadiusSize = cutoutRadius * 2

            val cutoutHeight = height * this.cutoutHeight

            val shape = Path()
            shape.moveTo(0f, cornerRadius)
            shape.arcTo(Rect(0f, 0f, cornerRadiusSize, cornerRadiusSize), 180f, 90f, false)
            shape.arcTo(
                Rect(width - cornerRadiusSize, 0f, width, cornerRadiusSize),
                270f,
                90f,
                false
            )
            shape.arcTo(
                Rect(
                    width - cutoutRadius,
                    cutoutHeight,
                    width + cutoutRadius,
                    cutoutHeight + cutoutRadiusSize
                ),
                270f,
                -180f,
                false
            )
            shape.arcTo(
                Rect(width - cornerRadiusSize, height - cornerRadiusSize, width, height),
                0f,
                90f,
                false
            )
            shape.arcTo(
                Rect(0f, height - cornerRadiusSize, cornerRadiusSize, height),
                90f,
                90f,
                false
            )
            shape.arcTo(
                Rect(
                    -cutoutRadius,
                    cutoutHeight,
                    cutoutRadius,
                    cutoutHeight + cutoutRadiusSize
                ), 90f, -180f, false
            )
            shape.lineTo(0f, cornerRadius)
            shape.close()
            return Outline.Generic(shape)
        }


    }

    @Preview(showBackground = true)
    @Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun PrevTicketScreen() {
        BanyumasTourismAppTheme {
            TicketScreen()
        }
    }
