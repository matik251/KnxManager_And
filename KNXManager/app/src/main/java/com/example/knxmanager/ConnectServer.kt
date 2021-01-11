package com.example.knxmanager

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*
import kotlin.jvm.Throws


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
        //CoroutineScope(Dispatchers.IO).launch {
            serverResponseT.text = srvResponseString

        // Loa

            /* Creates an instance of the UserService using a simple Retrofit builder using Moshi
    * as a JSON converter, this will append the endpoints set on the UserService interface
    * (for example '/api', '/api?results=2') with the base URL set here, resulting on the
    * full URL that will be called: 'https://randomuser.me/api' */
            val service = Retrofit.Builder()
                    .baseUrl(urlString)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(getUnsafeOkHttpClient()?.build())
                    .build()
                    .create(ServerHelloService::class.java)

            /* Uses the lifecycle scope to trigger the coroutine. It's important to call this
             * using a scope to follow the structured concurrency principle.
             * https://medium.com/@elizarov/structured-concurrency-722d765aa952
             * https://developer.android.com/topic/libraries/architecture/coroutines */
            lifecycleScope.launch {
                try{
                    val users = service.getServerHello()
                    srvResponseString = users.toString()
                }catch(e:Exception){
                    srvResponseString = e.message.toString()
                }
                /* This will print the result of the network call to the Logcat. This runs on the
                 * main thread */
                serverResponseT.text = srvResponseString
            }
        //}
    }

    /*
    * Omit certs
    * */
    fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            builder
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }
}

/* Kotlin data/model classes that map the JSON response, we could also add Moshi
 * annotations to help the compiler with the mappings on a production app */

data class ServerHello(val id: Int, val homeText: String, val time : String)


/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */
interface ServerHelloService {
    @GET("/api")
    suspend fun getServerHello(): List<ServerHello>
}