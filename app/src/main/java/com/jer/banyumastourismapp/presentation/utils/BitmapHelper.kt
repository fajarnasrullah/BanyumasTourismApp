package com.jer.banyumastourismapp.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Canvas
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

private const val TAG = "BitmapHelper"

data class BitmapParameters(
    @DrawableRes val id: Int,
    @ColorInt val iconColor: Int,
    @ColorInt val backgroundColor: Int? = null,
    val backgroundAlpha: Int = 168,
    val padding: Int = 16,
)


private val bitmapCache = mutableMapOf<BitmapParameters, BitmapDescriptor>()


fun vectorToBitmap(context: Context, parameters: BitmapParameters): BitmapDescriptor {
    return bitmapCache[parameters] ?: createBitmapDescriptor(context, parameters)
}

private fun createBitmapDescriptor(
    context: Context,
    parameters: BitmapParameters
): BitmapDescriptor {
    val vectorDrawable = ResourcesCompat.getDrawable(
        context.resources,
        parameters.id,
        null
    ) ?: return run {
        Log.e(TAG, "Resource not found")
        BitmapDescriptorFactory.defaultMarker()
    }

    val padding = if (parameters.backgroundColor != null) parameters.padding else 0
    val halfPadding = padding / 2

    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth + padding,
        vectorDrawable.intrinsicHeight + padding,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable.setBounds(
        halfPadding,
        halfPadding,
        canvas.width - halfPadding,
        canvas.height - halfPadding
    )
    DrawableCompat.setTint(vectorDrawable, parameters.iconColor)

    if (parameters.backgroundColor != null) {
        val fillPaint = Paint().apply {
            style = Paint.Style.FILL
            color = parameters.backgroundColor
            alpha = parameters.backgroundAlpha
        }

        val strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = parameters.backgroundColor
            strokeWidth = 3f
        }

        canvas.drawCircle(
            canvas.width / 2.0f,
            canvas.height / 2.0f,
            canvas.width.toFloat() / 2,
            fillPaint
        )
        canvas.drawCircle(
            canvas.width / 2.0f,
            canvas.height / 2.0f,
            (canvas.width.toFloat() / 2) - 3,
            strokePaint
        )
    }
    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap).also { bitmapCache[parameters] = it }
}