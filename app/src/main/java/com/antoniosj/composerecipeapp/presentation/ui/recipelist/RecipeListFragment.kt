package com.antoniosj.composerecipeapp.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.antoniosj.composerecipeapp.R
import com.antoniosj.composerecipeapp.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
                    val state = remember { mutableStateOf(HeartAnimationDef.HeartBtnState.IDLEE) }
                    HeartButton(modifier = Modifier,
                        buttonState = state,
                        onToggle = {
                            state.value = if(state.value == HeartAnimationDef.HeartBtnState.IDLEE) HeartAnimationDef.HeartBtnState.ACTIVEE else HeartAnimationDef.HeartBtnState.IDLEE
                        },)
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