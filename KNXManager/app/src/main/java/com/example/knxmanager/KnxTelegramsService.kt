package com.example.knxmanager

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.net.ssl.*
import kotlin.jvm.Throws

class KnxTelegramsService(urlString : String) {
    // get Hello from server
    suspend fun testServerConnection(urlString : String) : List<KnxTelegram> {

        var srvResponse :List<KnxTelegram> = ArrayList<KnxTelegram>(0)
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
            srvResponse = getKnxTelegrams()
        }catch(e:Exception){
        }
        /* This will print the result of the network call to the Logcat. This runs on the
         * main thread */
        //}

        return srvResponse;
    }

    fun getKnxTelegrams() : List<KnxTelegram>{
        try {
            //TODO napisac serwis do telegramów
            var number = 2
            var test : Int? = 123
            val telegram : KnxTelegram = KnxTelegram( number.toLong(),"LocalDateTime.now().toString()", LocalDateTime.now().toString(),"DummyService","Cemi","0123456789ABCDEF", test,"test.xml")
            val temp : MutableList<KnxTelegram> = ArrayList<KnxTelegram>(0)
            temp.add(telegram)
            return temp
        }catch (e : Exception){
            Log.e("Exception", e.message.toString())
            val temp : MutableList<KnxTelegram> = ArrayList<KnxTelegram>(0)
            return temp
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