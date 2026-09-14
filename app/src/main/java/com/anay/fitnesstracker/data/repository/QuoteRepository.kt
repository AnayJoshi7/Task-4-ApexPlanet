package com.anay.fitnesstracker.data.repository

import com.anay.fitnesstracker.data.model.Quote
import com.anay.fitnesstracker.data.remote.RetrofitInstance

class QuoteRepository {

    suspend fun getRandomQuote(): Quote {
        return RetrofitInstance.quoteApi.getRandomQuote()
    }
}