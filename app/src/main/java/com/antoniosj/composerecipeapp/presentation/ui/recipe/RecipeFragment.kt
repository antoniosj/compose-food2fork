package com.antoniosj.composerecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.antoniosj.composerecipeapp.presentation.BaseApplication
import com.antoniosj.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.antoniosj.composerecipeapp.presentation.components.DefaultSnackbar
import com.antoniosj.composerecipeapp.presentation.components.RecipeView
import com.antoniosj.composerecipeapp.presentation.theme.AppTheme
import com.antoniosj.composerecipeapp.utils.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    val args: RecipeFragmentArgs by navArgs()
    val viewModel: RecipeViewModel by viewModels()

    @Inject
    lateinit var application: BaseApplication

    @ExperimentalMaterialApi
    private val snackbarController = SnackbarController(lifecycleScope)


    private var recipeId: MutableState<Int> = mutableStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(args.recipeId))
        recipeId.value = args.recipeId
    }


    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value

                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                ){
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box (
                            modifier = Modifier.fillMaxSize()
                        ){
                            //if (loading && recipe == null) Text(text = "LOADING...")
                            recipe?.let {
                                if(it.id == 1) { // create a fake error
                                    snackbarController.getScope().launch {
                                        snackbarController.showSnackbar(
                                            scaffoldState = scaffoldState,
                                            message = "An error occurred with this recipe",
                                            actionLabel = "Ok"
                                        )
                                    }
                                }
                                else{
                                    RecipeView(
                                        recipe = it,
                                    )
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}