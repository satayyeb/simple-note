package com.example.simplenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplenote.network.SessionManager
import com.example.simplenote.ui.theme.SimpleNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(this)
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
fun NavApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    var startDist = "onboarding"
    if (SessionManager.isLoggedIn())
        startDist = "home"
    NavHost(navController, startDestination = startDist) {
        composable("onboarding") {
            OnboardingScreen { navController.navigate("login") }
        }
        composable("login") {
            LoginScreen({ navController.navigate("home") }) { navController.navigate("register") }
        }
        composable("register") {
            RegisterScreen { navController.navigate("login") }
        }
        composable("home") {
            Hello()
        }
    }
}

@Composable
fun Hello() {
    Text("hello world! You are login now.")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    NavApp(modifier)
}