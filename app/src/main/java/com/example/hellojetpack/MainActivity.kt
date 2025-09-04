package com.example.hellojetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hellojetpack.ui.theme.HelloJetpackTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloJetpackTheme {
                val counter = remember{
                    mutableStateOf(0)
                }
                val isRunning = remember {
                    mutableStateOf(false)
                }
                
                // Auto increment timer
                LaunchedEffect(isRunning.value) {
                    while (isRunning.value) {
                        delay(100) // Tăng số mỗi 100ms
                        counter.value += 1
                    }
                }
                
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "${counter.value}",
                            style = TextStyle(color = Color.Magenta,
                            fontSize = 35.sp,
                                fontWeight = FontWeight.ExtraBold))

                        Spacer(modifier = Modifier.height(130.dp))
                        CreateCircle(
                            counter = counter.value,
                            isRunning = isRunning.value
                        ){
                            isRunning.value = !isRunning.value
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CreateCircle(
    counter: Int=0, 
    isRunning: Boolean = false,
    toggleTimer: () -> Unit
){
    Card(modifier = Modifier
        .padding(3.dp)
        .size(105.dp)
        .clickable{
            toggleTimer()
    },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Text(
                text = if (isRunning) "Stop" else "Start",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloJetpackTheme {
        Greeting("Android")
    }
}