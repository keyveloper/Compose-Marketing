package com.example.marketing.ui.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marketing.enums.DeliveryCategory
import com.example.marketing.enums.DeliveryCategoryIcon

@Composable
fun DeliveryCategorySelectionCard(
    modifier: Modifier = Modifier,
    onAdd: (DeliveryCategory) -> Unit,
    onDelete: (DeliveryCategory) -> Unit
) {
    val categories: List<DeliveryCategory> = DeliveryCategory.entries.toList()
    val selectedCategories = mutableSetOf<DeliveryCategory>()

    LazyRow(
        modifier = modifier
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val isSelected = category in selectedCategories
            Card(
                onClick = {
                    if (!isSelected) {
                        onAdd(category)
                        selectedCategories + category
                        Log.i("DeliverySelector", "${selectedCategories}")
                    } else {
                        onDelete(category)
                        selectedCategories - category
                        Log.i("DeliverySelector", "${selectedCategories}")
                    }
                },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        Color.Transparent,                          // outlined look
                    contentColor = if (isSelected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurface
                ),
                border = if (isSelected) null                       // hide outline
                else BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isSelected) 4.dp else 0.dp
                ),
                modifier = Modifier
                    .height(44.dp)                                  // consistent height
                    .wrapContentWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = DeliveryCategoryIcon.fromCode(category.code)?.emoji ?: "null?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
