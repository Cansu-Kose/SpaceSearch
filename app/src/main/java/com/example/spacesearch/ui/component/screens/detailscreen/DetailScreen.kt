package com.example.spacesearch.ui.component.screens.detailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacesearch.R
import com.skydoves.landscapist.coil.CoilImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailScreen(title: String?, subreddit: String?, imageUrl: String?, timestamp: Double?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = title ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.testTag("DetailTitle")
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = subreddit ?: "", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Posted on: ${convertUtcToDate(timestamp ?: 0.0)}",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        CoilImage(
            imageModel = { imageUrl },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(12.dp)),
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            failure = {
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        )
    }
}

fun convertUtcToDate(utc: Double): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(Date((utc * 1000).toLong()))
}
