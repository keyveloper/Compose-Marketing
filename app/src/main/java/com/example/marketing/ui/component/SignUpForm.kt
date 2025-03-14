package com.example.marketing.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketing.item.SignUpFormItem

@Composable
fun SignUpForm(
    title: String,
    toSubmit: () -> Unit,
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
        Spacer(modifier = Modifier.height(18.dp))

        // welcome message Text
        Text(title)

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
                modifier = Modifier.fillMaxWidth(),
                visualTransformation =
                if (field.needSecret) PasswordVisualTransformation()
                    else VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(18.dp))
        }
        // to Next button
        Button(
            onClick = toSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
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

