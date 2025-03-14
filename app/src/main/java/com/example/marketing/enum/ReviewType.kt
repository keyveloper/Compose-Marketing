package com.example.marketing.enum

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.ui.graphics.vector.ImageVector


enum class ReviewType(val code: Int, val icon: ImageVector) {
    VISITED(0, Icons.Filled.FoodBank),
    DELIVERY(1, Icons.Filled.DeliveryDining),
    ARTICLE(2, AutoMirrored.Filled.Article),
    BUY(3, Icons.Filled.Money),
    RECEIPT(4, Icons.Filled.Receipt),
    LONG_FORM(5, Icons.Filled.Movie),
    SHORT_FROM(6, Icons.Filled.LocalMovies)
}