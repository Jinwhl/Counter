package com.example.counter

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.counter.ui.theme.CounterTheme

class AdvancedFunctionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterTheme {
                SubScreen()
            }
        }
    }
}

@Composable
fun SubScreen() {
    val context = LocalContext.current as? Activity

    val numA = remember {
        context?.intent?.getIntExtra("ID for numA",0) ?: 0 // numA 는 Int 니까 getIntExtra 함수 사용
    }
    val numB = remember {
        context?.intent?.getIntExtra("ID for numB",0) ?: 0
    }

    Column {
        Button(
            onClick = {
                context?.finish()
            }) {
            Text(
                text = "Close Page",
            )
        }
        Text(text = "numA : $numA")
        Text(text = "numB : $numB")
    }
}

@Preview(showBackground = true)
@Composable
fun SubPreview() {
    CounterTheme {
        SubScreen()
    }
}