package com.example.mealexample

import android.util.Log
import androidx.annotation.StyleRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.Exception

class MainViewModel : ViewModel() {

    private val _categorieState = MutableStateFlow(RecipeState())
    val categoriesState = _categorieState.asStateFlow()
    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()
    private val _chosenCategory = MutableStateFlow("")
    val chosenCategory = _chosenCategory.asStateFlow()

    init {
        fetchCategories()
    }

    fun setChosenCategory(categoryName: String){
        _chosenCategory.value = categoryName
    }

    fun getCategory(id: String){
        viewModelScope.launch {
            try {
                val categoryResponse = MealService.service.getMealsByCategory(id)
                Log.e("CategoryResponse", "CategoryList: ${categoryResponse.meals}")
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    meals = categoryResponse.meals
                )
            } catch (e: Exception){
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }


    private fun fetchCategories(){
        viewModelScope.launch{
            try {
                val response = MealService.service.getCategories()
                _categorieState.value = _categorieState.value.copy(loading = false, list = response.categories)

            }catch (e: Exception){
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    error = "Loading failed"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
    data class CategoryState(
        val loading: Boolean = true,
        val meals: List<Meal> = emptyList(),
        val error: String? = null
    )
}