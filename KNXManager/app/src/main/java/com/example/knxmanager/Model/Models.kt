package com.example.knxmanager.Model

import java.lang.Math.round


/* Kotlin data/model classes that map the JSON response, we could also add Moshi
 * annotations to help the compiler with the mappings on a production app */

data class ServerHello(
    val id: Int,
    val homeText: String,
    val time : String)

data class KnxTelegram(
    var Tid: Long?,
    var TimestampS: String?,
    var Timestamp: String?,
    var Service: String?,
    var FrameFormat: String?,
    var RawData: String?,
    var RawDataLength: Int?,
    var FileName: String?)


data class KnxProcess(var ProcessId : Int){
    var ProcessName : String?
    var ProcessIp : String?
    var ProcessType : String?

    var ProcessedFile : String?
    var AcutalTelegramNr : Int?
    var TotalTelegramNr : Int?

    init{
        ProcessId = 123
        ProcessName = "Testowy"
        ProcessIp = "255.255.255.0"
        ProcessType = "Testowy"
        ProcessedFile = "Testsowy.xml"
        AcutalTelegramNr = 30
        TotalTelegramNr = 100
    }

    fun getPercentage(): Float{
        var floatPercentage : Float = AcutalTelegramNr!!.toFloat()
        floatPercentage = TotalTelegramNr?.let { floatPercentage?.div(it) }!!
        return floatPercentage
    }

    fun getPercentageText(): String{
        return round(getPercentageToChart()).toString() + "%"
    }


    fun getPercentageToChart(): Float{
        var floatPercentage : Float = AcutalTelegramNr!!.toFloat()
        floatPercentage = TotalTelegramNr?.let { floatPercentage?.div(it) }!!
        return floatPercentage * 100.0f
    }

    fun getProcessStatsText(): String{
        return AcutalTelegramNr.toString() + "/" + TotalTelegramNr.toString()
    }
}