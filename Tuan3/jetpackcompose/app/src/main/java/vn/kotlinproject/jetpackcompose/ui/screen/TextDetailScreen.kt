package vn.kotlinproject.jetpackcompose.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            val styled = buildAnnotatedString {
                append("The ")

                // quick (gạch ngang)
                pushStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                append("quick ")
                pop()

                // Brown (đậm)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("Brown ")
                pop()

                append("fox ")

                // jumps (có thể letter-spacing nhẹ nếu thích)
                // pushStyle(SpanStyle(letterSpacing = 0.8.sp))
                append("jumps ")
                // pop()

                // over (đậm)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("over ")
                pop()

                append("the ")

                // lazy (gạch dưới)
                pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                append("lazy ")
                pop()

                append("dog.")
            }

            Text(text = styled, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
