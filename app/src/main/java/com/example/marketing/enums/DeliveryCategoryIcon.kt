package com.example.marketing.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class DeliveryCategoryIcon(
    val code: Int,
    val iconVector: ImageVector,
    val emoji: String?,
    val toKorean: String,
    val contentDescription: String,
) {
    LIFE(
        0,
        Icons.Default.LocalLaundryService,
        "🧘",
        "생활",
        "생활/가전 아이콘"
    ),
    SERVICE(
        1,
        Icons.Default.SupportAgent,
        "🛠️",
        "서비스",
        "서비스 아이콘"
    ),
    DIGITAL(
        2,
        Icons.Default.Memory,
        "💻",
        "디지털",
        "디지털/전자기기 아이콘"
    ),
    BEAUTY(
        3,
        Icons.Default.Brush,
        "💄",
        "뷰티",
        "화장품 및 뷰티 아이콘"
    ),
    FASSION(
        4,
        Icons.Default.Checkroom,
        "👗",
        "패션",
        "의류 및 패션 아이콘"
    ),
    BOOK(
        5,
        Icons.AutoMirrored.Filled.MenuBook,
        "📚",
        "도서",
        "책 및 도서 아이콘"
    ),
    FOOD(
        6,
        Icons.Default.Fastfood,
        "🍔",
        "음식",
        "음식 및 식음료 아이콘"
    ),
    PET(
        7,
        Icons.Default.Pets,
        "🐶",
        "반려동물",
        "반려동물 및 애완용품 아이콘"
    );

    companion object {
        val codeMap: Map<Int, DeliveryCategoryIcon> = entries.associateBy { it.code }

        fun fromCode(code: Int): DeliveryCategoryIcon? = codeMap[code]
    }
}