package com.jer.banyumastourismapp.presentation.sosmed

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.sosmed.component.AlertDialogStoryInput
import java.util.Date

@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    viewModel: StoryViewModel,
)  {

    var  selectedCategory  = rememberSaveable {
        mutableStateOf("")
    }
    val isClassified = rememberSaveable {
        mutableStateOf(false)
    }

    var isShowDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val  destination  = rememberSaveable {
        mutableStateOf("")
    }

    val  category  = rememberSaveable {
        mutableStateOf("")
    }

    val  message  = rememberSaveable {
        mutableStateOf("")
    }

    val  rating  = rememberSaveable {
        mutableStateOf(0)
    }


    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firebaseUser = auth.currentUser

    val textAlert by viewModel.textAlert.collectAsState()
    val story by viewModel.story.collectAsState()
    val storyByCategory = getStoryByCategory(selectedCategory.value, story)


    LaunchedEffect(Unit) {
        viewModel.getStory()
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Column { 
                AddPostButton(
                    onClick = {
                        isShowDialog = true
                    }
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier.padding(innerPadding)
        ) {



            val (bg, divider, categoryRow, sosmedList) = createRefs()

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(divider) {
                        top.linkTo(categoryRow.bottom)
                    }
            )

//            Box(
//                modifier = Modifier
//                    .height(100.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.colorScheme.primaryContainer)
//                    .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
//                    .constrainAs(bg) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )


            CategoryRow (
               isDelay = false,
                selectedCategory = selectedCategory,
                isOnClassified = isClassified,
                modifier = Modifier
                    .constrainAs(categoryRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(vertical = 30.dp),
            )



            StoryListColumn(
                isClassified = isClassified.value,
                storyByCategory = storyByCategory,
                listStory = story.reversed(),
                modifier = Modifier
                    .constrainAs(sosmedList) {
                        top.linkTo(divider.bottom)
                    }
                    .fillMaxHeight()

            )

            if (isShowDialog) {
                AlertDialogStoryInput(
                    alertTitle = "Post Your Trip Experience!",
                    onDismiss = { isShowDialog = false },
                    destination = destination,
                    category = category,
                    rating = rating,
                    message = message,
                    onSubmit = {
                        viewModel.insertStory(
                            Story(
                                name = firebaseUser?.displayName,
                                photoUrl = firebaseUser?.photoUrl.toString(),
                                destination = destination.value,
                                category = category.value,
                                rating = rating.value.toDouble(),
                                message = message.value,
                                time = Date().time
                            )
                        )

                        viewModel.loadStory()
                        Toast.makeText(context, textAlert ?: "Success", Toast.LENGTH_SHORT).show()

                        isShowDialog = false
                    }
                )
            } else {
                destination.value = ""
                category.value = ""
                rating.value = 0
                message.value = ""
            }
        }

    }

}

@Composable
fun AddPostButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        onClick = { onClick() },
        modifier = Modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun StoryListColumn(
    modifier: Modifier = Modifier,
    isClassified: Boolean,
    storyByCategory: List<Story>,
    listStory: List<Story>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 15.dp, bottom = 250.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        if (isClassified) {
            items(storyByCategory) { story ->
                StoryCard(story = story)
            }
        } else {
            items(listStory.size) { index ->
                StoryCard(story = listStory[index])
            }
        }

    }
}

fun getStoryByCategory(category: String, listStory: List<Story>): List<Story> {
    return listStory.filter {
        it.category?.contains(category) ?: false
    }
}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//private fun PrevSosmedListScreen() {
//    BanyumasTourismAppTheme {
//        val listStory = listOf(
//            Story (
//                name = "Fajar",
//                message = "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya",
//                category = "Waterfall",
//                destination = "Curug Telu",
//                rating = 4.5,
//                time = 5
//            ),
//            Story (
//                name = "Fajar",
//                message = "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya",
//                category = "Waterfall",
//                destination = "Curug Telu",
//                rating = 4.5,
//                time = 5
//            ),
//            Story (
//                name = "Fajar",
//                message = "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya",
//                category = "Waterfall",
//                destination = "Curug Telu",
//                rating = 4.5,
//                time = 5
//            ),
//            Story (
//                name = "Fajar",
//                message = "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya",
//                category = "Waterfall",
//                destination = "Curug Telu",
//                rating = 4.5,
//                time = 5
//            ),
//
//
//        )
//        StoryScreen(
//            listStory = listStory,
//        )
//
//    }
//}