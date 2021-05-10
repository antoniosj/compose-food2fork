package com.antoniosj.composerecipeapp.di

import com.antoniosj.composerecipeapp.network.RecipeService
import com.antoniosj.composerecipeapp.network.model.RecipeDtoMapper
import com.antoniosj.composerecipeapp.repository.RecipeRepository
import com.antoniosj.composerecipeapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }
}