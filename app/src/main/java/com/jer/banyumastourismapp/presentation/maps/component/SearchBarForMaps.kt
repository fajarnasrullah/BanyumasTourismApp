package com.jer.banyumastourismapp.presentation.maps.component

import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarForMaps(
    modifier: Modifier = Modifier,
    hint: String,
    onPlaceSelected: (String) -> Unit,

) {

    val context = LocalContext.current
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val listHistory = remember { mutableListOf<String>() }

    AndroidView(
        factory = { ctx ->
            AutoCompleteTextView(ctx).apply {
                this.hint = hint
                setTextColor(textColor.toArgb())
                setHintTextColor(textColor.copy(alpha = 0.6f).toArgb())
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                val autocompleteAdapter = ArrayAdapter<String>(ctx, android.R.layout.simple_dropdown_item_1line)
                val placesClient = Places.createClient(ctx)
                val autocompleteSessionToken = AutocompleteSessionToken.newInstance()

                addTextChangedListener { editable ->
                    val query = editable?.toString() ?: ""
                    if (query.isNotEmpty()) {
                        val request = FindAutocompletePredictionsRequest.builder()
                            .setSessionToken(autocompleteSessionToken)
                            .setQuery(query)
                            .build()

                        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                            autocompleteAdapter.clear()
                            response.autocompletePredictions.forEach { prediction ->
                                autocompleteAdapter.add(prediction.getFullText(null).toString())
                            }
                            autocompleteAdapter.notifyDataSetChanged()
                        }
                    }
                }

                setAdapter(autocompleteAdapter)

                setOnItemClickListener { _, _, position, _ ->
                    val selectedPlace = autocompleteAdapter.getItem(position) ?: return@setOnItemClickListener
                    onPlaceSelected(selectedPlace)
                    listHistory.add(selectedPlace)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .clip(CircleShape)

    )



}