package vn.kotlinproject.uth_smarttasks.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vn.kotlinproject.uth_smarttasks.R   // 👈 nhớ import

data class OnboardPage(
    val imageRes: Int,
    val title: String,
    val desc: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onSkip: () -> Unit,
    onDone: () -> Unit
) {
    val pages = remember {
        listOf(
            OnboardPage(
                imageRes = R.drawable.onb_time,
                title = "Easy Time Management",
                desc  = "With management based on priority and daily tasks, it gives you convenience…"
            ),
            OnboardPage(
                imageRes = R.drawable.onb_effectiveness,
                title = "Increase Work Effectiveness",
                desc  = "Time management and determining important tasks give better statistics…"
            ),
            OnboardPage(
                imageRes = R.drawable.onb_reminder,
                title = "Reminder Notification",
                desc  = "The app also provides reminders so you don't forget your assignments…"
            )
        )
    }
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == pages.lastIndex

    Scaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onSkip) { Text("Skip") }
            }
        },
        bottomBar = {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage > 0) {
                    FilledTonalIconButton(
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        }
                    ) { Icon(Icons.Default.ArrowBack, null) }
                } else {
                    Spacer(Modifier.size(40.dp))
                }

                Spacer(Modifier.width(12.dp))
                DotsIndicator(
                    total = pages.size,
                    index = pagerState.currentPage,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = {
                        if (isLast) onDone()
                        else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    },
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(if (isLast) "Get Started" else "Next")
                }
            }
        }
    ) { p ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().padding(p)
        ) { page ->
            OnboardPageContent(pages[page])
        }
    }
}

@Composable
private fun OnboardPageContent(model: OnboardPage) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ảnh minh hoạ từ drawable
        Image(
            painter = painterResource(model.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),          // tuỳ chỉnh theo ảnh
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(24.dp))
        Text(model.title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text(model.desc, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
    }
}

@Composable
private fun DotsIndicator(total: Int, index: Int, modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        repeat(total) { i ->
            val selected = i == index
            Box(
                Modifier
                    .padding(4.dp)
                    .size(if (selected) 10.dp else 8.dp)
            ) {
                Surface(
                    color = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxSize()
                ) {}
            }
        }
    }
}
