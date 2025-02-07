@file:OptIn(ExperimentalMaterial3Api::class)

package com.jer.banyumastourismapp.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.common.verySmallIcon
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme

@Composable
fun SearchBarForAll(modifier: Modifier = Modifier, hint: String) {

    var query by remember {
        mutableStateOf("")
    }

    var active by remember {
        mutableStateOf(false)
    }

    var isVisible by remember { mutableStateOf(false) }

//    var context = LocalContext.current

    val listHistory by remember { mutableStateOf(mutableListOf<String>()) }


        DockedSearchBar(
            modifier = modifier.fillMaxWidth(),
            query = query,
            onQueryChange = { query = it },
            onSearch = { newQuery ->
                listHistory.add(newQuery)
                active = false


                       },
            active = active,
            onActiveChange = { active = it},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null )
            },
            colors = SearchBarDefaults.colors(containerColor =  MaterialTheme.colorScheme.onPrimary),
            placeholder = { Text(text = hint, color = MaterialTheme.colorScheme.outline) },
            trailingIcon = {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (active) {
                        IconButton(
                            onClick = {
                                if (query.isNotEmpty()) {
                                    query = ""
//                                    Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                                } else active = false
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }

                    } else {

                        val listDropdown = listOf("A - Z", "Z - A", "Reset")

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.onPrimary,
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                            onClick = { isVisible = true },
                        ) {
                            Box(
                                modifier = Modifier.size(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.filtericon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(verySmallIcon)
                                )
                            }
                        }
                        
                        DropdownMenu(
                            expanded = isVisible,
                            onDismissRequest = { isVisible = false },
                        ) {
                            listDropdown.forEach { menuItem ->
                                DropdownMenuItem(
                                    text = { Text(
                                        text = menuItem,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ) },
                                    onClick = {
                                        isVisible = false

                                        // sini cuy logic nya
                                    }
                                )
                            }
                        }

                    }


                }


            }

        ) {

            listHistory.takeLast(3).reversed().forEach { item ->
                ListItem(
                    headlineContent = {
                        Text(text = item, color = MaterialTheme.colorScheme.onSurfaceVariant) } ,
                    leadingContent = { Icon(painter = painterResource(id = R.drawable.historyicon), contentDescription = null)},
                    trailingContent = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    listHistory.remove(item)
                                    active = false
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(verySmallIcon)
                            )
                        }
                    }
                       ,
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable { query = item }

                )
            }

        }






}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevSearchBar() {
    BanyumasTourismAppTheme {
        SearchBarForAll(hint = "Search Destination")
    }

}