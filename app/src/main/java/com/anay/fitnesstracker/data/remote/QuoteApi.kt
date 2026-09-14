package com.anay.fitnesstracker.data.remote

import com.anay.fitnesstracker.data.model.Quote
import retrofit2.http.GET

interface QuoteApi {

    @GET("quotes/random")
    suspend fun getRandomQuote(): Quote
}