package com.antoniosj.composerecipeapp.presentation.ui.recipelist

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()
}