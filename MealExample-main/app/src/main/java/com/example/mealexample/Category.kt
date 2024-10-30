package com.example.mealexample

import com.google.gson.annotations.SerializedName

data class Category(val idCategory:String,
                    val strCategory: String,
                    val strCategoryThumb: String,
                    val strCategoryDescription: String
)

data class Meal(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String,
)

data class CategoriesResponse(val categories: List<Category>)
data class MealResponse(
    val meals: List<Meal>
)

