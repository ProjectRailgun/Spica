package co.railgun.common.model

import com.google.gson.annotations.SerializedName

data class DnsResponse (
        @SerializedName("Status")
        val status: String,
        @SerializedName("Answer")
        val answer: List<Answer>
) {
    data class Answer(val name: String, val type: Int, val TTL: String, val data: String)
}