import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.LoginRequest
import com.example.simplenote.data.LoginResponse
import com.example.simplenote.network.BackendApi
import com.example.simplenote.network.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    private val api = BackendApi.api

    fun loginUser(
        username: String,
        password: String,
    ) {

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val request = LoginRequest(
                    username = username,
                    password = password,
                )
                val response: Response<LoginResponse> = api.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    SessionManager.saveTokens(body!!.access, body.refresh)
                    isSuccess = true
                } else {
                    errorMessage = response.errorBody()?.string() ?: "Login failed"
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