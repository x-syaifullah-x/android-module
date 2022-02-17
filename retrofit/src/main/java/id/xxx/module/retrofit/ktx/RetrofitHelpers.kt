package id.xxx.module.retrofit.ktx

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

object RetrofitHelpers {

    inline fun <reified T> errorConverter(retrofit: Retrofit): Converter<ResponseBody, T> =
        retrofit.responseBodyConverter(T::class.java, arrayOf())

    inline fun <reified T> create(retrofit: Retrofit): T = retrofit.create(T::class.java)
}