package com.example.simplenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val notesVm: NotesViewModel = viewModel()
    val navController = rememberNavController()

    var startDist = "onboarding"
    if (SessionManager.isLoggedIn())
        startDist = "home"
    NavHost(navController, startDestination = startDist) {
        composable("onboarding") {
            OnboardingScreen { navController.navigate("login") }
        }
        composable("login") {
            LoginScreen({ navController.navigate("home") }) {
                navController.navigate(
                    "register"
                )
            }
        }
        composable("register") {
            RegisterScreen { navController.navigate("login") }
        }
        composable("home") {
            // بار اول که وارد می‌شویم، نوت‌ها را لود کن
            LaunchedEffect(Unit) { notesVm.loadNotes() }

            NotesHubScreen(
                notes = notesVm.notes, // ← دیگر لیست ثابت نیست
                onOpenNote = { noteId ->
                    if (noteId.isNullOrBlank()) {
                        navController.navigate("note") // add new
                    } else {
                        navController.navigate("note/${noteId}") // edit
                    }
                },
                onHome = { /* همین صفحه‌ایم */ },
                onSettings = { navController.navigate("settings") }
            )
        }
        composable("note") {
            // حالت Add (بدون id)
            NoteScreen(
                noteId = null,
                loadNote = { _ -> null },
                onSave = { note -> notesVm.save(note) { navController.popBackStack() } },
                onDelete = { /* در حالت add دلیت نداریم */ },
                onBack = { navController.popBackStack() }
            )
        }
        composable("note/{noteId}") { backStack ->
            val noteId = backStack.arguments?.getString("noteId")
            NoteScreen(
                noteId = noteId,
                loadNote = { id -> notesVm.loadOne(id) },
                onSave = { note -> notesVm.save(note) { navController.popBackStack() } },
                onDelete = { id -> notesVm.delete(id) { navController.popBackStack() } },
                onBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsActivity(
                Modifier,
                { navController.navigate("home") },
                { navController.navigate("changePassword") },
                {
                    SessionManager.clearTokens()
                    navController.navigate("login")
                })
        }
        composable("changePassword") {
            ChangePasswordActivity(Modifier) { navController.navigate("settings") }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    NavApp(modifier)
}