package com.example.musicandfilm.models.events.roomtables

import com.google.gson.annotations.SerializedName


data class DatesRoom (

    @SerializedName("start" ) var start : String,
    @SerializedName("end"   ) var end   : String

)