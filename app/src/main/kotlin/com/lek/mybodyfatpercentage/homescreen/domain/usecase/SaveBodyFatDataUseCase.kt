package com.lek.mybodyfatpercentage.homescreen.domain.usecase

import com.lek.mybodyfatpercentage.core.UseCase
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import javax.inject.Inject

class SaveBodyFatDataUseCase @Inject constructor(
    private val repository: IBodyFatDataRepository
) : UseCase<BodyFatData, Unit>() {
    override suspend fun run(param: BodyFatData) = repository.saveReading(param)
}