package com.insightnews.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.insightnews.app.ui.screens.ArticleDetailScreen
import com.insightnews.app.ui.screens.BookmarksScreen
import com.insightnews.core.domain.model.Article
import com.insightnews.feature.news.presentation.NewsScreen

sealed class Screen(val route: String) {
    data object News : Screen("news")
    data object ArticleDetail : Screen("article_detail/{articleId}") {
        fun createRoute(article: Article) = "article_detail/${article.id}"
    }
    data object Bookmarks : Screen("bookmarks")
}

@Composable
fun InsightNewsNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.News.route
    ) {
        composable(Screen.News.route) {
            NewsScreen(
                onArticleClick = { article ->
                    SharedArticleHolder.article = article
                    navController.navigate(Screen.ArticleDetail.createRoute(article))
                },
                onBookmarksClick = { navController.navigate(Screen.Bookmarks.route) }
            )
        }
        composable(
            route = Screen.ArticleDetail.route,
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) {
            val article = SharedArticleHolder.article
            if (article != null) {
                ArticleDetailScreen(
                    article = article,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(Screen.Bookmarks.route) {
            BookmarksScreen(
                onBack = { navController.popBackStack() },
                onArticleClick = { article ->
                    SharedArticleHolder.article = article
                    navController.navigate(Screen.ArticleDetail.createRoute(article))
                }
            )
        }
    }
}
