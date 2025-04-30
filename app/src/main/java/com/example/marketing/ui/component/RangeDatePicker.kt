package com.example.marketing.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeDatePicker(
    label: String,
    modifier: Modifier = Modifier,
    onDateRangeSelected: (Long, Long) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }
    val state = rememberDateRangePickerState()
    var selectedStart: Long? by remember { mutableStateOf(null) }
    var selectedEnd: Long? by remember { mutableStateOf(null) }


    OutlinedTextField(
        modifier = modifier,
        value = buildString {
            append(
                selectedStart?.let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
                } ?: "시작일"
            )
            append(" - ")
            append(
                selectedEnd?.let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
                } ?: "종료일"
            )
        },
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = null)
            }
        }
    )

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val start = state.selectedStartDateMillis
                        val end = state.selectedEndDateMillis
                        if (start != null && end != null) {
                            selectedStart = start
                            selectedEnd = end
                            onDateRangeSelected(start, end)
                        }
                        showPicker = false
                    }
                ) {
                    Text("확인")
                }
            },
        ) {
            DateRangePicker(state = state)
        }
    }
}

