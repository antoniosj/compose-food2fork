package com.antoniosj.composerecipeapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.antoniosj.composerecipeapp.presentation.ui.recipelist.FoodCategory
import com.antoniosj.composerecipeapp.presentation.ui.recipelist.getAllFoodCategories
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    scrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier= Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = {
                        onQueryChanged(it)
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "SearchIcon")
                    },
                    keyboardActions = KeyboardActions(onSearch = {
                        onExecuteSearch()
                        keyboardController?.hide()
                    }),
                    textStyle = MaterialTheme.typography.button,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )
                ConstraintLayout(modifier = Modifier.align(Alignment.CenterVertically)) {
                    val menu = createRef()
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                    ) {
                        Icon(Icons.Filled.MoreVert, "dark theme")
                    }
                }
            }
            // scroll state for chips
            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(start = 8.dp, bottom = 8.dp)

            ) {
                coroutineScope.launch {
                    scrollState.scrollTo(scrollPosition)
                }
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        }
                    )
                }
            }
        }

    }
}