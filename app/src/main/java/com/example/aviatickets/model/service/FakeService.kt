    package com.example.aviatickets.model.service
    import com.google.gson.Gson
    import com.google.gson.GsonBuilder
    import retrofit2.Call
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory
    import retrofit2.http.GET

    import com.example.aviatickets.model.entity.Airline
    import com.example.aviatickets.model.entity.Flight
    import com.example.aviatickets.model.entity.Location
    import com.example.aviatickets.model.entity.Offer
    import java.util.UUID

    interface ApiService {

        @GET("your_api_endpoint") // Replace with your actual API endpoint
        fun getOffers(): Call<List<Offer>>
    }

    object RetrofitService {

        private const val BASE_URL = "https://your_base_url/" // Replace with your actual base URL

        private val gson: Gson = GsonBuilder().setLenient().create()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }

    object NetworkManager {

        // Function to fetch offer list using Retrofit
        private fun fetchOffers(): List<Offer>? {
            val call = RetrofitService.apiService.getOffers()

            return try {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        // Function to get the combined offer list
        fun getCombinedOfferList(): List<Offer> {
            val offerList = fetchOffers()
            return offerList ?: emptyList()
        }
    }
