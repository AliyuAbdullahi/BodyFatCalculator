package com.lek.mybodyfatpercentage.homescreen.domain.usecase

import com.lek.mybodyfatpercentage.core.UseCase
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatCalculationData
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import javax.inject.Inject

class CreateBodyFatReadingUseCase @Inject constructor(
    private val bodyFatRepo: IBodyFatDataRepository
) : UseCase<BodyFatCalculationData, BodyFatData>() {

    override suspend fun run(param: BodyFatCalculationData): BodyFatData {

        return bodyFatRepo.createReading(
            BodyFatCalculationData(
                firstReading = param.firstReading,
                secondReading = param.secondReading,
                thirdRecording = param.thirdRecording,
                weight = param.weight,
                age = param.age,
                gender = param.gender
            )
        )
    }
}