package com.codewithfk.quickernotes

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codewithfk.quickernotes.db.NoteDatabase
import com.codewithfk.quickernotes.feature.auth.SignInScreen
import com.codewithfk.quickernotes.feature.auth.SignUpScreen
import com.codewithfk.quickernotes.feature.home.HomeScreen
import com.codewithfk.quickernotes.model.Note
import com.codewithfk.quickernotes.notes.ListNotesScreen
import com.codewithfk.quickernotes.ui.theme.QuickNotesAppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quickernotes.composeapp.generated.resources.Res
import quickernotes.composeapp.generated.resources.rafiki

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(database: NoteDatabase) {
    QuickNotesAppTheme {

        val navController = rememberNavController()

        NavHost(navController, startDestination = "home") {

            composable(route = "home") {
                HomeScreen(database, navController)
            }

            composable(route = "signup") {
                SignUpScreen(navController)
            }
//
            composable(route = "signin") {
                SignInScreen(navController)
            }
//            composable (route="login"){
//                LoginScreen()
//            }
//            composable (route="login"){
//                LoginScreen()
//            }

        }
    }
}