package com.lek.mybodyfatpercentage.onboarding.domain.usecase

import com.lek.mybodyfatpercentage.core.UseCase
import com.lek.mybodyfatpercentage.onboarding.domain.IUserInfoRepository
import com.lek.mybodyfatpercentage.onboarding.domain.UserInfo
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: IUserInfoRepository
) : UseCase<Unit, UserInfo?>() {

    override suspend fun run(param: Unit): UserInfo? = repository.getUserInfo()
}