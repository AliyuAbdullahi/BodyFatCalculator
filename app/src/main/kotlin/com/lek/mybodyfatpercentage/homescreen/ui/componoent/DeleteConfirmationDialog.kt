package com.lek.mybodyfatpercentage.homescreen.ui.componoent

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.theme.sharedcomponents.DialogWindow

@Composable
fun DeleteDialog(onDeleteConfirmed: () -> Unit, onDismiss: () -> Unit) {
    DialogWindow(
        icon = R.drawable.warning,
        title = stringResource(id = R.string.delete_confirmation),
        okayLabel = R.string.yes,
        cancelLabel = R.string.no,
        onOkayClicked = onDeleteConfirmed,
        onCancelClicked = onDismiss,
        onBackgroundClicked = onDismiss
    )
}