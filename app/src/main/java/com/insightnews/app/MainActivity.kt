package com.insightnews.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.insightnews.app.ui.navigation.InsightNewsNavGraph
import com.insightnews.app.ui.theme.InsightNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //WindowCompat.getDecorFitsSystemWindows(window, false)
        setContent {
            InsightNewsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    InsightNewsNavGraph()
                }
            }
        }
    }
}
