package com.codewithfk.quickernotes.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codewithfk.quickernotes.utils.UrlUtils


@Composable
fun SettingsScreen(navController: NavController, urlUtils: UrlUtils) {
    Scaffold {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Settings", modifier = Modifier.padding(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.3f)).clickable {
                        urlUtils.openUrlInBrowser("https://furqanullah717.github.io/QuickNotes-CMP/")
                    }.padding(16.dp)
            ) {
                Text("Privacy Policy")
            }
        }

    }

}