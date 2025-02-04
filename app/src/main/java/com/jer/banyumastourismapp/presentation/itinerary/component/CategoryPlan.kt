package com.jer.banyumastourismapp.presentation.itinerary.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.jer.banyumastourismapp.R

data class CategoryPlanItem(val title: String, val icon: @Composable () -> Unit)


val categoryPlan = listOf(
    CategoryPlanItem(title = "On The Way", icon = { painterResource(id = R.drawable.caricon) }),
    CategoryPlanItem(title = "On The Way", icon = { painterResource(id = R.drawable.caricon) }),
    CategoryPlanItem(title = "On The Way", icon = { painterResource(id = R.drawable.caricon) }),
    CategoryPlanItem(title = "On The Way", icon = { painterResource(id = R.drawable.caricon) }),

)