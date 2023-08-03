package com.example.myfirstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreen() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val count = remember { mutableStateOf(0) }
    val checked = remember { mutableStateOf(true) }
    val drawerList = mutableListOf("Главная", "Контакты", "О приложении")
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = Color.Black,
                    contentColor = Color.White,
                    action = {
                        Column(modifier = Modifier.padding(10.dp)) {
                            TextButton(onClick = { data.performAction() }) {
                                Text("Add click", fontSize = 22.sp, color = Color.White) }

                        }
                    }

                ) {
                    Text("Your clicks", fontSize = 20.sp)
                }
            }
        },
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ) {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) { Icon(Icons.Filled.Menu, contentDescription = "Кнопка меню") }
                Text(text = "Jetpack Compose")
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Info,
                        contentDescription = "Кнопка инфо"
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        checked.value = !checked.value
                        val result = scaffoldState.snackbarHostState.showSnackbar("")
                        if (result == SnackbarResult.ActionPerformed) {
                            count.value++
                            checked.value = !checked.value
                        } else if (result == SnackbarResult.Dismissed) {
                            checked.value = !checked.value
                        }
                    }
                },
                content = {
                    if (checked.value) Icon(
                        Icons.Filled.Add,
                        contentDescription = "Кнопка добавить"
                    )
                    else Icon(Icons.Filled.Clear, contentDescription = "Кнопка удалить")
                },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        },
        drawerContent = {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = "Меню", fontSize = 28.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            ) {paddingValue ->
                Column(modifier = Modifier.padding(paddingValue)) {
                    for(item in 0 until drawerList.size) {
                        Row {
                            Text(
                                text = drawerList[item],
                                fontSize = 28.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f, true))
                            IconButton(
                                onClick = {}) {
                                Icon(Icons.Filled.Delete, contentDescription = "Кнопка удалить")
                            }
                        }
                    }
                }

            }
        }

    ) {paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            Text(
                text = "Click: ${count.value}",
                fontSize = 28.sp,
                modifier = Modifier.padding(10.dp)
            )

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 20.dp, y = 10.dp)
                ) {
                    DropdownMenuItem(onClick = { }) {
                        Text("Скопировать")
                    }
                    DropdownMenuItem(onClick = { }) {
                        Text("Вставить")
                    }
                    Divider()
                    DropdownMenuItem(onClick = { }) {
                        Text("Настройки")
                    }
                }
            }



        }
    }
}
