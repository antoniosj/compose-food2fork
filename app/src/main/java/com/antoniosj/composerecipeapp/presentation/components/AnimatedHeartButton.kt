package com.antoniosj.composerecipeapp.presentation.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource


import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.expandedIconSize
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.idleIconSize
import com.antoniosj.composerecipeapp.utils.loadDrawable
import com.antoniosj.composerecipeapp.R
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.expandedIconImage
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDefinition.idleIconImage

object HeartAnimationDefinition {

    enum class HeartButtonState {
        IDLE, ACTIVE
    }
    val idleIconSize = 50.dp
    val expandedIconSize = 80.dp
    const val idleIconImage = R.drawable.heart_grey
    const val expandedIconImage = R.drawable.heart_red
}

@Composable
fun HeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
    onToggle: () -> Unit
) {
    val transitionData = AnimatedHeartBtn(buttonState)

    if (buttonState.value == HeartAnimationDefinition.HeartButtonState.ACTIVE) {
        loadDrawable(drawable = expandedIconImage).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = modifier
                    .noRippleClickable(onClick = onToggle)
                    .size(transitionData.size),
                contentDescription = "")
        }
    } else {
        loadDrawable(drawable = idleIconImage).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = modifier
                    .noRippleClickable(onClick = onToggle)
                    .size(transitionData.size),
                contentDescription = "")
        }
    }


}

private class TransitionData(
    size: State<Dp>
) {
    val size by size
}

@Composable
private fun AnimatedHeartBtn(
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
): TransitionData  {
    val transition = updateTransition(buttonState, label = "")
    val size = transition.animateDp { state ->
        when (state.value) {
            HeartAnimationDefinition.HeartButtonState.IDLE -> idleIconSize
            HeartAnimationDefinition.HeartButtonState.ACTIVE -> expandedIconSize
        }
    }
    return remember(transition) { TransitionData(size) }
}

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}