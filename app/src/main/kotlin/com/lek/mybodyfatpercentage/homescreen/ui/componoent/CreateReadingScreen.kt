package com.lek.mybodyfatpercentage.homescreen.ui.componoent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.homescreen.ui.model.CreateReadingState
import com.lek.mybodyfatpercentage.homescreen.ui.model.CreationSuccess
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import com.lek.mybodyfatpercentage.onboarding.domain.MeasurementIndex
import com.lek.mybodyfatpercentage.onboarding.ui.onboarding.GenderSelectionComponent
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import com.lek.mybodyfatpercentage.theme.sharedcomponents.ActionButton
import com.lek.mybodyfatpercentage.theme.sharedcomponents.DialogWindow

@Composable
fun CreateReadingScreen(
    state: CreateReadingState,
    onFirstReadingChanged: (String) -> Unit = {},
    onSecondReadingChanged: (String) -> Unit = {},
    onThirdReadingChanged: (String) -> Unit = {},
    onAgeChanged: (String) -> Unit = {},
    onGenderChanged: (Gender) -> Unit = {},
    onWeightChanged: (String) -> Unit = {},
    onCalculateClicked: () -> Unit = {},
    onDialogOkayClicked: () -> Unit = {},
    onDialogNewInputClicked: () -> Unit = {},
    onDialogCancelClicked: () -> Unit = {},
    onDialogBackgroundClicked: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    BodyFatReadingInput(
        gender = state.gender,
        isValidInputs = state.isValid,
        firstReading = state.firstReading,
        secondReading = state.secondReading,
        thirdReading = state.thirdReading,
        age = state.age,
        weight = state.weight,
        success = state.creationSuccess,
        onFirstReadingChanged = onFirstReadingChanged,
        onSecondReadingChanged = onSecondReadingChanged,
        onThirdReadingChanged = onThirdReadingChanged,
        onWeightChanged = onWeightChanged,
        onAgeChanged = onAgeChanged,
        onGenderChanged = onGenderChanged,
        onCalculateClicked = onCalculateClicked,
        onOkayClicked = onDialogOkayClicked,
        onNewInputClicked = onDialogNewInputClicked,
        onCancelClicked = onDialogCancelClicked,
        onDialogBackgroundClicked = onDialogBackgroundClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun BodyFatReadingInput(
    gender: Gender,
    isValidInputs: Boolean,
    firstReading: String,
    secondReading: String,
    thirdReading: String,
    age: String,
    weight: String,
    success: CreationSuccess? = null,
    onFirstReadingChanged: (String) -> Unit,
    onSecondReadingChanged: (String) -> Unit,
    onThirdReadingChanged: (String) -> Unit,
    onWeightChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    onCalculateClicked: () -> Unit,
    onOkayClicked: () -> Unit,
    onNewInputClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onDialogBackgroundClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            TopAppBar(
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            ) {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
                Text(
                    text = stringResource(id = R.string.complete_the_fields),
                    style = MaterialTheme.typography.h6
                )
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                GenderSelectionComponent(gender = gender, onGenderSelected = onGenderChanged)

                val theAge = if (age.isBlank() || age.toInt() < 0) {
                    ""
                } else {
                    age
                }

                Spacer(modifier = Modifier.size(8.dp))

                OutlinedTextField(
                    value = theAge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = { Text(text = stringResource(id = R.string.age)) },
                    onValueChange = onAgeChanged
                )

                val theWeight = if (weight.isBlank() || weight.toDouble() < 0.0) {
                    ""
                } else {
                    weight
                }

                OutlinedTextField(
                    value = theWeight,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = { Text(text = stringResource(id = R.string.weight)) },
                    onValueChange = onWeightChanged
                )

                Spacer(modifier = Modifier.size(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    InputImage(
                        modifier = Modifier.size(80.dp),
                        gender = gender, measurementIndex = MeasurementIndex.FIRST
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    val reading = getReading(firstReading)
                    OutlinedTextField(
                        value = reading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = onFirstReadingChanged,
                        label = { Text(text = stringResource(id = R.string.first_reading)) }
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    InputImage(
                        modifier = Modifier.size(80.dp),
                        gender = gender, measurementIndex = MeasurementIndex.SECOND
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    val reading = getReading(secondReading)
                    OutlinedTextField(
                        value = reading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = onSecondReadingChanged,
                        label = { Text(text = stringResource(id = R.string.second_reading)) }
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    InputImage(
                        modifier = Modifier.size(80.dp),
                        gender = gender, measurementIndex = MeasurementIndex.THIRD
                    )
                    val reading = getReading(thirdReading)
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = reading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = onThirdReadingChanged,
                        label = { Text(text = stringResource(id = R.string.third_reading)) }
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Spacer(modifier = Modifier.weight(1F))

                AnimatedVisibility(visible = isValidInputs) {
                    ActionButton(
                        label = R.string.calculate_reading,
                        onActionButtonClicked = onCalculateClicked
                    )
                }
            }
        }

        success?.percentage?.let {
            DialogWindow(
                title = stringResource(id = R.string.reading_success_label, it),
                okayLabel = R.string.save,
                tertiaryLabel = R.string.new_input,
                cancelLabel = R.string.dismiss,
                icon = R.drawable.checked,
                onOkayClicked = onOkayClicked,
                onCancelClicked = onCancelClicked,
                onBackgroundClicked = onDialogBackgroundClicked,
                onTertiaryClicked = onNewInputClicked
            )
        }
    }
}

private fun getReading(reading: String): String {
    val theReading = if (reading.isEmpty() || reading.toDouble() <= 0) {
        ""
    } else {
        reading
    }
    return theReading
}

@Composable
private fun InputImage(
    modifier: Modifier = Modifier,
    gender: Gender,
    measurementIndex: MeasurementIndex
) {
    val position = measurementIndex.ordinal
    val imageResource = if (gender == Gender.MALE) {
        maleImages[position]
    } else {
        femaleImages[position]
    }
    AnimatedVisibility(visible = gender != Gender.UNKNOWN) {
        Image(
            modifier = modifier,
            contentScale = ContentScale.Crop,
            painter = painterResource(id = imageResource),
            contentDescription = null
        )
    }
}

private val maleImages =
    listOf(R.drawable.male_first, R.drawable.male_second, R.drawable.male_third)
private val femaleImages =
    listOf(R.drawable.female_first, R.drawable.female_second, R.drawable.female_third)

@Composable
@Preview
fun BodyFatReadingForMaleInputPreview() {
    MyBodyFatPercentageTheme {
        val state = CreateReadingState(gender = Gender.MALE)
        CreateReadingScreen(state)
    }
}

@Composable
@Preview
fun BodyFatReadingForFemaleInputPreview() {
    MyBodyFatPercentageTheme {
        val state = CreateReadingState(gender = Gender.FEMALE)
        CreateReadingScreen(state)
    }
}

@Composable
@Preview
fun BodyFatReadingValidInputPreview() {
    MyBodyFatPercentageTheme {
        val state = CreateReadingState(
            firstReading = "10.0",
            secondReading = "11.0",
            thirdReading = "10.0",
            age = "34",
            date = 22L,
            weight = "44.9",
            gender = Gender.MALE
        )
        CreateReadingScreen(state)
    }
}