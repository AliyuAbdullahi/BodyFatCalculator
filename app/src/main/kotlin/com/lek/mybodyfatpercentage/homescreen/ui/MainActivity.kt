package com.lek.mybodyfatpercentage.homescreen.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.lek.mybodyfatpercentage.homescreen.ui.componoent.ReadingsScreen
import com.lek.mybodyfatpercentage.homescreen.ui.model.DeleteOneReadingClicked
import com.lek.mybodyfatpercentage.homescreen.ui.model.DeleteReadingConfirmed
import com.lek.mybodyfatpercentage.homescreen.ui.model.DialogDismissed
import com.lek.mybodyfatpercentage.homescreen.ui.model.ReadingListState
import com.lek.mybodyfatpercentage.theme.MyBodyFatPercentageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: BodyFatDataListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MyBodyFatPercentageTheme {
                ReadingsScreen(
                    viewModel.stateFlow.collectAsState(initial = ReadingListState()).value,
                    onCreateReadingClicked = { startAddReadingScreen() },
                    onDeleteClicked = { viewModel.handleEvent(DeleteOneReadingClicked(it)) },
                    onDeleteConfirmedClicked = { viewModel.handleEvent(DeleteReadingConfirmed) },
                    onDeleteDialogDismissed = { viewModel.handleEvent(DialogDismissed) }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.resume()
    }

    private fun startAddReadingScreen() {
        startActivity(Intent(this, CreateReadingActivity::class.java))
    }
}

