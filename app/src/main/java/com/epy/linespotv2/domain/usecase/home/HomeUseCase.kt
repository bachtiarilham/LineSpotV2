package com.epy.linespotv2.domain.usecase.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.HomeResponseModel
import com.epy.linespotv2.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Tugas utama UseCase (Domain Layer) adalah menjadi bos yang tahu apa yang harus dilakukan,
// tetapi tidak mau tahu teknisnya. UseCase hanya tahu: "Saya mau ambil data dashboard."
class HomeUseCase @Inject constructor(
    private val repository : HomeRepository
){
    suspend operator fun invoke(
    ): Flow<ApiCondition<HomeResponseModel>> = repository.getHomePage()
}