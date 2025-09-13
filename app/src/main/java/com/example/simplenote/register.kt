package com.example.simplenote

import RegisterViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplenote.homenotes.Spacer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    toLoginClick: () -> Unit
) {
    val viewModel: RegisterViewModel = viewModel()

    // Trigger navigation on success
    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            // TODO: some user notify
            toLoginClick()
        }
    }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Login",
                tint = Color(0xFF5C4EE5),
                modifier = Modifier.clickable { toLoginClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Back to Login",
                color = Color(0xFF5C4EE5),
                modifier = Modifier.clickable { toLoginClick() })
        }

        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "And start taking notes",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )
        Text("First Name")
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = { Text("Example: Taha") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text("Last Name")
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = { Text("Example: Hamifar") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text("Username")
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("@HamifarTaha") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text("Email Address")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Example: hamifar.taha@gmail.com") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text("Password")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("********") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Text("Retype Password")
        OutlinedTextField(
            value = password2,
            onValueChange = { password2 = it },
            placeholder = { Text("********") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                viewModel.registerUser(firstName, lastName, username, email, password, password2)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C4EE5))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(if (viewModel.isLoading) "Registering..." else "Register", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Register",
                    tint = Color.White
                )
            }
        }

        // Error Display
        viewModel.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(" Or ", modifier = Modifier.padding(horizontal = 8.dp), color = Color.Gray)
            Divider(modifier = Modifier.weight(1f))
        }

        Text(
            text = "already have an account? Login here",
            color = Color(0xFF5C4EE5),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { toLoginClick() }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(fun() {})
}