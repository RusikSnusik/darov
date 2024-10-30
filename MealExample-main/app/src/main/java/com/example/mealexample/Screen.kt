package com.example.mealexample

object ScreenRoute {
    val mainScreen = Screen(route = "MainScreen")
    val categoryScreen = Screen(route = "CategoryScreen")
    val mealDetailScreen = Screen(route = "MealDetailScreen/{mealId}")
}

data class Screen(
    val route: String
)