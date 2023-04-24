package com.lek.mybodyfatpercentage.homescreen.data

import android.content.SharedPreferences
import app.cash.turbine.test
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatCalculationData
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
internal class BodyFatDataRepositoryTest {

    private val preference: SharedPreferences = mockk(relaxed = true)
    private lateinit var repository: IBodyFatDataRepository

    @Nested
    @DisplayName("Repository Initialization Test")
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    inner class InitializeTest {
        @Test
        fun `when repository has no data - emits empty`() = runTest {
            every { preference.getString(any(), any()) }.returns("")
            repository = BodyFatDataRepository(preference)
            repository.bodyFatFlow.test {
                assertTrue(awaitItem().isEmpty())
            }
        }

        @Test
        fun `when repository has data - emits result`() = runTest {
            val data = BodyFatData(12L, 10.0, 70.0)
            every { preference.getString(any(), any()) }.returns(listOf(data).toString())
            repository = BodyFatDataRepository(preference)
            repository.bodyFatFlow.test {
                assertEquals(awaitItem(), listOf(data))
            }
        }
    }

    @Nested
    @DisplayName("Repository Data Update Test")
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    inner class TestDataUpdate {

        @Test
        fun `when #createReading() is invoked - data is created but not saved`() = runTest {
            val data = BodyFatData(12L, 10.0, 70.0)
            every { preference.getString(any(), any()) }.returns(listOf(data).toString())
            repository = BodyFatDataRepository(preference)
            val reading = BodyFatCalculationData(
                firstReading = 8.0,
                secondReading = 5.0,
                thirdRecording = 6.0,
                weight = 71.0,
                age = 33,
                gender = Gender.MALE
            )
            repository.createReading(reading)

            repository.bodyFatFlow.test {
                assertTrue(awaitItem().size == 1)
            }
        }

        @Test
        fun `when #saveReading() is invoked - data is saved added to repository`() = runTest {
            val data = BodyFatData(12L, 10.0, 70.0)
            every { preference.getString(any(), any()) }.returns(listOf(data).toString())
            repository = BodyFatDataRepository(preference)
            val reading = BodyFatCalculationData(
                firstReading = 8.0,
                secondReading = 5.0,
                thirdRecording = 6.0,
                weight = 71.0,
                age = 33,
                gender = Gender.MALE
            )
            val createdReading = repository.createReading(reading)
            repository.saveReading(BodyFatData(date = 22L, reading = createdReading.reading, bodyWeightUsed = createdReading.bodyWeightUsed))

            repository.bodyFatFlow.test {
                assertTrue(awaitItem().size == 2)
            }
        }

        @Test
        fun `when #delete() is invoked - data is removed to repository`() = runTest {
            val data = BodyFatData(12L, 10.0, 70.0)
            every { preference.getString(any(), any()) }.returns(listOf(data).toString())
            repository = BodyFatDataRepository(preference)
            val reading = BodyFatCalculationData(
                firstReading = 8.0,
                secondReading = 5.0,
                thirdRecording = 6.0,
                weight = 71.0,
                age = 33,
                gender = Gender.MALE
            )
            val createdReading = repository.createReading(reading)
            repository.saveReading(BodyFatData(date = 22L, reading = createdReading.reading, bodyWeightUsed = createdReading.bodyWeightUsed))
            var result: Long? = null
            repository.bodyFatFlow.test {
                val bodyFataData = awaitItem()
                result = bodyFataData[1].date
                assertTrue(bodyFataData.size == 2)
            }

            repository.deleteReading(result!!)

            repository.bodyFatFlow.test {
                assertTrue(awaitItem().size == 1)
            }
        }

        @Test
        fun `when #deleteAll() is invoked - all data is removed from repository`() = runTest {
            val data = BodyFatData(12L, 10.0, 70.0)
            every { preference.getString(any(), any()) }.returns(listOf(data).toString())
            repository = BodyFatDataRepository(preference)
            val reading = BodyFatCalculationData(
                firstReading = 8.0,
                secondReading = 5.0,
                thirdRecording = 6.0,
                weight = 71.0,
                age = 33,
                gender = Gender.MALE
            )
            val createdReading = repository.createReading(reading)
            repository.saveReading(BodyFatData(date = 22L, reading = createdReading.reading, bodyWeightUsed = createdReading.bodyWeightUsed))
            repository.bodyFatFlow.test {
                assertTrue(awaitItem().size == 2)
            }

            repository.deleteAllReadings()

            repository.bodyFatFlow.test {
                assertTrue(awaitItem().isEmpty())
            }
        }
    }
}