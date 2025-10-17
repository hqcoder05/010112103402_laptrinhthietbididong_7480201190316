package vn.kotlinproject.jetpackcompose_1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import vn.kotlinproject.jetpackcompose_1.R

@Composable
fun WelcomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_round),
            contentDescription = null,
            modifier = Modifier.size(140.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(12.dp))
        Text("Jetpack Compose", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            "Compose là UI toolkit hiện đại cho Android theo hướng khai báo.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
            Text("I'm ready")
        }
    }
}