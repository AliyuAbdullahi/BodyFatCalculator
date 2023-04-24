package com.lek.mybodyfatpercentage.onboarding.ui

import app.cash.turbine.test
import com.lek.mybodyfatpercentage.core.invoke
import com.lek.mybodyfatpercentage.onboarding.domain.Gender
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.GetUserInfoUseCase
import com.lek.mybodyfatpercentage.onboarding.domain.usecase.SetUserInfoUseCase
import com.lek.mybodyfatpercentage.onboarding.ui.UserInfoViewModel
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.AgeChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.DataSet
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.GenderChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.Info
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.NameChanged
import com.lek.mybodyfatpercentage.onboarding.ui.userinfo.state.WeightChanged
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
internal class UserInfoViewModelTest {

    private val getUserInfoUseCase: GetUserInfoUseCase = mockk(relaxed = true)
    private val setUserInfoUseCase: SetUserInfoUseCase = mockk(relaxed = true)
    private val viewModel = UserInfoViewModel(setUserInfoUseCase, getUserInfoUseCase)
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)

    @Nested
    @DisplayName("ViewModel is Initialization Test")
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    inner class ViewModelInitializationTest {
        @Test
        fun `WHEN viewModel is initialized for empty info - state is updated`() = runTest {
            Dispatchers.setMain(testDispatcher)
            val invalidInfo = UserInfo("", null, null)
            coEvery { getUserInfoUseCase() }.returns(invalidInfo)
            viewModel.initialize()
            viewModel.userInfoState.test {
                assertEquals(awaitItem(), Info("", 0, 0.0))
            }
        }

        @Test
        fun `WHEN viewModel is initialized with user info data - state is updated`() = runTest {
            Dispatchers.setMain(testDispatcher)
            val invalidInfo = UserInfo("testName", 22, 70.5)
            coEvery { getUserInfoUseCase() }.returns(invalidInfo)
            viewModel.initialize()
            viewModel.userInfoState.test {
                assertEquals(awaitItem(), DataSet)
            }
        }
    }

    @Nested
    @DisplayName("ViewModel Event Handler Test")
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    inner class ViewModelEventHandlingTest {

        @Test
        fun `WHEN viewModel#handleEvent(NameChanged) - state is updated`() = runTest {
            Dispatchers.setMain(testDispatcher)
            val invalidInfo = UserInfo("testName", null, null)
            coEvery { getUserInfoUseCase() }.returns(invalidInfo)
            viewModel.initialize()
            viewModel.userInfoState.test {
                assertEquals(awaitItem(), Info("testName", 0, 0.0))
            }
            viewModel.handleEvent(NameChanged("newName"))
            viewModel.userInfoState.test {
                assertEquals(Info("newName", 0, 0.0), awaitItem())
            }
        }

        @Test
        fun `WHEN viewModel#handleEvent(AgeChanged) - state is updated`() = runTest {
            Dispatchers.setMain(testDispatcher)
            val invalidInfo = UserInfo("testName", null, null)
            coEvery { getUserInfoUseCase() }.returns(invalidInfo)
            viewModel.initialize()
            viewModel.userInfoState.test {
                assertEquals(awaitItem(), Info("testName", 0, 0.0))
            }
            viewModel.handleEvent(AgeChanged("99"))
            viewModel.userInfoState.test {
                assertEquals(Info("testName", 99, 0.0), awaitItem())
            }
        }

        @Test
        fun `WHEN viewModel#handleEvent(WeightChanged) - state is updated`() = runTest {
            Dispatchers.setMain(testDispatcher)
            val invalidInfo = UserInfo("testName", null, null)
            coEvery { getUserInfoUseCase() }.returns(invalidInfo)
            viewModel.initialize()
            viewModel.userInfoState.test {
                assertEquals(awaitItem(), Info("testName", 0, 0.0))
            }
            viewModel.handleEvent(WeightChanged("74.3"))
            viewModel.userInfoState.test {
                assertEquals(Info("testName", 0, 74.3), awaitItem())
            }
        }

        @Test
        fun `WHEN viewModel#handleEvent(GenderChanged) - state is updated`() = runTest {
            Dispatchers.setMain(testDispatcher)
            val invalidInfo = UserInfo("testName", null, null)
            coEvery { getUserInfoUseCase() }.returns(invalidInfo)
            viewModel.initialize()
            viewModel.userInfoState.test {
                assertEquals(awaitItem(), Info("testName", 0, 0.0))
            }
            viewModel.handleEvent(GenderChanged(Gender.MALE))
            viewModel.userInfoState.test {
                assertEquals(Info("testName", 0, 0.0, gender = Gender.MALE), awaitItem())
            }
        }
    }
}