import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.util.*


object ServiceBuilder {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(DefaultInterceptor()).build()

    val gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, UnixTimestampAdapter())
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://hse-forum-backend.herokuapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}

private class DefaultInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Headers.Builder = Headers.Builder()
        val token = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW5tb3NvbGtvdiIsImV4cCI6MTY1MDY2OTA5NywiaWF0IjoxNjUwNTc5MDk3LCJzY29wZSI6IlJPTEVfVVNFUiJ9.SoslbQ4--TmeDXq1i70cx2z6dZ_-YGnpy90Xg0vtGceZhyYe8xXDLFPdlRqLCW8djVBk_1rMhnY0Tum0AhMEsix-U8tig3Z5TCFE202ljiSHZW2J3TvPxcNoMKEcf9KdJkQ7_kSeDsNw6QBjyqyjD8PoPC0azmuEzy7G8tFHNNKmjHlf-FBW61zmaQYtkneZqeDYAiNSyikp3ux7DyQ028q2tlh6JVJ01QoLxkyqnQoSUCKgX_pbOR642D6uDm-eYQIetGoYs6krehmOvmJEwkpgOPxhgcZpD4ifCrUqSz42dQIOdRNveduBzEqf4XGSm80PPLG8GbYdt3fcUAInSg"
//                "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW5tb3NvbGtvdiIsImV4cCI6MTY1MDYyNjU1NCwiaWF0IjoxNjUwNTM2NTU0LCJzY29wZSI6IlJPTEVfVVNFUiJ9.KO_OIfEd2W6u2PRsuo1AgwvSsq4fjIV8Dmzo83pK5hV7aRAlOOX9Ncvzn66WGCXiG3WHkTku4npP3qcdniweOv1ziiSo9UxdkprSUmIeYYA8lTNFR5c5yZTmSU5YEWWPK9mYyUku4-_ZiBvAiIMuK5AGTNGqmrMRGtJfrAOvfn9OpcnsNS18h1pLoO0LQkfCPDx5x_DnREJm5h4A-lV8AwmkxErZELPDGf80sU4HR2S_5gp8gM6T0dyGNH7JAKUDMrA7AmTwPeyq_q5HgH9jedIakYoizSwa-dxCyUsuodqIdUCbiKpcE1o_y4H78UAO5_EK_RXCO_2OUrp7dh-fvg"
        builder.add("Content-Type", "application/json;charset=UTF-8")
        builder.add("Accept", "*/*")
        builder.add("Accept-Encoding", "gzip, deflate, br")
        builder.add("Connection", "keep-alive")
        builder.add("Authorization", token)
        return chain.proceed(
            chain.request().newBuilder()
                .headers(builder.build())
                .build()
        )

    }
}

class UnixTimestampAdapter : TypeAdapter<Date?>() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(value.time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun read(rin: JsonReader?): Date? {
        return if (rin == null) {
            null
        } else Date.from(Instant.ofEpochMilli(rin.nextString().split('.')[0].toLong()))
    }
}