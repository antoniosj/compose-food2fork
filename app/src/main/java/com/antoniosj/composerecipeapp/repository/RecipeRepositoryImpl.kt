package com.antoniosj.composerecipeapp.repository

import com.antoniosj.composerecipeapp.domain.model.Recipe
import com.antoniosj.composerecipeapp.network.RecipeService
import com.antoniosj.composerecipeapp.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(
            recipeService.search(token, page, query).recipes
        )
    }

    override suspend fun get(token: String, id: Int): Recipe {
        //val recipeDto: RecipeDto = recipeService.get(token, id)
        //mapper.mapToDomainModel(recipeDto)
        // same as above
        return mapper.mapToDomainModel(recipeService.get(token,id))
    }
}