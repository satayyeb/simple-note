package com.example.simplenote

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.simplenote.data.ApiHandler
import com.example.simplenote.ui.theme.SimpleNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleNoteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Fazel",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val json = """
    {
        "username": "fazelii",
        "password": "Admin@83",
        "email": "user3@example.com",
        "first_name": "fazel",
        "last_name": "fazeli"
    }
""".trimIndent()

    ApiHandler().post(
        context = LocalContext.current,
        path = "auth/register/",
        needToken = false,
        jsonBody = json
    ) {

        Log.d("POST Response", it ?: "No response")
    }

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}