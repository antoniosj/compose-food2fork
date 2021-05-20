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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.antoniosj.composerecipeapp.presentation.BaseApplication
import com.antoniosj.composerecipeapp.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.HeartButtonState.*
import com.antoniosj.composerecipeapp.presentation.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

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

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = viewModel::newSearch,
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
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background)
                        ) {
                            if (loading) {
                                ShimmerRecipeCardItem(imageHeight = 250.dp, padding = 8.dp)
                            } else {
                                LazyColumn {
                                    itemsIndexed(items = recipes) { index, recipe ->
                                        RecipeCard(
                                            recipe = recipe,
                                            onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }

//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//
//                        val state = remember { mutableStateOf(IDLE) }
//
//                        HeartButton(modifier = Modifier,
//                            buttonState = state,
//                            onToggle = {
//                                state.value = if(state.value == IDLE) ACTIVE else IDLE
//                            }
//                        )
//                    }
                        //PulsingDemo() // Testing my component
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(){
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = {Icon(Icons.Default.BrokenImage, "")},
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {Icon(Icons.Default.Search, "")},
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {Icon(Icons.Default.AccountBalanceWallet, "")},
            selected = false,
            onClick = {}
        )
    }
}


@Composable
fun MyDrawer(){
    Column() {
        Text(text = "Item1")
        Text(text = "Item2")
        Text(text = "Item3")
        Text(text = "Item4")
        Text(text = "Item5")
    }
}

// snackbar
@ExperimentalMaterialApi
@Composable
fun DecoupledSnackbarDemo(
    snackbarHostState: SnackbarHostState
){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        ){
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel?: "",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text(snackbarHostState.currentSnackbarData?.message?: "")
                }
            }
        )
    }
}

@Composable
fun SimpleSnackbarDemo(
    show: Boolean,
    onHideSnackbar: () -> Unit,
){
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val snackbar = createRef()
        if(show){
            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        "Hide",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = MaterialTheme.typography.h5
                    )
                },
            ) {
                Text(text = "Hey look a snackbar")
            }
        }
    }
}
