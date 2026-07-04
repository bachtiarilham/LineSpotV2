package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.HomeModel
import com.epy.linespotv2.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Tugas utama UseCase (Domain Layer) adalah menjadi bos yang tahu apa yang harus dilakukan,
// tetapi tidak mau tahu teknisnya. UseCase hanya tahu: "Saya mau ambil data dashboard."
class HomeUseCase @Inject constructor(
    private val repository : HomeRepository
){
    operator fun invoke(
    ): Flow<ApiCondition<HomeModel>> = repository.getHome()
}