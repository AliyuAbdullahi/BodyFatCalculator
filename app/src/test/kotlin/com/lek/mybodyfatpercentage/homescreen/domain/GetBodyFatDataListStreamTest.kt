package com.lek.mybodyfatpercentage.homescreen.domain

import app.cash.turbine.test
import com.lek.mybodyfatpercentage.homescreen.domain.model.BodyFatData
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.GetBodyFatDataListStream
import io.mockk.coEvery
import io.mockk.mockk
import com.lek.mybodyfatpercentage.core.invoke
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetBodyFatDataListStreamTest {

    private val repository: IBodyFatDataRepository = mockk(relaxed = true)
    private val stream = GetBodyFatDataListStream(repository)

    @Test
    fun `when GetBodyFatDataListStream is invoked - data in repository can be observed`() = runTest {
        val data = listOf(BodyFatData(date = 22L, reading = 10.5, bodyWeightUsed = 70.5))
        coEvery { repository.bodyFatFlow }.coAnswers { flowOf(data) }
        stream().test {
            assertEquals(data, awaitItem())
            awaitComplete()
        }
    }
}