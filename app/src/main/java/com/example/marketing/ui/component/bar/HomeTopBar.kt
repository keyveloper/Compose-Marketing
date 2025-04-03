package com.example.marketing.ui.component.bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import com.example.marketing.enums.HomeScreenStatus

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onHomeBarSelected: (HomeScreenStatus) -> Unit
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(scrollState)
            ) {
                OutlinedButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { onHomeBarSelected(HomeScreenStatus.Event) },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text("Event")
                }

                OutlinedButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { onHomeBarSelected(HomeScreenStatus.Delivery) },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text("Delivery")
                }

                OutlinedButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { onHomeBarSelected(HomeScreenStatus.Type) },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text("Types")
                }

                OutlinedButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { onHomeBarSelected(HomeScreenStatus.Platform) },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text("Platforms")
                }
            }
            Spacer(Modifier.height(4.dp))

            HorizontalDivider(
                modifier.fillMaxWidth(),
                color = Color.Gray,
                thickness = 1.dp
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewHomeTopBar() {
    HomeTopBar { selectedStatus ->
        println("selected! ${selectedStatus}")
    }
}