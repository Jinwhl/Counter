package com.example.counter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.counter.ui.theme.CounterTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val activity = LocalContext.current as? Activity
    val sharedPref = remember {
        activity?.getPreferences(Context.MODE_PRIVATE)
    }
    var numA by remember {
        // count(정수)를 다룰거 라서 getInt 사용
        val saveCount = sharedPref?.getInt("lastCount", 0) // default Value : 값이 없을 경우 0으로 처리
        mutableIntStateOf(saveCount ?: 0)
    }
    var numB by remember { // remember 해줘야 코드가 새로 돌면서 무한 루프에 빠지지 않고 값을 [저장]기능을 가지게 됨
        // swapNum(정수)를 다룰거 라서 getInt 사용
        val saveCount = sharedPref?.getInt("swapNumber", 0) // default Value : 값이 없을 경우 0으로 처리
        mutableIntStateOf(saveCount ?: 0)
    }
    // Compose 집합
    Column {
        Spacer(modifier = Modifier.height(32.dp))

        // Section 1 - numA 연산
        Text(text = "numA : $numA")
        Row {
            // count Up Button
            Button(
                onClick = {
                    numA++
                    sharedPref?.edit {
                        putInt("lastCount", numA) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Up")
            }
            // count Down Button
            Button(
                onClick = {
                    numA--
                    sharedPref?.edit {
                        putInt("lastCount", numA) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Down")
            }
            // Double count value
            Button(
                onClick = {
                    numA *= 2
                    sharedPref?.edit {
                        putInt("lastCount", numA) // count(정수)를 다룰거 라서 putInt 사용
                    }
                },
                // 값 Overflow 방지
                enabled = if (Int.MIN_VALUE / 2 <= numA && numA <= Int.MAX_VALUE / 2) {
                    true
                } else {
                    false
                }) {
                Text(text = "X 2")
            }
            // Divide count value
            Button(
                onClick = {
                    numA /= 2
                    sharedPref?.edit {
                        putInt("lastCount", numA) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Half")
            }
            // Reset
            Button(
                onClick = {
                    numA = 0
                    sharedPref?.edit {
                        putInt("lastCount", numA) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Reset")
            }
        }
        Divider()

        // Section 2 - numB 연산
        Text(text = "numB : $numB")
        Row {
            // count Up Button
            Button(
                onClick = {
                    numB++
                    sharedPref?.edit {
                        putInt("lastCount", numB) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Up")
            }
            // count Down Button
            Button(
                onClick = {
                    numB--
                    sharedPref?.edit {
                        putInt("lastCount", numB) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Down")
            }
            // Double count value
            Button(
                onClick = {
                    numB *= 2
                    sharedPref?.edit {
                        putInt("lastCount", numB) // count(정수)를 다룰거 라서 putInt 사용
                    }
                },
                // 값 Overflow 방지
                enabled = if (Int.MIN_VALUE / 2 <= numB && numB <= Int.MAX_VALUE / 2) {
                    true
                } else {
                    false
                }
            ) {
                Text(text = "X 2")
            }
            // Divide count value
            Button(
                onClick = {
                    numB /= 2
                    sharedPref?.edit {
                        putInt("lastCount", numB) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Half")
            }
            // Reset
            Button(
                onClick = {
                    numB = 0
                    sharedPref?.edit {
                        putInt("lastCount", numB) // count(정수)를 다룰거 라서 putInt 사용
                    }
                }) {
                Text(text = "Reset")
            }
        }
        Divider()

        // Section 3 - 추가 기능 : numA, numB 모두 0 으로 초기화 / 값 서로 바꾸기 / Page 전환
        Text(text = "Extra Settings")
        Row {
            // Reset numbers
            Button(
                onClick = {
                    numA = 0
                    numB = 0

                    sharedPref?.edit {
                        putInt("lastCount", numA)
                        putInt("lastCount", numB)
                    }
                }) {
                Text(text = "Reset All Value")
            }
            // Swap two numbers
            Button(
                onClick = {
                    val temp = numB
                    numB = numA
                    numA = temp

                    sharedPref?.edit {
                        putInt("Swap Count", numB)
                    }
                }) {
                Text(text = "Swap Number")
            }
            AdvancedFunctionPage(numA, numB)
        }
        Divider()

        // Section 4 - 버튼을 터치한 시간을 기록
        Timer()
        Divider()
    }
}

@Composable
fun AdvancedFunctionPage(numA:Int, numB:Int) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(context, AdvancedFunctionActivity::class.java)
            intent.putExtra("ID for numA", numA) // name 역할 : SubActivity 에서 변수 numA 를 사용할 수 있게끔 변수에 고유 아이디를 지정해줌
            intent.putExtra("ID for numB", numB)
            context.startActivity(intent)

        }) {
        Text(text = "Advanced")
    }
}
@Composable
fun Timer() {
    var currentTime by remember { mutableStateOf(0L) } // 타이머 현재 시간
    var isRunning by remember { mutableStateOf(false) } // 타이머 실행 여부
    var recordedTimes by remember { mutableStateOf(mutableListOf<Long>()) } // 타이머 시간 기록 저장소, Long 타입으로 해야 됨

    // 시간 표시 형식 지정 함수
    fun formatMillis(timeMillis: Long): String {
        val hours = timeMillis / 3600000
        val minutes = (timeMillis % 3600000) / 60000
        val seconds = (timeMillis % 60000) / 1000
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)

    }

    // delay 에 맞게 currentTime 계속 update
    LaunchedEffect(key1 = isRunning) {
        while (isRunning) {
            delay(1000) // Import : Long~
            currentTime += 1000
        }
    }
    Column {
        Text(text = formatMillis(currentTime)) // 타이머 시간 표시
        Row {
            // 타이머 시작,종료 버튼
            Button(onClick = {
                isRunning = !isRunning
                if (isRunning) {
                    currentTime = 0
                }
            }) {
                Text(if (isRunning) "Stop" else "Start")
            }

            // 타이머 시간 초기화 버튼
            Button(onClick = {
                currentTime = 0
            }) {
                Text(text = " Reset")
            }
            
            // 타이머 시간 기록 버튼
            Button(onClick = {
                recordedTimes.add(currentTime)
                // currentTime = 0 // 현재 시간 초기화 (선택 사항)
            }) {
                Text(text = "Record")
            }

            // 타이머 기록 삭제
            Button(onClick = {
                recordedTimes.clear() // 기록된 시간 목록 비우기
            }) {
                Text(text = "Clear ALL")
            }
        }
        Column {
            for (recordedTime in recordedTimes) {
                Text(text = formatMillis(recordedTime))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    CounterTheme {
        MainScreen()
    }
}