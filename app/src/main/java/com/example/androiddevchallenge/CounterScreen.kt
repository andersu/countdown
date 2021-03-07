/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
