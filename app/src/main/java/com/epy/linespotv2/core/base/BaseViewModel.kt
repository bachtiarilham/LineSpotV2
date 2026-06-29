package com.epy.linespotv2.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Base ViewModel untuk pola MVI.
 * - S = State  : data UI yang ditampilkan ke layar
 * - I = Intent : aksi yang dikirim dari UI ke ViewModel
 */
abstract class BaseViewModel<I, S>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // UI mengirim aksi ke ViewModel lewat fungsi ini
    abstract fun onIntent(intent: I)

    // Helper untuk update state dari dalam ViewModel
    protected fun updateState(reducer: (S) -> S) {
        _state.update(reducer)
    }
}