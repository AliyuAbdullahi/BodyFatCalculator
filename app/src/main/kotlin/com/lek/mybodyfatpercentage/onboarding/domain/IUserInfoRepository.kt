package com.lek.mybodyfatpercentage.onboarding.domain

import kotlinx.coroutines.flow.Flow

interface IUserInfoRepository {
    val userInfo: Flow<UserInfo?>
    fun getUserInfo(): UserInfo?
    fun setUserInfo(info: UserInfo)
    fun deleteUserInfo()
}