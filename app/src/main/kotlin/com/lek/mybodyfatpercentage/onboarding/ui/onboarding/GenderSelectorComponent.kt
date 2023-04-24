package com.lek.mybodyfatpercentage.onboarding.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import com.lek.mybodyfatpercentage.theme.Grey200
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import com.lek.mybodyfatpercentage.theme.Pink700

@Composable
fun GenderSelectionComponent(
    gender: Gender,
    modifier: Modifier = Modifier,
    onGenderSelected: (Gender) -> Unit = {}
) {
    Row(modifier = modifier.wrapContentSize()) {
        val color = getGenderSelectionBackground(gender == Gender.MALE)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(color)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = Color.Black
                    ),
                    onClick = { onGenderSelected(Gender.MALE) }
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Transparent)
                ,
                text = stringResource(id = R.string.male),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        val femaleColor = getGenderSelectionBackground(gender == Gender.FEMALE)

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(femaleColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = Color.Black
                    ),
                    onClick = { onGenderSelected(Gender.FEMALE) }
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.Transparent),
                text = stringResource(id = R.string.female),
                color = Color.White
            )
        }
    }
}

@Composable
private fun getGenderSelectionBackground(isSelected: Boolean): Color =
    if (isSelected) {
        Pink700
    } else {
        Grey200
    }

@Composable
@Preview(showSystemUi = true)
fun GenderSelectionComponentUnknownPreview() {
    MyBodyFatPercentageTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            GenderSelectionComponent(Gender.UNKNOWN)
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun GenderSelectionComponentMalePreview() {
    MyBodyFatPercentageTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            GenderSelectionComponent(Gender.MALE)
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun GenderSelectionComponentFemalePreview() {
    MyBodyFatPercentageTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            GenderSelectionComponent(Gender.FEMALE)
        }
    }
}