import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.UserInfoResponse
import com.example.simplenote.network.BackendApi
import com.example.simplenote.network.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Response

class SettingsViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)
    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")

    private val api = BackendApi.api

    fun fetchUser() {

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response: Response<UserInfoResponse> = api.getUserInfo(
                    token = "Bearer ${SessionManager.fetchAccessToken()}"
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    username = body?.username ?: "User"
                    name = body?.firstName + body?.lastName + '(' + username + ')'
                    email = body?.email ?: ""
                    isSuccess = true
                } else {
                    errorMessage = response.errorBody()?.string() ?: "fetch failed"
                    Log.i("ali","fail to fetch user $errorMessage")
                    if (errorMessage!!.contains("token_not_valid")){
                        // TODO: logout here or refresh token
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