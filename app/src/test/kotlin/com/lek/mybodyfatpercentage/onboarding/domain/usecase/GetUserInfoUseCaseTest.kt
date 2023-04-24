package com.lek.mybodyfatpercentage.onboarding.domain.usecase

import com.lek.mybodyfatpercentage.onboarding.domain.IUserInfoRepository
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import  com.lek.mybodyfatpercentage.core.invoke
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.GetUserInfoUseCase
import org.junit.jupiter.api.Assertions.assertEquals

internal class GetUserInfoUseCaseTest {

    private val repository: IUserInfoRepository = mockk(relaxed = true)
    private val useCase = GetUserInfoUseCase(repository)

    @Test
    fun `WHEN useCase is invoked - returns result in repository`() = runBlocking {
        val userInfo: UserInfo = mockk()
        every { repository.getUserInfo() }.returns(userInfo)
        val result = useCase()
        assertEquals(result, userInfo)
    }
}