package com.example.mealexample



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object MealService{
    val service: ApiService by lazy {
        Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/").addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(ApiService::class.java)
    }
}

interface ApiService{
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String) : MealResponse
}

