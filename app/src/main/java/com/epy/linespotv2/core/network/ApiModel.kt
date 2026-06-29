package com.epy.linespotv2.core.network

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ApiEnvelope<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("service") val service: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: T? = null
)

data class ApiJobEnvelope(
    @SerializedName("queued") val queued: Boolean = false,
    @SerializedName("job") val job: QueuedJobDto? = null
)

data class QueuedJobDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("topic") val topic: String? = null,
    @SerializedName("last_error") val lastError: String? = null,
    @SerializedName("result_payload") val resultPayload: JsonObject? = null
)