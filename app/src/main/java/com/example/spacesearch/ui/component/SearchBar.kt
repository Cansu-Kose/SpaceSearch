package com.example.spacesearch.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacesearch.R
import com.example.spacesearch.ui.theme.Green50
import com.example.spacesearch.viewmodel.MainViewModel

@Composable
fun SearchBar(viewModel: MainViewModel) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
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
                contentDescription = stringResource(R.string.search),
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    if (it.text.isNotEmpty()) {
                        viewModel.search(it.text, "week", 20)
                    }
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (text.text.isEmpty()) {
                        Text(
                            text = "Search",
                            fontSize = 16.sp
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
            if (text.text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = "Clear",
                    modifier = Modifier.clickable {
                        text = TextFieldValue("")
                        viewModel.search("", "week", 20)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


    }
}


