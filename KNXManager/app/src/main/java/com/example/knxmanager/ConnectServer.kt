package com.example.knxmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.*

class ConnectServer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_server)

    }


    fun testServerConnection(view: View) {

        var serverIpET = findViewById<EditText>(R.id.editTextTextServerIp)
        var serverPortET = findViewById<EditText>(R.id.editTextTextServerPort)
        var serverResponseT = findViewById<TextView>(R.id.textView3Response)

        var urlString : String = "https://" + serverIpET.text + ":" + serverPortET.text
        var srvResponseString = ""
        CoroutineScope(Dispatchers.IO).launch {
            serverResponseT.text = srvResponseString

            /* Creates an instance of the UserService using a simple Retrofit builder using Moshi
    * as a JSON converter, this will append the endpoints set on the UserService interface
    * (for example '/api', '/api?results=2') with the base URL set here, resulting on the
    * full URL that will be called: 'https://randomuser.me/api' */
            val service = Retrofit.Builder()
                    .baseUrl(urlString)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(UserService::class.java)

            /* Uses the lifecycle scope to trigger the coroutine. It's important to call this
             * using a scope to follow the structured concurrency principle.
             * https://medium.com/@elizarov/structured-concurrency-722d765aa952
             * https://developer.android.com/topic/libraries/architecture/coroutines */
            lifecycleScope.launch {
                val users : ServerHello = service.getUsers()
                /* This will print the result of the network call to the Logcat. This runs on the
                 * main thread */
                srvResponseString = users.toString()
                serverResponseT.text = srvResponseString
            }
        }


    }
}

/* Kotlin data/model classes that map the JSON response, we could also add Moshi
 * annotations to help the compiler with the mappings on a production app */
data class UserResponse(val results: List<User>)
data class User(val email: String, val phone: String)


data class ServerHello(val ID: Int, val HomeText: String?, val ServerTime : Date?)


/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */
interface UserService {
    @GET("/api")
    suspend fun getUsers(): ServerHello
}