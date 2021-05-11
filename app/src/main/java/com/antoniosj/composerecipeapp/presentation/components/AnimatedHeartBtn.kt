package com.antoniosj.composerecipeapp.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDef.expandedIconSize
import com.antoniosj.composerecipeapp.presentation.components.HeartAnimationDef.idleIconSize
import com.antoniosj.composerecipeapp.utils.loadDrawable
import com.antoniosj.composerecipeapp.R

object HeartAnimationDef {

    enum class HeartBtnState {
        IDLEE, ACTIVEE
    }
    val idleIconSize = 50.dp
    val expandedIconSize = 80.dp
}

@Composable
fun HeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDef.HeartBtnState>,
    onToggle: () -> Unit
) {
    val transitionData = AnimatedHeartBtn(buttonState)

    if (buttonState.value == HeartAnimationDef.HeartBtnState.ACTIVEE) {
        loadDrawable(drawable = R.drawable.heart_red).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = modifier
                    .noRippleClickable(onClick = onToggle)
                    .size(transitionData.size),
                contentDescription = "")
        }
    } else {
        loadDrawable(drawable = R.drawable.heart_grey).value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = modifier
                    .noRippleClickable(onClick = onToggle)
                    .size(transitionData.size),
                contentDescription = "")
        }
    }


}

class TransitionData(
    size: State<Dp>
) {
    val size by size
}

@Composable
fun AnimatedHeartBtn(
    buttonState: MutableState<HeartAnimationDef.HeartBtnState>,
): TransitionData  {
    val transitionx = updateTransition(buttonState)
    val size = transitionx.animateDp { state ->
        when (state.value) {
            HeartAnimationDef.HeartBtnState.IDLEE -> idleIconSize
            HeartAnimationDef.HeartBtnState.ACTIVEE -> expandedIconSize
        }
    }
    return remember(transitionx) { TransitionData(size) }
}

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}