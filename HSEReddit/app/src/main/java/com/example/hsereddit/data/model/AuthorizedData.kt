package com.example.hsereddit.data.model

import UnixTimestampAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

data class AuthorizedData(
    val authenticationToken: String,
    @JsonAdapter(UnixTimestampAdapter::class) val expiresAt: Date,
    val refreshToken: String,
    val username: String
)