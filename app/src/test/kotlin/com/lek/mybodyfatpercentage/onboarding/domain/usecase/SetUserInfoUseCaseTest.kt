package com.lek.mybodyfatpercentage.onboarding.domain.usecase

import com.lek.mybodyfatpercentage.onboarding.domain.IUserInfoRepository
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.SetUserInfoUseCase
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class SetUserInfoUseCaseTest {

    private val repository: IUserInfoRepository = mockk(relaxed = true)
    private val useCase = SetUserInfoUseCase(repository)

    @Test
    fun `when useCase is invoked with data - userInfo is saved`() = runBlocking {
        val userInfo: UserInfo = mockk()
        useCase(userInfo)
        verify { repository.setUserInfo(userInfo) }
    }
}