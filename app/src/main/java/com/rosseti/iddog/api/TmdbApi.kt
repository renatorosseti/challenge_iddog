package com.rosseti.iddog.api


import com.google.gson.annotations.SerializedName
import com.rosseti.iddog.model.LoginPostData
import com.rosseti.iddog.model.SignUpResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface TmdbApi {

    companion object {
        const val URL = "https://iddog-nrizncxqba-uc.a.run.app/"
        const val contentType = "application/json"
    }

    @POST("signup2")
    @Headers("Content-Type: application/json")
    fun signUp(
            @Body loginPostData: LoginPostData
    ): Observable<SignUpResponse>


}
