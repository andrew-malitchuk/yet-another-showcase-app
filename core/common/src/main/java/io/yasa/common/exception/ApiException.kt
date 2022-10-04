package io.yasa.common.exception

open class ApiException(
    method: String,
    httpCode: Int,
    details: String,
    override val cause: Throwable? = null
) : RuntimeException(
    "Api: method=[$method], code=[$httpCode], details=[$details]",
    cause
) {
    companion object ErrorCode {
        const val UNKNOWN = -1
        const val JSON_PARSE = 1
        const val SERVER_NOT_RESPONDING = 2
        const val SOCKET_TIMEOUT = 3
        const val NOT_FOUND = 404
    }
}