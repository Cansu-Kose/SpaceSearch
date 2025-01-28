package com.example.spacesearch.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacesearch.R
import com.example.spacesearch.ui.theme.Green50

@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onSearch: (TextFieldValue) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .background(color = Green50, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "Search",
        )
        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = searchText,
            onValueChange = { newValue ->
                val filteredText = newValue.text.filter { it.isLowerCase() && it.isLetter() }
                if (filteredText == newValue.text) {
                    onSearch(newValue)
                } else {
                    onSearch(TextFieldValue(filteredText))
                }
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier
                .weight(1f), decorationBox = { innerTextField ->
                if (searchText.text.isEmpty()) {
                    Text(
                        text = "Search",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            singleLine = true
        )

        if (searchText.text.isNotEmpty()) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_clear_24),
                contentDescription = "Clear",
                modifier = Modifier.clickable {
                    onSearch(TextFieldValue(""))
                }
            )
        }
    }
}


