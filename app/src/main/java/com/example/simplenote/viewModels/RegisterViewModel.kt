import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.RegisterRequest
import com.example.simplenote.data.RegisterResponse
import com.example.simplenote.network.BackendApi
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    // UI states
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    private val api = BackendApi.api

    fun registerUser(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String,
        password2: String
    ) {
        if (password != password2) {
            errorMessage = "Passwords do not match!"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val request = RegisterRequest(
                    username = username,
                    password = password,
                    email = email,
                    firstName = firstName.takeIf { it.isNotBlank() },
                    lastName = lastName.takeIf { it.isNotBlank() }
                )
                val response: Response<RegisterResponse> = api.register(request)

                if (response.isSuccessful) {
                    // Handle success (e.g., save user data, navigate)
                    isSuccess = true
                } else {
                    // Handle API errors (e.g., 400 validation)
                    errorMessage = response.errorBody()?.string()
                    if (errorMessage != null) {
                        val ali = JSONObject(errorMessage)
                        val errors = ali.getJSONArray("errors")
                        val g = (0 until errors.length()).map { i ->
                            errors.getJSONObject(i).getString("attr") + ": " +
                                    errors.getJSONObject(i).getString("detail")
                        }
                        errorMessage = g.joinToString("\n")
                    }
                }
            } catch (e: Exception) {
                // Handle network/other exceptions
                errorMessage = "Network error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}