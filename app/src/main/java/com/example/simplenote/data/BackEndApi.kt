import com.example.simplenote.data.SimpleNoteApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlin.getValue

object BackEndApi {
    private const val BASE_URL = "http://127.0.0.1:8000"  // Replace with actual base URL

    val api: SimpleNoteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())  // For suspend functions
            .build()
            .create(SimpleNoteApi::class.java)
    }
}