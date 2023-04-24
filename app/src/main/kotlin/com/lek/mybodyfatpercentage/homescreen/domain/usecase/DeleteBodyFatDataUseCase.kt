package com.lek.mybodyfatpercentage.homescreen.domain.usecase

import com.lek.mybodyfatpercentage.core.UseCase
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import javax.inject.Inject

class DeleteBodyFatDataUseCase @Inject constructor(
    private val repository: IBodyFatDataRepository
): UseCase<Long, Unit>() {
    override suspend fun run(param: Long) = repository.deleteReading(param)
}