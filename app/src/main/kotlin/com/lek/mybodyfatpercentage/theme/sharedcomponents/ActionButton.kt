package com.lek.mybodyfatpercentage.theme.sharedcomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(modifier: Modifier = Modifier, @StringRes label: Int, onActionButtonClicked: () -> Unit = {}) {
    Button(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .fillMaxWidth(), onClick = { onActionButtonClicked() }) {
        Text(modifier = Modifier.padding(8.dp), text = stringResource(id = label))
    }
}