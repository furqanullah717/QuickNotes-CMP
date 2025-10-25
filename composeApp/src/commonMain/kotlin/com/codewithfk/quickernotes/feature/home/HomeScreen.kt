package com.codewithfk.quickernotes.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codewithfk.quickernotes.HomeViewModel
import com.codewithfk.quickernotes.data.db.NoteDatabase
import com.codewithfk.quickernotes.model.Note
import com.codewithfk.quickernotes.notes.ListNotesScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import quickernotes.composeapp.generated.resources.Res
import quickernotes.composeapp.generated.resources.rafiki
import quickernotes.composeapp.generated.resources.user

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(database: NoteDatabase, navController: NavController) {
    val viewModel = viewModel { HomeViewModel(database) }
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
            }, shape = CircleShape) {
                Text(text = "+", fontSize = 18.sp)
            }
        }) {


        val notes = viewModel.notes.collectAsStateWithLifecycle(emptyList())
        Column(modifier = Modifier.padding(it)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Notes",
                    modifier = Modifier.fillMaxWidth().padding(16.dp).align(Alignment.Center),
                    fontSize = 30.sp
                )
                Image(painterResource(Res.drawable.user), null, modifier = Modifier.padding(end = 16.dp).size(48.dp).padding(4.dp).clickable{
                    navController.navigate("signup")
                }.align(Alignment.CenterEnd))
            }
            if (notes.value.isNotEmpty()) {
                ListNotesScreen(notes.value)
            } else {
                EmptyView()
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(onDismissRequest = {
                showBottomSheet = false
            }, sheetState = bottomSheetState) {
                AddItemDialog(onCancel = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                    showBottomSheet = false
                }, {
                    viewModel.addNotes(it)
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                    showBottomSheet = false
                })
            }
        }
    }
}


@Composable
fun AddItemDialog(onCancel: () -> Unit, onSave: (Note) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        val colour = TextFieldDefaults.colors(
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent,
        )

        TextField(
            value = title,
            onValueChange = { title = it },
            colors = colour,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Title", fontSize = 22.sp)
            },
            textStyle = TextStyle(fontSize = 22.sp)
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            colors = colour,
            placeholder = {
                Text(text = "Say something")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )
        Row(modifier = Modifier.align(Alignment.End)) {
            Text(text = "Cancel", modifier = Modifier.padding(8.dp).clickable {
                onCancel()
            })
            Text(text = "Save", modifier = Modifier.padding(8.dp).clickable {
                onSave(Note(title = title, description = description))
            })
        }
    }
}


@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painterResource(Res.drawable.rafiki),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Create your first note !",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp
            )
        }
    }
}