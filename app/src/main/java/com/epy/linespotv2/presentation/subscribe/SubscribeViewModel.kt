package com.epy.linespotv2.presentation.subscribe

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.usecase.SubscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val doSubscribeUseCase: SubscribeUseCase
) : BaseViewModel<SubscribeIntent, SubscribeState>(SubscribeState()) {

    override fun onIntent(intent: SubscribeIntent) {
        when (intent) {
            is SubscribeIntent.loadPage -> loadPage(isRefresh = false)
            is SubscribeIntent.onClickTab -> {
                updateState { it.copy(selectedTabIndex = intent.index) }
            }
            is SubscribeIntent.onOpenBenefit -> sendEffect(SubscribeEffect.NavigateToBenefit)
            is SubscribeIntent.onClickPackage -> sendEffect(SubscribeEffect.NavigateToPackage)
        }
    }

    fun consumeEffect() {
        updateState { it.copy(subscribeEffect = null) }
    }

    private fun loadPage(isRefresh: Boolean = false) {
        viewModelScope.launch {
            updateState { currentState ->
                currentState.copy(
                    isLoading = true,
                    isRefresh = isRefresh,
                    error = null
                )
            }

            doSubscribeUseCase().collect { result ->
                when (result) {
                    is ApiCondition.AppSuccess -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                isRefresh = false,
                                subscribeModel = result.data,
                                error = null
                            )
                        }
                    }
                    is ApiCondition.AppFailure -> {
                        val errorMessage = result.exception.message ?: "Terjadi kesalahan"
                        updateState {
                            it.copy(
                                isLoading = false,
                                isRefresh = false,
                                error = errorMessage
                            )
                        }
                    }
                    is ApiCondition.AppLoading -> {
                        updateState {
                            it.copy(
                                isLoading = true,
                                isRefresh = if (!isRefresh) it.isRefresh else true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendEffect(effect: SubscribeEffect) {
        updateState { it.copy(subscribeEffect = effect) }
    }
}
