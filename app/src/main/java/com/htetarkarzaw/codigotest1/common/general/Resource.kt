package com.htetarkarzaw.codigotest1.common.general

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : Resource<T>()
    class Error<T>(error: String) : Resource<T>(null, error)
    class Success<T>(data: T) : Resource<T>(data)

}