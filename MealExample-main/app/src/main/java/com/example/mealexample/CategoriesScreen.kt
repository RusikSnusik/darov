package com.example.mealexample

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage


@Composable
fun NavigationScreen(modifier: Modifier){
    val navigationController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    NavHost(
        modifier = modifier,
        navController = navigationController,
        startDestination = ScreenRoute.mainScreen.route
    ){
        composable(route = ScreenRoute.mainScreen.route){
            CategoriesScreen(viewModel = viewModel, navHostController = navigationController)
        }
        composable(route = "${ScreenRoute.categoryScreen.route}"){

            CategoryScreen(viewModel)
        }


    }
}

@Composable
fun CategoriesScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = viewModel()
){

    val categoryState by viewModel.categoriesState.collectAsState()
    if (categoryState.loading){
        LoadingScreen()
    }
    if (categoryState.list.isNotEmpty()){
        CategoriesList(
            viewModel,
            navHostController,
            categoryState.list
        )
    }
    if (categoryState.error != null){
        ErrorScreen(categoryState.error!!)
    }
}

@Composable
fun CategoriesList(viewModel: MainViewModel, navHostController: NavHostController, categoriesList: List<Category>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)) {
        items(categoriesList){
            CategoryItem(viewModel,navHostController, it)
        }
    }
}

@Composable
fun CategoryItem(viewModel: MainViewModel, navHostController: NavHostController, category: Category) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .border(BorderStroke(2.dp, Color.White)) // Добавляем рамку
            .background(color = Color.DarkGray)
            .clickable {
                viewModel.setChosenCategory(category.strCategory)
                navHostController.navigate("${ScreenRoute.categoryScreen.route}")
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = category.strCategoryThumb,
                contentDescription = null
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = category.strCategory,
                color = Color.White
            )
        }
    }
}
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading...",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ErrorScreen(message: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = message)
    }
}

@Composable
fun MealDetailScreen(mealId: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Details for meal ID: $mealId")
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){

}