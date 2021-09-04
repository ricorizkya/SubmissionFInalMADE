package com.example.core.data

import com.example.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResources<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val db = loadDB().first()
        if (fetch(db)) {
            emit(Resource.Loading())
            when (val apiResponse = apiCall().first()) {
                is ApiResponse.Success -> {
                    callResult(apiResponse.data)
                    emitAll(loadDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    fetchFailed()
                    emit(Resource.Errorr<ResultType>(apiResponse.errorMessage))
                }
            }
        }else {
            emitAll(loadDB().map { Resource.Success(it) })
        }
    }

//    init {
//        mediatorLiveData.value = Resource.Loading(null)
//        @Suppress("LeakingThis")
//        val db = loadDB()
//        mediatorLiveData.addSource(db) {
//            data -> mediatorLiveData.removeSource(db)
//            if (fetch(data)) {
//                fetchNetwork(db)
//            }else {
//                mediatorLiveData.addSource(db) {
//                    newData -> mediatorLiveData.value = Resource.Success(newData)
//                }
//            }
//        }
//    }

    protected open fun fetchFailed(){}
//    private fun fetchNetwork(db: LiveData<ResultType>){
//        val apiResponse = apiCall()
//        mediatorLiveData.addSource(db) {
//            newData -> mediatorLiveData.value = Resource.Loading(newData)
//        }
//        mediatorLiveData.addSource(apiResponse){ response ->
//            mediatorLiveData.removeSource(apiResponse)
//            mediatorLiveData.removeSource(db)
//            when (response) {
//                is ApiResponse.Success ->
//                    appExecutors.diskIO().execute {
//                        callResult(response.data)
//                        appExecutors.mainThread().execute {
//                            mediatorLiveData.addSource(loadDB()) {
//                                newData -> mediatorLiveData.value = Resource.Success(newData)
//                            }
//                        }
//                    }
//                is ApiResponse.Empty -> {
//                    appExecutors.mainThread().execute {
//                        mediatorLiveData.addSource(loadDB()) { newData ->
//                            mediatorLiveData.value = Resource.Success(newData)
//                        }
//                    }
//                }
//                is ApiResponse.Error -> {
//                    fetchFailed()
//                    mediatorLiveData.addSource(db) { newData ->
//                        mediatorLiveData.value = Resource.Errorr(response.errorMessage, newData)
//                    }
//                }
//
//            }
//        }
//    }

    fun asFlow(): Flow<Resource<ResultType>> = result
    protected abstract fun loadDB(): Flow<ResultType>
    protected abstract fun fetch(resultType: ResultType?): Boolean
    protected abstract suspend fun apiCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun callResult(requestType: RequestType)
}