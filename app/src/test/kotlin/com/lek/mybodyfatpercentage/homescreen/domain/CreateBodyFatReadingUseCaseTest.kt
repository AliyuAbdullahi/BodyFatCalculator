package com.lek.mybodyfatpercentage.homescreen.domain

import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatCalculationData
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.CreateBodyFatReadingUseCase
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CreateBodyFatReadingUseCaseTest {
    private val repository: IBodyFatDataRepository = mockk(relaxed = true)
    private val useCase = CreateBodyFatReadingUseCase(repository)

    @Test
    fun `when useCase is invoked - repository calculates body fat percentage`() = runTest {
        val data = BodyFatCalculationData(
            firstReading = 20.0,
            secondReading = 20.0,
            thirdRecording = 10.0,
            weight = 80.0,
            age = 30,
            gender = Gender.MALE
        )
        useCase(data)
        verify { repository.createReading(data) }
    }
}