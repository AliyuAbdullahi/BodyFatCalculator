package com.lek.mybodyfatpercentage.onboarding.domain.usecase

import com.lek.mybodyfatpercentage.core.UseCase
import com.lek.mybodyfatpercentage.onboarding.domain.IUserInfoRepository
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import javax.inject.Inject

class SetUserInfoUseCase @Inject constructor(
    private val repository: IUserInfoRepository
) : UseCase<UserInfo, Unit>() {

    override suspend fun run(param: UserInfo) = repository.setUserInfo(param)
}