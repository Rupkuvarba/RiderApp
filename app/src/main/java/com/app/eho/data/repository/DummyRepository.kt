package com.app.eho.data.repository

import com.example.kotlin.data.local.db.DatabaseService
import com.app.eho.data.model.Dummy
import com.app.eho.data.remote.NetworkService
import com.app.eho.data.remote.request.DummyRequest
import io.reactivex.Single
import javax.inject.Inject

class DummyRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

}