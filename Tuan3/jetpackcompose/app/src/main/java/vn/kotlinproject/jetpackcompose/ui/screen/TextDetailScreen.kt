package vn.kotlinproject.jetpackcompose.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            // Tiêu đề canh giữa + màu xanh giống hình
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Text Detail",
                        color = Color(0xFF26A5E4),           // xanh nhạt như ảnh
                        style = MaterialTheme.typography.titleMedium
                    )
                },
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
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val baseColor = Color(0xFF222222)              // đen xám
            val brown     = Color(0xFFB67A2A)              // nâu chữ Brown
            val gray      = Color(0xFF8A8A8A)              // xám nhạt cho "lazy"

            val styled = buildAnnotatedString {
                pushStyle(SpanStyle(color = baseColor))
                append("The ")

                // quick (gạch ngang, giữ màu base)
                pushStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                append("quick ")
                pop()

                // Brown (đậm + nâu)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold, color = brown))
                append("Brown ")
                pop()

                append("\nfox ")

                // j u m p s (giãn chữ)
                pushStyle(SpanStyle(letterSpacing = 0.35.em))
                append("jumps ")
                pop()

                // over (đậm, đen)
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("over ")
                pop()

                append("\n")

                // the (gạch dưới)
                pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                append("the ")
                pop()

                // lazy (in nghiêng + xám nhạt)
                pushStyle(SpanStyle(fontStyle = FontStyle.Italic, color = gray))
                append("lazy ")
                pop()

                append("dog.")
                pop() // baseColor
            }

            Text(
                text = styled,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 22.sp,
                    lineHeight = 30.sp
                )
            )

            Divider(thickness = 0.7.dp, color = Color(0x1A000000)) // đường kẻ mảnh nhạt
        }
    }
}
