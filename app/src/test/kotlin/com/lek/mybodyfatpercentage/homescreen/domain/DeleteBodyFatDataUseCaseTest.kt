package com.lek.mybodyfatpercentage.homescreen.domain

import com.lek.mybodyfatpercentage.homescreen.domain.usecase.DeleteBodyFatDataUseCase
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.GetBodyFatDataListStream
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteBodyFatDataUseCaseTest {

    private val repository: IBodyFatDataRepository = mockk(relaxed = true)
    private val useCase = DeleteBodyFatDataUseCase(repository)

    @Test
    fun `when useCase is invoked - repository attempts to delete item`() = runTest {

        useCase(22L)
        verify { repository.deleteReading(22L) }
    }
}