package com.example.marketing.ui.item

data class SignUpFormItem(
    val label: String,
    val placeholder: String,
    val value: String,
    val onValueChange: (String) -> Unit,
    val needSecret: Boolean
)
