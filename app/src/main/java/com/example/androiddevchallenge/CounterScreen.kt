package com.example.androiddevchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.placeholder

@Composable
fun CounterScreen(viewModel: CounterViewModel = CounterViewModel()) {
    val counterTextStyle = TextStyle(
        fontSize = 100.sp,
        textAlign = TextAlign.Center,
        background = Color.Transparent,
        color = MaterialTheme.colors.onSurface,
    )

    val seconds: Int by viewModel.seconds.observeAsState(0)
    var buttonText by remember { mutableStateOf(R.string.start) }

    val timerFinished: Boolean by viewModel.timerFinished.observeAsState(false)
    if (timerFinished) {
        buttonText = R.string.start
    }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            TextField(
                colors = textFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    backgroundColor = MaterialTheme.colors.background
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "10",
                        modifier = Modifier.fillMaxWidth(),
                        style = counterTextStyle.copy(color = MaterialTheme.colors.placeholder())
                    )
                },
                value = if (seconds == 0) "" else "$seconds",
                textStyle = counterTextStyle,
                onValueChange = {
                    viewModel.onSetTimer(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = {
                    buttonText = if (buttonText == R.string.start) {
                        viewModel.onStartClick()
                        R.string.pause
                    } else {
                        viewModel.onPauseClick()
                        R.string.start
                    }
                },
                content = {
                    Text(
                        text = stringResource(buttonText)
                    )
                }
            )
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
private fun LightPreview() {
    MyTheme {
        CounterScreen()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
private fun DarkPreview() {
    MyTheme(darkTheme = true) {
        CounterScreen()
    }
}