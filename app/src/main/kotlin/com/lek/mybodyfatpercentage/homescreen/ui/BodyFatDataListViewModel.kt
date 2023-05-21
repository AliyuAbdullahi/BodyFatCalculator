package com.lek.mybodyfatpercentage.homescreen.ui

import androidx.lifecycle.viewModelScope
import com.lek.mybodyfatpercentage.core.BaseViewModel
import com.lek.mybodyfatpercentage.core.invoke
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.DeleteAllBodyFatDataUseCase
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.DeleteBodyFatDataUseCase
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.GetBodyFatDataListStream
import com.lek.mybodyfatpercentage.homescreen.ui.model.DeleteAllReadingsClicked
import com.lek.mybodyfatpercentage.homescreen.ui.model.DeleteOneReadingClicked
import com.lek.mybodyfatpercentage.homescreen.ui.model.DeleteReadingConfirmed
import com.lek.mybodyfatpercentage.homescreen.ui.model.DialogDismissed
import com.lek.mybodyfatpercentage.homescreen.ui.model.ReadingListEvent
import com.lek.mybodyfatpercentage.homescreen.ui.model.ReadingListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BodyFatDataListViewModel @Inject constructor(
    private val deleteAllBodyFatDataUseCase: DeleteAllBodyFatDataUseCase,
    private val deleteBodyFatDataUseCase: DeleteBodyFatDataUseCase,
    private val getBodyFatDataStream: GetBodyFatDataListStream
) : BaseViewModel<ReadingListState, ReadingListEvent>() {

    override fun initialState(): ReadingListState = ReadingListState()

    fun resume() {
        startWatchingReadings()
    }

    private fun deleteAllData() {
        viewModelScope.launch {
            deleteAllBodyFatDataUseCase()
        }
    }

    private fun deleteOne(timeAdded: Long) {
        viewModelScope.launch {
            updateState {
                copy(
                    selected = state.readings.firstOrNull { it.date == timeAdded },
                    isDeleteDialogShowing = true
                )
            }
        }
    }

    private fun confirmDelete() {
        viewModelScope.launch {
            state.selected?.date?.let {
                deleteBodyFatDataUseCase(it)
            }
        }
    }

    private fun startWatchingReadings() {
        viewModelScope.launch {
            getBodyFatDataStream().collect {
                updateState { copy(readings = it, isDeleteDialogShowing = false) }
            }
        }
    }

    private fun clearSelection() {
        updateState { copy(selected = null, isDeleteDialogShowing = false) }
    }

    override fun handleEvent(event: ReadingListEvent) {
        when (event) {
            DeleteAllReadingsClicked -> deleteAllData()
            is DeleteOneReadingClicked -> deleteOne(event.timeAdded)
            is DeleteReadingConfirmed -> confirmDelete()
            DialogDismissed -> clearSelection()
        }
    }
}