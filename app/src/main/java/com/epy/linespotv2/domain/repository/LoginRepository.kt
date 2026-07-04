package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.auth.LoginModel

interface LoginRepository {
suspend fun login (username : String, password : String) : ApiCondition<LoginModel>
}