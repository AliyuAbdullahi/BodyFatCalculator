package com.lek.mybodyfatpercentage.onboarding.data

import android.content.SharedPreferences
import com.lek.mybodyfatpercentage.onboarding.domain.IUserInfoRepository
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import com.lek.mybodyfatpercentage.onboarding.domain.toUserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

private const val KEY_USER_INFO = "user_info_key"

class UserInfoRepository(private val sharedPreferences: SharedPreferences) : IUserInfoRepository {

    private val mutableInfo: MutableSharedFlow<UserInfo?> = MutableSharedFlow(replay = 1)

    override val userInfo: Flow<UserInfo?> = mutableInfo

    override fun getUserInfo(): UserInfo? {
        val info = sharedPreferences.getString(KEY_USER_INFO, "")?.toUserInfo()
        mutableInfo.tryEmit(info)
        return info
    }

    override fun setUserInfo(info: UserInfo) {
        val newInfo = info.toString()
        sharedPreferences.edit().putString(KEY_USER_INFO, newInfo).apply()
        mutableInfo.tryEmit(info)
    }

    override fun deleteUserInfo() {
        sharedPreferences.edit().clear().apply()
        getUserInfo()
    }
}