package com.lek.mybodyfatpercentage.onboarding.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import kotlinx.coroutines.delay

@Composable
fun LogoAnimation(onNavigateToNext: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isAnimating by remember {
                mutableStateOf(false)
            }
            var isSecondAnimating: Boolean by remember {
                mutableStateOf(false)
            }

            var isThirdAnimating: Boolean by remember {
                mutableStateOf(false)
            }
            Row() {
                AnimatedImage(
                    isAnimating = isAnimating,
                    origin = AnimationOrigin.START,
                    image = R.drawable.logo_block_one
                )
                Spacer(modifier = Modifier.size(8.dp))
                AnimatedImage(
                    isAnimating = isAnimating,
                    origin = AnimationOrigin.END,
                    image = R.drawable.logo_block_two
                ) {
                    isSecondAnimating = true
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row() {
                AnimatedImage(
                    isSecondAnimating,
                    origin = AnimationOrigin.START,
                    image = R.drawable.logo_block_three
                )
                Spacer(modifier = Modifier.size(8.dp))
                AnimatedImage(
                    isSecondAnimating,
                    origin = AnimationOrigin.END,
                    image = R.drawable.logo_block_four
                ) {
                    isThirdAnimating = true
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row() {
                AnimatedImage(
                    isThirdAnimating,
                    origin = AnimationOrigin.START,
                    image = R.drawable.logo_block_five
                )
                Spacer(modifier = Modifier.size(8.dp))
                AnimatedImage(
                    isThirdAnimating,
                    origin = AnimationOrigin.END,
                    image = R.drawable.logo_block_six
                )
            }

            LaunchedEffect(key1 = Unit) {
                isAnimating = true
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(1600)
        onNavigateToNext()
    }
}

enum class AnimationOrigin {
    START, END
}

@Composable
fun AnimatedImage(
    isAnimating: Boolean,
    origin: AnimationOrigin,
    @DrawableRes image: Int,
    onNext: () -> Unit = {}
) {
    val animation = when (origin) {
        AnimationOrigin.START -> slideInHorizontally(tween(1000), initialOffsetX = { -it * 2 })
        AnimationOrigin.END -> slideInHorizontally(tween(1000), initialOffsetX = { it * 2 })
    }
    AnimatedVisibility(
        visible = isAnimating,
        enter = animation
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "block"
        )
    }

    LaunchedEffect(key1 = Unit) {
        delay(200)
        onNext()
    }
}

@Composable
@Preview(showSystemUi = true)
fun LoadingScreenPreview() {
    MyBodyFatPercentageTheme() {
        LogoAnimation()
    }
}