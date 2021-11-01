package org.acme.model.proxy

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.acme.model.error.ApiErrorResponse
import org.acme.model.error.ErrorCode

@JsonIgnoreProperties(ignoreUnknown = true)
data class Country(
    var name: Name
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Name(
    var common: String,
    var official: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CountryApiError(var code: ErrorCode = ErrorCode.DEFAULT, val status: Int = -1, override val message: String = "") : RuntimeException(message) {
    fun toApiError(): ApiErrorResponse = ApiErrorResponse(code.value, status, message)
}