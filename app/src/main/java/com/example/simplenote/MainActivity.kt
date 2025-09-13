package com.example.simplenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
fun NavApp(modifier: Modifier = Modifier) {
    val loginApi = fun(a: String, b: String) {
        print("login")
    }
    val navController = rememberNavController()
    NavHost(navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnboardingScreen { navController.navigate("login") }
        }
        composable("login") {
            LoginScreen(loginApi) { navController.navigate("register") }
        }
        composable("register") {
            RegisterScreen { navController.navigate("login") }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    NavApp(modifier)
}