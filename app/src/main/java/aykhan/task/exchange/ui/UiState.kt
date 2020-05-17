package aykhan.task.exchange.ui

sealed class UiState {
    object Loading : UiState()
    object Loaded : UiState()
    object Stable : UiState()
}