package com.example.marketing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.marketing.ui.theme.MarketingTheme
import com.example.marketing.view.AdvertisementDetailScreen
import com.example.marketing.view.AuthHomeScreen
import com.example.marketing.view.InfluencerLoginScreen
import com.example.marketing.view.InfluencerSignUpScreen
import com.example.marketing.view.MainScreen
import com.example.marketing.view.ScaffoldTestScreen
import com.example.marketing.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketingTheme {
                InfluencerSignUpScreen()
            }
        }
    }
}
