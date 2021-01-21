package com.example.knxmanager.Model

import java.lang.Math.round


/* Kotlin data/model classes that map the JSON response, we could also add Moshi
 * annotations to help the compiler with the mappings on a production app */

data class ServerHello(
    val id: Int,
    val homeText: String,
    val time : String){

    fun getString() : String{
        return homeText + System.lineSeparator() + " server time: " + time
    }
}

data class KnxTelegram(
        var tid: Int,
        var timestampS: String,
        var timestamp: String?,
        var service: String,
        var frameFormat: String,
        var rawData: String,
        var rawDataLength: Int?,
        var fileName: String?)


data class KnxProcess(var pid : Int){
    var processName : String?
    var processIp : String?
    var processType : String?

    var processedFile : String?
    var actualTelegramNr : Int?
    var totalTelegramNr : Int?

    init{
        pid = 0
        processName = ""
        processIp = ""
        processType = ""
        processedFile = ""
        actualTelegramNr = 0
        totalTelegramNr = 0
    }

    fun getPercentage(): Float{
        var floatPercentage : Float = actualTelegramNr!!.toFloat()
        floatPercentage = totalTelegramNr?.let { floatPercentage?.div(it) }!!
        return floatPercentage
    }

    fun getPercentageText(): String{
        return round(getPercentageToChart()).toString() + "%"
    }


    fun getPercentageToChart(): Float{
        var floatPercentage : Float = actualTelegramNr!!.toFloat()
        if(this.totalTelegramNr?.equals(0)!!) {
            return 0f
        }
        return 100.0f * totalTelegramNr?.let { floatPercentage?.div(it) }!!
    }

    fun getProcessStatsText(): String{
        return actualTelegramNr.toString() + "/" + totalTelegramNr.toString()
    }
}