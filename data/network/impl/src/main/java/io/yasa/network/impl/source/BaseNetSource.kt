package io.yasa.network.impl.source

import android.util.MalformedJsonException
import io.yasa.common.exception.ApiException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class BaseNetSource<ApiService>(private val apiService: ApiService) {

    @Throws(ApiException::class, Throwable::class)
    protected open suspend fun <ResponseModel : Any> launch(
        request: suspend ApiService.() -> ResponseModel
    ): ResponseModel {
        return try {
            apiService.request()
        } catch (e: Exception) {
            throw parseApiErrorException(e)
        }
    }

    @Throws(Throwable::class)
    protected open fun parseApiErrorException(throwable: Throwable): ApiException {
        val apiException: ApiException
        if (throwable is HttpException) {
            val url = throwable.response()?.raw()?.request?.url.toString()
            apiException = ApiException(url, throwable.code(), throwable.message())
        } else {
            val apiErrorCode: Int = when (throwable) {
                is JSONException,
                is MalformedJsonException,
                is ConnectException -> ApiException.ErrorCode.SERVER_NOT_RESPONDING
                is SocketTimeoutException -> ApiException.ErrorCode.SOCKET_TIMEOUT
                else -> throw throwable
            }
            apiException = ApiException("", apiErrorCode, throwable.message.orEmpty(), throwable)
        }
        return apiException
    }

}
