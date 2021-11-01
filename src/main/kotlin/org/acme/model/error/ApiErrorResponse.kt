package org.acme.model.error

data class ApiErrorResponse(var code: Int, var status: Int, var message: String)


enum class ErrorCode(val value: Int) {
    DEFAULT(-1),
    COUNTRY_API_CLIENT_ERROR(1000),
    COUNTRY_API_SERVER_ERROR(1001)
}