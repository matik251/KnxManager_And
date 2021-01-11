package com.example.knxmanager

import android.net.Uri
import android.util.Log
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOError
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlinx.serialization.json.*
import java.sql.Time
import java.util.*



@Deprecated
public class ServiceChecker {
/*
    public fun getUrlBytes(urlSpec: String) : ByteArray{
        val url = URL(urlSpec)
        val connection:HttpsURLConnection =  url.openConnection() as HttpsURLConnection
        try {
            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException(connection.responseMessage)
            }

            var bytesRead: Int
            val buffer = ByteArray(1024)

            do {
                bytesRead = input.read(buffer)
                out.write(buffer, 0, bytesRead)
            } while (input.read(buffer) > 0)
            out.close()
            return out.toByteArray()
        }catch (e: IOException) {
            Log.e("HTTP_CONN_ERR", e.message.toString())
            return ByteArray(0)
        }finally {
            connection.disconnect()
        }
    }

    public fun getUrlString(urlSpec: String) : String{
        return String(getUrlBytes(urlSpec))
    }

    public fun getJSONString(urlSpec: String) : String{
        var jsonString = ""
        try{
            val url : String = Uri.parse(urlSpec + "")
                    .buildUpon()
                    .appendQueryParameter("format","json")
                    .appendQueryParameter("nojsoncallback","1")
                    .appendQueryParameter("extras","url_s")
                .build().toString()

            jsonString = getUrlString(url)

            return jsonString
        }catch(je : JSONException){
            Log.e("JSON_PARSING_ERROR", je.message.toString())
        }
        return jsonString
    }*/
}