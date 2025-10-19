package com.codewithfk.quickernotes.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun SignInScreen(navController: NavController) {
    val viewModel = viewModel { SignInViewModel() }

    val email = viewModel.email.collectAsStateWithLifecycle()
    val pass = viewModel.password.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up", fontSize = 22.sp)
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            email.value, onValueChange = {
                viewModel.onEmailUpdated(it)
            }, modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Email")
            },
            label = {
                Text("Email")
            })

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            pass.value, onValueChange = {
                viewModel.onPasswordUpdated(it)
            }, modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Password")
            },
            label = {
                Text("Password")
            })

        Spacer(modifier = Modifier.size(16.dp))

        TextButton({
            navController.popBackStack()
        }) {
            Text("Don't have an account? Signup")
        }
        Spacer(modifier = Modifier.size(16.dp))



        Button({ viewModel.signIn() }, modifier = Modifier.fillMaxWidth()) {
            Text("Submit")
        }

    }
}