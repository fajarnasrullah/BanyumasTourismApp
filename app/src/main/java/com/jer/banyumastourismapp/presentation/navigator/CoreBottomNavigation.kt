package com.jer.banyumastourismapp.presentation.navigator

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jer.banyumastourismapp.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select

//
//@Composable
//fun GlassmorphicBottomNav(
//    hazeState: HazeState
//) {
//
//    var selectedItemIndex by remember { mutableIntStateOf(1) }
//
//    Box(
//        modifier = Modifier
//            .padding(vertical = 24.dp, horizontal = 64.dp)
//            .fillMaxWidth()
//            .height(64.dp)
//            .hazeChild(state = hazeState, shape = CircleShape)
//            .border(
//                width = Dp.Hairline,
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color.White.copy(alpha = .8f),
//                        Color.White.copy(alpha = .2f),
//                    ),
//                ),
//                shape = CircleShape
//            )
//    ) {
//
//        CoreBottomNavigation(
//            items = bottomNavigationItems,
//            selected = selectedItemIndex,
//            onItemClick = {
//                selectedItemIndex = it
//            },
//        )
//
//
//
//        val animatedSelectedTabIndex by animateFloatAsState(
//            targetValue = selectedItemIndex.toFloat(), label = "animatedSelectedTabIndex",
//            animationSpec = spring(
//                stiffness = Spring.StiffnessLow,
//                dampingRatio = Spring.DampingRatioLowBouncy,
//            )
//        )
//
//        val animatedColor by animateColorAsState(
//            targetValue = Color(0xFF0EA8B9),
//            label = "animatedColor",
//            animationSpec = spring(
//                stiffness = Spring.StiffnessLow,
//            )
//        )
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//                .blur(radius = 50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
//        ) {
//            val itemWidth = size.width / bottomNavigationItems.size
//            drawCircle(
//                color = animatedColor.copy(alpha = .6f),
//                radius = size.height / 2,
//                center = Offset(
//                    (itemWidth * animatedSelectedTabIndex) + itemWidth / 2,
//                    size.height / 2
//                )
//            )
//        }
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//        ) {
//            val path = Path().apply {
//                addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
//            }
//            val length = PathMeasure().apply { setPath(path, false) }.length
//
//            val tabWidth = size.width / bottomNavigationItems.size
//            drawPath(
//                path,
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        animatedColor.copy(alpha = 0f),
//                        animatedColor.copy(alpha = 1f),
//                        animatedColor.copy(alpha = 1f),
//                        animatedColor.copy(alpha = 0f),
//                    ),
//                    startX = tabWidth * animatedSelectedTabIndex,
//                    endX = tabWidth * (animatedSelectedTabIndex + 1),
//                ),
//                style = Stroke(
//                    width = 6f,
//                    pathEffect = PathEffect.dashPathEffect(
//                        intervals = floatArrayOf(length / 2, length)
//                    )
//                )
//            )
//        }
//
//    }
//
//}


@Composable
fun CoreBottomNavigationWkwkwk(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit,
    hazeState: HazeState
) {
    var selectedItemIndex by remember { mutableIntStateOf(selected) }

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 32.dp)
            .fillMaxWidth()
            .height(80.dp)
            .hazeChild(state = hazeState, shape = CircleShape)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB8B5B5).copy(alpha = .5f),
                        Color(0xFFB8B5B5).copy(alpha = .1f),
                    ),
                ),
                shape = CircleShape
            )
            .clip(CircleShape)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedItemIndex == index

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.85f,
                    label = "scale",
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                )

                val animatedColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF0EA8B9) else Color.White.copy(alpha = 0.3f),
                    label = "animatedColor"
                )

//                Box(
//                    modifier = Modifier
//                        .size(56.dp)
//                        .scale(scale)
//                        .hazeChild(state = hazeState, shape = CircleShape) // Efek blur di tiap item
//                        .border(
//                            width = 1.dp,
//                            color = animatedColor.copy(alpha = 0.6f),
//                            shape = CircleShape
//                        )
//                        .clip(CircleShape)
//                        .clickable {
//                            selectedItemIndex = index
//                            onItemClick(index)
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            tint =  MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(24.dp)
                        )
                        if (selected == index) {

                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = item.label,
                                fontSize = if (item.label == "Destination") 12.sp else 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

//                }
            }
        }
    }
}









@Composable
fun CoreBottomNavigation(
    items: List<BottomNavigationItem> ,
    selected: Int,
    onItemClick: (Int) -> Unit,
    hazeState: HazeState
) {

    val selectedItemIndex by remember { mutableIntStateOf(selected) }
    Box(
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 30.dp)
            .fillMaxWidth()
            .height(75.dp)
            .hazeChild(state = hazeState, shape = CircleShape)
            .border(
                width = 4.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE5E2E1).copy(alpha = .8f),
                        Color(0xFFE5E2E1).copy(alpha = .1f),
                    ),
                ),
                shape = CircleShape
            )
    ) {

        BottomBarMain(
            items = items ,
            selected = selected,
            onItemClick = {onItemClick(items.indexOf(items[it]))}
        )


        val animatedSelectedTabIndex by animateFloatAsState(
            targetValue = selected.toFloat(), label = "animatedSelectedTabIndex",
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioLowBouncy,
            )
        )

        val animatedColor by animateColorAsState(
            targetValue = Color(0xFF0EA8B9),
            label = "animatedColor",
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
            )
        )
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//                .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
//        ) {
//            val tabWidth = size.width / items.size
//            drawCircle(
//                color = animatedColor.copy(alpha = .6f),
//                radius = size.height / 2,
//                center = Offset(
//                    (tabWidth * animatedSelectedTabIndex) + tabWidth / 2,
//                    size.height / 2
//                )
//            )
//        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        ) {
            val path = Path().apply {
                addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
            }
            val length = PathMeasure().apply { setPath(path, false) }.length

            val tabWidth = size.width / items.size
            drawPath(
                path,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        animatedColor.copy(alpha = 0f),
                        animatedColor.copy(alpha = 1f),
                        animatedColor.copy(alpha = 1f),
                        animatedColor.copy(alpha = 0f),
                    ),
                    startX = tabWidth * animatedSelectedTabIndex,
                    endX = tabWidth * (animatedSelectedTabIndex + 1),
                ),
                style = Stroke(
                    width = 6f,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(length / 2, length)
                    )
                )
            )
        }





    }

}


@Composable
fun BottomBarMain(
    items: List<BottomNavigationItem> ,
    selected: Int,
    onItemClick: (Int) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selected == index

            val alpha by animateFloatAsState(
                targetValue = if (selected == index) 1f else .35f,
                label = "alpha"
            )

            val scale by animateFloatAsState(
                targetValue = if (selected == index) 1f else .85f,
                visibilityThreshold = .000001f,
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioHighBouncy,

                    ),
                label = "scale"
            )


            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .scale(scale)
//                    .alpha(alpha)
                    .fillMaxHeight()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onItemClick(index)
                        }
                    },

            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onBackground,
//                    if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                if (selected == index) {

                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.label,
                        fontSize = if (item.label == "Destination") 11.sp else 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer
//                        if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }
            }


        }
    }
}



@Composable
fun MainBottomBar(
    items: List<BottomNavigationItem> ,
    selected: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar (
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape),
        tonalElevation = 10.dp

//                .hazeChild(state = hazeState, shape = CircleShape)
//                .border(
//                    width = 4.dp,
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.onSurface.copy(
//                                alpha = .8f
//                            ),
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = .2f),
//                        ),
//                    ),
//                    shape = CircleShape
//                )
    ) {


        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onItemClick(index) },
                icon = {

                    val alpha by animateFloatAsState(
                        targetValue = if (selected == index) 1f else .35f,
                        label = "alpha"
                    )

                    val scale by animateFloatAsState(
                        targetValue = if (selected == index) 1f else .85f,
                        visibilityThreshold = .000001f,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow,
                            dampingRatio = Spring.DampingRatioHighBouncy,

                            ),
                        label = "scale"
                    )


                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .scale(scale)
                            .alpha(alpha)

                    ) {

                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            tint = if (selected == index) Color.White
                            else MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(20.dp)
                        )

                        if (selected == index) {

                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = item.label,
                                fontSize = if (item.label == "Destination") 12.sp else 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.outline,
                )

            )
        }

    }
}

data class BottomNavigationItem (
    val label: String,
    @DrawableRes val icon: Int,
)

