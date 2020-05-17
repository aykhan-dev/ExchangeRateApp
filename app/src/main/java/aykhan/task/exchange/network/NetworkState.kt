package aykhan.task.exchange.network

sealed class NetworkState {
    object Success : NetworkState()
    object Failure : NetworkState()
}