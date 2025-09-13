import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.ChangePasswordRequest
import com.example.simplenote.data.ChangePasswordResponse
import com.example.simplenote.network.BackendApi
import com.example.simplenote.network.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    private val api = BackendApi.api

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        newPassword2: String,
    ) {
        if (newPassword != newPassword2){
            errorMessage = "Passwords do not match!"
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val request = ChangePasswordRequest(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                )
                val response: Response<ChangePasswordResponse> = api.changePassword(
                    token = "Bearer ${SessionManager.fetchAccessToken()}",
                    request = request
                )
                if (response.isSuccessful) {
                    isSuccess = true
                } else {
                    errorMessage = response.errorBody()?.string() ?: "Change Password failed"
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