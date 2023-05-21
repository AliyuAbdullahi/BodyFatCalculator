package com.lek.mybodyfatpercentage.homescreen.ui.componoent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.common.formatLongToStringDate
import com.lek.mybodyfatpercentage.homescreen.ui.model.ReadingListState
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme

@Composable
fun ReadingsScreen(
    state: ReadingListState = ReadingListState(),
    onCreateReadingClicked: () -> Unit = {},
    onDeleteClicked: (Long) -> Unit = {},
    onDeleteConfirmedClicked: () -> Unit = {},
    onDeleteDialogDismissed: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        AnimatedVisibility(visible = state.readings.isEmpty(), enter = fadeIn(), exit = fadeOut()) {
            EmptyState(onCreateReadingClicked)
        }
        AnimatedVisibility(visible = state.readings.isNotEmpty()) {
            Column {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = stringResource(id = R.string.your_readings),
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    CreateNewButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .wrapContentSize(),
                        onCreateReadingClicked
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    items(state.readings) { reading ->
                        if (reading.isFirst) {
                            CurrentReading(
                                weightUsed = reading.bodyWeightUsed.toString(),
                                dateTaken = formatLongToStringDate(reading.date),
                                bodyFatUsed = reading.reading.toString(),
                                onDeleteClicked = { onDeleteClicked(reading.date) }
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        } else {
                            BodyFatListItem(
                                reading = reading.reading.toString(),
                                dateTaken = formatLongToStringDate(reading.date),
                                weightUsed = reading.bodyWeightUsed.toString(),
                                onDeleteClicked = { onDeleteClicked(reading.date) }
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = state.isDeleteDialogShowing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                DeleteDialog(
                    onDeleteConfirmed = { onDeleteConfirmedClicked() },
                    onDismiss = onDeleteDialogDismissed
                )
            }
        }
    }
}

@Composable
fun CurrentReading(
    weightUsed: String,
    dateTaken: String,
    bodyFatUsed: String,
    onDeleteClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.last_reading),
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
                IconButton(modifier = Modifier.padding(8.dp), onClick = onDeleteClicked) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                }
            }

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.weight_used, weightUsed),
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.body_fat_used, bodyFatUsed),
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.date_taken, dateTaken),
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun BodyFatListItem(
    reading: String,
    dateTaken: String,
    weightUsed: String,
    onItemClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onItemClicked() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.body_fat_row_title, reading))
            IconButton(modifier = Modifier.padding(8.dp), onClick = onDeleteClicked) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }
        }
        Text(text = stringResource(id = R.string.body_fat_row_date, dateTaken, weightUsed))
    }
}

@Composable
fun EmptyState(onCreateReadingClicked: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.empty_readings_screen_state))
            Spacer(modifier = Modifier.size(8.dp))
            CreateNewButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                onCreateReadingClicked
            )
        }
    }
}

@Composable
private fun CreateNewButton(modifier: Modifier = Modifier, onCreateReadingClicked: () -> Unit) {
    Button(modifier = modifier, onClick = { onCreateReadingClicked() }) {
        Text(modifier = Modifier.padding(8.dp), text = stringResource(id = R.string.create_new))
    }
}

@Preview
@Composable
fun CurrentReadingPreview() {
    MyBodyFatPercentageTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            CurrentReading(
                weightUsed = "74",
                dateTaken = "12/02/2023",
                bodyFatUsed = "10.3",
                onDeleteClicked = {}
            )
        }
    }
}