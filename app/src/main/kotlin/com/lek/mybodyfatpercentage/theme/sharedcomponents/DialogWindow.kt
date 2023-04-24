package com.lek.mybodyfatpercentage.theme.sharedcomponents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.mybodyfatpercentage.R
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import com.lek.mybodyfatpercentage.theme.TextGrey
import com.lek.mybodyfatpercentage.theme.TranslucentGrey

@Composable
fun DialogWindow(
    title: String,
    okayLabel: Int,
    @StringRes subtitle: Int? = null,
    @DrawableRes icon: Int? = null,
    cancelLabel: Int? = null,
    tertiaryLabel: Int? = null,
    onTertiaryClicked: (() -> Unit)? = null,
    onOkayClicked: (() -> Unit) = {},
    onCancelClicked: (() -> Unit)? = null,
    onBackgroundClicked: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranslucentGrey)
            .clickable { onBackgroundClicked?.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                icon?.let { theIcon ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(120.dp),
                            painter = painterResource(id = theIcon),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.size(16.dp))

                subtitle?.let { theSubTitle ->
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(id = theSubTitle),
                        style = MaterialTheme.typography.button,
                        color = TextGrey
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Spacer(modifier = Modifier.weight(1F))
                    tertiaryLabel?.let { theCancelLabel ->
                        Spacer(modifier = Modifier.size(8.dp))
                        DialogButton(
                            label = theCancelLabel,
                            backgroundColor = Color.Green,
                            onActionButtonClicked = { onTertiaryClicked?.invoke() }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    cancelLabel?.let { theCancelLabel ->
                        DialogButton(
                            label = theCancelLabel,
                            backgroundColor = Color.Red,
                            onActionButtonClicked = { onCancelClicked?.invoke() }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    DialogButton(
                        label = okayLabel,
                        onActionButtonClicked = onOkayClicked
                    )
                }
            }
        }
    }
}

@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    backgroundColor: Color = MaterialTheme.colors.primary,
    onActionButtonClicked: () -> Unit = {}
) {
    Button(
        modifier = modifier.wrapContentSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor, contentColor = Color.White),
        onClick = { onActionButtonClicked() }
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = stringResource(id = label)
        )
    }
}

@Composable
@Preview
private fun DialogWindowPreview() {
    MyBodyFatPercentageTheme {
        DialogWindow(
            title = "Done",
            subtitle = R.string.create_new,
            okayLabel = R.string.ok,
            cancelLabel = R.string.dismiss,
            tertiaryLabel = R.string.new_input,
            icon = R.drawable.female_first
        )
    }
}