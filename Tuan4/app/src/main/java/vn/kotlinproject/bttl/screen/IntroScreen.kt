package vn.kotlinproject.bttl.screen;

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import vn.kotlinproject.bttl.R

@Composable
fun IntroScreen(onReady: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.jetpackcomposeicon),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )

            Spacer(Modifier.height(16.dp))
            Text("Jetpack Compose", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text(
                "Jetpack Compose is a modern UI Toolkit for building native Android Applications " +
                        "using a declarative programming approach",
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))
            Button(onClick = onReady, modifier = Modifier.fillMaxWidth()) {
                Text("PUSH")
            }
        }
    }
}
