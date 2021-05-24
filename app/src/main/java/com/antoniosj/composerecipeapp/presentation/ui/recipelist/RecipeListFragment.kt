package com.antoniosj.composerecipeapp.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.antoniosj.composerecipeapp.presentation.BaseApplication
import com.antoniosj.composerecipeapp.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.HeartButtonState.*
import com.antoniosj.composerecipeapp.presentation.theme.AppTheme
import com.antoniosj.composerecipeapp.presentation.ui.recipelist.RecipeListEvent.NewSearchEvent
import com.antoniosj.composerecipeapp.presentation.ui.recipelist.RecipeListEvent.NextPageEvent
import com.antoniosj.composerecipeapp.utils.SnackbarController
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalMaterialApi
    private val snackbarController = SnackbarController(lifecycleScope)

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value

                    // remember = gives a composable function memory
                    // precisa pra ele lembrar o valor :P
                    //val query = remember { mutableStateOf("beef")}
                    // trick pra atualizar textview compose
                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    val scaffoldState = rememberScaffoldState()

                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        lifecycleScope.launch {
                                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Invalid category: MILK",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(NewSearchEvent)
                                    }
                                },
                                scrollPosition = viewModel.categoryScrollPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = {
                                    application.toggleLightTheme()
                                }
                            )
                        },
                        bottomBar = {
                            //MyBottomBar()
                        },
                        drawerContent = {
                            //MyDrawer()
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerEvent = {
                                             viewModel.onTriggerEvent(NextPageEvent)
                            },
                            navController = findNavController(),
                            scaffoldState = scaffoldState ,
                            snackbarController = snackbarController
                        )
                    }
                }
            }
        }
    }
}
