package vn.kotlinproject.baitap2

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GetStartedScreen(
    onReadyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xFF1C1C1E),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 560.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(56.dp))

                    val logo = try {
                        painterResource(id = R.drawable.ic_compose_logo)
                    } catch (_: Exception) {
                        null
                    }
                    if (logo != null) {
                        Image(
                            painter = logo,
                            contentDescription = "Jetpack Compose",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(24.dp))
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Android,
                            contentDescription = null,
                            tint = Color(0xFF3A86FF),
                            modifier = Modifier.size(160.dp)
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = "Jetpack Compose",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(0xFF2B2B2B),
                            fontSize = 20.sp
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Jetpack Compose là bộ công cụ UI hiện đại để xây dựng " +
                                "ứng dụng Android native bằng cách tiếp cận lập trình khai báo.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF6B6B6B),
                        ),
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(horizontal = 6.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(Modifier.height(56.dp))

                    Button(
                        onClick = onReadyClick,
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(bottom = 28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3A86FF),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text("I'm ready")
                    }
                }
            }
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun GetStartedScreenPreview() {
    MaterialTheme {
        GetStartedScreen(onReadyClick = {})
    }
}
