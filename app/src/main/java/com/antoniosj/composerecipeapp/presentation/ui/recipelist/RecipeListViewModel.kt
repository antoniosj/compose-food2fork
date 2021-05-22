package com.antoniosj.composerecipeapp.presentation.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniosj.composerecipeapp.domain.model.Recipe
import com.antoniosj.composerecipeapp.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

class RecipeListViewModel @ViewModelInject constructor(
    private val randomString: String,
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String
): ViewModel() {

//    private val _recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
//
//    val recipes: LiveData<List<Recipe>>
//        get() = _recipes

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0
    val loading = mutableStateOf(false)

    // Pagination starts at '1'
    val page = mutableStateOf(1)
    var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {

            loading.value = true

            resetSearchState()

            delay(1000)

            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result

            loading.value = false
        }
    }


    fun nextPage(){
        viewModelScope.launch {
            // prevent duplicate event due to recompose happening to quickly
            if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE) ){
                loading.value = true
                incrementPage()


                // just to show pagination, api is fast
                delay(1000)

                if(page.value > 1){
                    val result = repository.search(token = token, page = page.value, query = query.value )

                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListScrollPosition = position
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    //categoryScrollPosition setter
    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }

}