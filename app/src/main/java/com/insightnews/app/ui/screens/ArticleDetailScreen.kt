package com.insightnews.app.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.SourceBias
import com.insightnews.core.common.util.DateFormatter
import com.insightnews.feature.summary.presentation.SummaryViewModel
import com.insightnews.feature.summary.presentation.SummaryUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    article: Article,
    onBack: () -> Unit,
    summaryViewModel: SummaryViewModel = hiltViewModel(),
    bookmarkViewModel: com.insightnews.app.ui.viewmodel.ArticleDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val summaryState by summaryViewModel.state.collectAsState()
    val isBookmarked by bookmarkViewModel.isBookmarked.collectAsState()

    LaunchedEffect(article) {
        summaryViewModel.loadSummary(article)
        bookmarkViewModel.loadBookmarkState(article.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = {
                    IconButton(onClick = {
                        if (isBookmarked) bookmarkViewModel.removeBookmark(article.id)
                        else bookmarkViewModel.addBookmark(article)
                    }) {
                        Icon(
                            if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark"
                        )
                    }
                    IconButton(onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${article.title}\n${article.url}")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Share article"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            article.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(article.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text("${article.sourceName} • ${DateFormatter.formatRelative(article.publishedAt)}", style = MaterialTheme.typography.bodySmall)
                if (article.sourceBias != SourceBias.UNKNOWN) {
                    Text("Source bias: ${article.sourceBias.name.replace("_", " ")}", style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.height(16.dp))

                when (val state = summaryState) {
                    is SummaryUiState.Loading -> Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    is SummaryUiState.Success -> {
                        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("AI Summary", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(state.summary.formattedReadingTime, style = MaterialTheme.typography.labelMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(state.summary.summaryText, style = MaterialTheme.typography.bodyMedium)
                                state.summary.bulletPoints.forEach { point ->
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("• $point", style = MaterialTheme.typography.bodySmall)
                                }
                                if (state.summary.keywords.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Topics: ${state.summary.keywords.take(5).joinToString(", ")}", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    is SummaryUiState.Error -> Text("Summary unavailable", style = MaterialTheme.typography.bodySmall)
                }

                Text("Full Article", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(article.description ?: article.content ?: "Open in browser for full content.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
