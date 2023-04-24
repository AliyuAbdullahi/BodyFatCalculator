package com.lek.mybodyfatpercentage.onboarding.data

import android.content.SharedPreferences
import app.cash.turbine.test
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import com.lek.mybodyfatpercentage.onboarding.data.UserInfoRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserInfoRepositoryTest {

    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val sharedPreference: SharedPreferences = mockk(relaxed = true)
    private val repository = UserInfoRepository(sharedPreference)

    @Test
    fun `when userInfo is set - repository cache useInfo`() = runBlocking {
        every { sharedPreference.edit() }.returns(editor)
        every { editor.putString(any(), any()) }.returns(editor)

        val userInfo = UserInfo(name = "testName", age = 33, bodyWeight = 75.3)
        repository.setUserInfo(userInfo)
        repository.userInfo.test {
            assertEquals(awaitItem(), userInfo)
        }
    }

    @Test
    fun `WHEN getUserInfo is invoked - returns existing info`() = runBlocking {
        val userInfo = UserInfo(name = "testName", age = 33, bodyWeight = 75.3)
        val expected = userInfo.toString()
        every { sharedPreference.getString(any(), any()) }.returns(expected)

        repository.getUserInfo()

        repository.userInfo.test {
            assertEquals(awaitItem(), userInfo)
        }
    }
}