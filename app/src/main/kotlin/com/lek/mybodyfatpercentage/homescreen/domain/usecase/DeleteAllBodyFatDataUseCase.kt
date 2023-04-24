package com.lek.mybodyfatpercentage.homescreen.domain.usecase

import com.lek.mybodyfatpercentage.core.UseCase
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import javax.inject.Inject

class DeleteAllBodyFatDataUseCase @Inject constructor(
    private val repository: IBodyFatDataRepository
): UseCase<Unit, Unit>() {
    override suspend fun run(param: Unit) = repository.deleteAllReadings()
}