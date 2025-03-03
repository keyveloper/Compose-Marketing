package com.example.marketing.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketing.item.SignUpFormItem

@Composable
fun SignUpForm(
    toNext: () -> Unit,
    items: List<SignUpFormItem>
) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 48.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .background(Color.White)
    ) {
        // welcome message Text
        Text("Welcome my World!")

        Spacer(modifier = Modifier.height(20.dp))

        // item 수만큼 생성하기
        items.forEach { field ->
            // 라벨
            Text(
                text = field.label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 입력 필드
            OutlinedTextField(
                value = field.value,
                onValueChange = field.onValueChange,
                placeholder = { Text(field.placeholder) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
        // to Next button
        Button(
            onClick = toNext,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("다음")
        }
    }
}



@Composable
@Preview
fun MobileAuthComponentPreview() {
}

