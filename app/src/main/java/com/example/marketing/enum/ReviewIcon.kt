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

enum class ReviewIcon(
    val code: Int,
    val icon: ImageVector,
    val description: String,
    val title: String
) {
    VISITED(0, Icons.Filled.FoodBank, "Visiting review icon", "방문형"),
    DELIVERY(1, Icons.Filled.DeliveryDining, "Delivery review icon", "배송형"),
    ARTICLE(2, AutoMirrored.Filled.Article, "Article review icon", "기자단"),
    BUY(3, Icons.Filled.Money, "Purchase review icon", "구매형"),
    RECEIPT(4, Icons.Filled.Receipt, "Receipt review icon", "영수증"),
    LONG_FORM(5, Icons.Filled.Movie, "Long form review icon", "롱폼"),
    SHORT_FROM(6, Icons.Filled.LocalMovies, "Short form review icon", "숏폼");

    companion object {
        fun fromCode(code: Int): ReviewIcon? {
            return entries.firstOrNull { it.code == code }
        }
    }
}