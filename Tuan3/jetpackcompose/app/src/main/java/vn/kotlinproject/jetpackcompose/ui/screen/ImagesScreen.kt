package vn.kotlinproject.jetpackcompose.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import vn.kotlinproject.jetpackcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Images") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val url = "https://xdcs.cdnchinhphu.vn/446259493575335936/2023/8/23/giao-thong-van-tai-9096-16927730679551867829733.jpeg"
            AsyncImage(
                model = url,
                contentDescription = "UTH logo (web)",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Text(url, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)

            AsyncImage(
                model = R.drawable.uth,
                contentDescription = "In app",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Text("In app", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}
