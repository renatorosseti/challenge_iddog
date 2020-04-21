package com.rosseti.iddog.api

import com.rosseti.iddog.model.SignUpResponse
import io.reactivex.Single
import retrofit2.http.*

interface IddogApi {

    companion object {
        const val URL = "https://iddog-nrizncxqba-uc.a.run.app/"
    }

    @POST("signup")
    @FormUrlEncoded
    fun signUp(
            @Field("email") email: String
    ): Single<SignUpResponse>


}
