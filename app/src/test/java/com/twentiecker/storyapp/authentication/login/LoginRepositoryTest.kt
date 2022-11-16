package com.twentiecker.storyapp.authentication.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.twentiecker.storyapp.api.ApiService
import com.twentiecker.storyapp.api.ApiResult
import com.twentiecker.storyapp.utils.DataDummy
import com.twentiecker.storyapp.utils.FakeApiService
import com.twentiecker.storyapp.utils.MainDispatcherRule
import com.twentiecker.storyapp.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var loginRepository: LoginRepository
    private val email: String = "twentiecker@gmail.com"
    private val pass: String = "123456"

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        apiService = FakeApiService()
        loginRepository = LoginRepository(apiService)
    }

    @Test
    fun `when Get LoginResponse Should Not Null`() = runTest {
        val expectedLogin = DataDummy.generateDummyLoginResponse()
        val actualLogin = loginRepository.loginService(email, pass)
        actualLogin.observeForTesting {
            assertNotNull(actualLogin)  // Memastikan data tidak null.
            assertEquals(
                expectedLogin, (actualLogin.value as ApiResult.Success).data
            ) // Memastikan data sesuai dengan yang diharapkan.
        }
    }
}