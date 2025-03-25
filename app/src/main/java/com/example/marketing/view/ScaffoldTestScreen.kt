package com.example.marketing.view


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material3.AlertDialog
import com.example.marketing.ui.component.MainTopBar
import kotlinx.coroutines.launch

@Composable
fun ScaffoldTestScreen() {

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {
                        dialogMessage = "Home clicked"
                        showDialog = true
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "home") },
                    label = { Text("Home") }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                dialogMessage = "Floating Action Button clicked"
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = { innerPadding ->
            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("This is a snackbar message!")
                    }
                },
                modifier = Modifier.padding(innerPadding)
            ) {
                Text("Show Snackbar")
            }

        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Dialog") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("확인")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    ScaffoldTestScreen()
}