package com.jguerrerope.speedrun.domain


@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status, val msg: String? = null
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.LOADING)

        // to use on PagedList.BoundaryCallback
        val INITIAL_LOADING = NetworkState(Status.INITIAL_LOADING)
        val NEXT_LOADING = NetworkState(Status.NEXT_LOADING)

        fun error(throwable: Throwable) = NetworkState(Status.FAILED, throwable.message)
    }
}
