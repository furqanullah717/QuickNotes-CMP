package com.codewithfk.quickernotes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codewithfk.quickernotes.data.cache.DataStoreManager
import com.codewithfk.quickernotes.data.db.NoteDatabase
import com.codewithfk.quickernotes.feature.auth.SignInScreen
import com.codewithfk.quickernotes.feature.auth.SignUpScreen
import com.codewithfk.quickernotes.feature.home.HomeScreen
import com.codewithfk.quickernotes.feature.profile.UserProfile
import com.codewithfk.quickernotes.ui.theme.QuickNotesAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(database: NoteDatabase, dataStoreManager: DataStoreManager) {
    QuickNotesAppTheme {

        val navController = rememberNavController()

        NavHost(navController, startDestination = "home") {

            composable(route = "home") {
                HomeScreen(database, dataStoreManager, navController)
            }

            composable(route = "signup") {
                SignUpScreen(dataStoreManager,navController)
            }
//
            composable(route = "signin") {
                SignInScreen(dataStoreManager,navController)
            }
            composable("profile") {
                UserProfile(dataStoreManager)
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