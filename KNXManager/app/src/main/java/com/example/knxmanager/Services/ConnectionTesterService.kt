package com.example.knxmanager.Services

import com.example.knxmanager.Model.ServerHello
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*
import kotlin.jvm.Throws

open class ConnectionTesterService{

    // get Hello from server
    suspend fun testServerConnection(urlString : String) : String {

        var srvResponseString = ""
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
        //lifecycleScope.launch {
            try{
                val users = service.getServerHello()
                srvResponseString = getServerHelloText(users[0])
            }catch(e:Exception){
                srvResponseString = e.message.toString()
            }
            /* This will print the result of the network call to the Logcat. This runs on the
             * main thread */
        //}

        return srvResponseString;
    }

    fun getServerHelloText(response : ServerHello ) : String{
        return try {
            response.getString()
        }catch (e : Exception){
            e.message.toString()
        }
    }

    // Omit certs
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



/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */
interface ServerHelloService {
    @GET("/api")
    suspend fun getServerHello(): List<ServerHello>
}