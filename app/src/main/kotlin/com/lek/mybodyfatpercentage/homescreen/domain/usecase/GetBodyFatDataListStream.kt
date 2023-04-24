package com.lek.mybodyfatpercentage.homescreen.domain.usecase

import com.lek.mybodyfatpercentage.core.StreamUseCase
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBodyFatDataListStream @Inject constructor(
    private val repository: IBodyFatDataRepository
): StreamUseCase<Unit, List<BodyFatData>>(){

    override fun run(param: Unit): Flow<List<BodyFatData>> = repository.bodyFatFlow
}