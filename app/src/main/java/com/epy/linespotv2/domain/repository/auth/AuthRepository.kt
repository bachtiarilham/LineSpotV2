package com.epy.linespotv2.domain.repository.auth

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.auth.ChangePasswordReqModel
import com.epy.linespotv2.domain.model.auth.LoginReqModel
import com.epy.linespotv2.domain.model.auth.LoginRespModel
import com.epy.linespotv2.domain.model.auth.RegisterReqModel

interface AuthRepository {
    suspend fun login (reqModel : LoginReqModel) : ApiCondition<LoginRespModel>
    suspend fun register( reqModel : RegisterReqModel): ApiCondition<String>
    suspend fun logout()
    suspend fun changePassword(reqModel : ChangePasswordReqModel) : ApiCondition<String>
}