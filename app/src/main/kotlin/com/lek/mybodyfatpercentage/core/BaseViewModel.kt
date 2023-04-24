package com.lek.mybodyfatpercentage.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel<STATE, EVENT> : ViewModel() {

    abstract fun initialState(): STATE

    protected var state: STATE = this.initialState()

    private var sharedFlow = MutableSharedFlow<STATE>(replay = 1)
    val stateFlow: Flow<STATE>
        get() = sharedFlow

    fun updateState(reducer: STATE.() -> STATE) {
        state = state.reducer()
        sharedFlow.tryEmit(state)
    }

    fun clearState() {
        state = initialState()
        sharedFlow.tryEmit(state)
    }

    open fun handleEvent(event: EVENT) {

    }
}