package com.antoniosj.composerecipeapp.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.antoniosj.composerecipeapp.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.HeartButtonState.*

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                // remember = gives a composable function memory
                // precisa pra ele lembrar o valor :P
                //val query = remember { mutableStateOf("beef")}
                // trick pra atualizar textview compose
                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                Column {

                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::newSearch,
                        scrollPosition = viewModel.categoryScrollPosition,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        val state = remember { mutableStateOf(IDLE) }

                        HeartButton(modifier = Modifier,
                            buttonState = state,
                            onToggle = {
                                state.value = if(state.value == IDLE) ACTIVE else IDLE
                            }
                        )
                    }
                    

                    //PulsingDemo() // Testing my component
//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                    ){
//                        LazyColumn {
//                            itemsIndexed(items = recipes) { index, recipe ->
//                                RecipeCard(
//                                    recipe = recipe,
//                                    onClick = {})
//                            }
//                        }
//                        CircularIndeterminateProgressBar(isDisplayed = loading)
//                    }
                }
            }
        }
    }
}