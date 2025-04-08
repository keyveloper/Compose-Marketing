package com.example.marketing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import com.example.marketing.navigation.AppNavGraph
import com.example.marketing.ui.theme.MarketingTheme
import com.example.marketing.view.AuthHomeScreen
import com.example.marketing.view.InfluencerLoginScreen
import com.example.marketing.view.InfluencerProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketingTheme {
                AppNavGraph()
            }
        }
    }
}
