package com.app.eho.data.remote

import com.app.eho.data.remote.request.DummyRequest
import com.app.eho.data.remote.request.LoginRequest
import com.app.eho.data.remote.request.RegisterRequest
import com.app.eho.data.remote.response.DummyResponse
import com.app.eho.data.remote.response.LoginResponse
import com.app.eho.data.remote.response.RegisterResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit layer
 */

interface NetworkService {

    @POST(Endpoints.API_REGISTER)
    fun doRegister(
        @Body request: RegisterRequest
    ) : Single<RegisterResponse>

    @POST(Endpoints.DUMMY)
    fun doDummyCall(
        @Body request: DummyRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<DummyResponse>

    /*
     * Example to add other headers
     *
     *  @POST(Endpoints.DUMMY_PROTECTED)
        fun sampleDummyProtectedCall(
            @Body request: DummyRequest,
            @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
            @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
        ): Single<DummyResponse>
     *//*
     */

    @POST(Endpoints.LOGIN)
    fun doLoginCall(
        @Body request: LoginRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<LoginResponse>
}