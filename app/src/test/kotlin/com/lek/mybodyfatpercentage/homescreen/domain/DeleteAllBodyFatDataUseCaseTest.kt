package com.lek.mybodyfatpercentage.homescreen.domain

import com.lek.mybodyfatpercentage.core.invoke
import com.lek.mybodyfatpercentage.homescreen.domain.usecase.DeleteAllBodyFatDataUseCase
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteAllBodyFatDataUseCaseTest {

    private val repository: IBodyFatDataRepository = mockk(relaxed = true)
    private val useCase = DeleteAllBodyFatDataUseCase(repository)

    @Test
    fun `when useCase is invoked - repository attempts to delete all items`() = runTest {

        useCase()
        verify { repository.deleteAllReadings() }
    }
}