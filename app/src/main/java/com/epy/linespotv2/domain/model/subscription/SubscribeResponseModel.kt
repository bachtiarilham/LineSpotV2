package com.epy.linespotv2.domain.model.subscription

data class SubscribeResponseModel(
    val activePackageName: String?,
    val activePackageExpired: String?,
    val activePackageBenefits: List<String?>? = emptyList(),
    val listPaket: ListPaket?,
    val promoTersedia: PromoTersedia?
)

data class ListPaket(
    val bulanan: List<DetailPaket?>? = emptyList(),
    val enamBulan: List<DetailPaket?>? = emptyList(),
    val tahunan: List<DetailPaket?>? = emptyList()
)

data class DetailPaket(
    val namaPaket: String?,
    val harga: Long?,
    val coverageLokasi: List<String?>? = emptyList(),
    val benefitPackage: List<String?>? = emptyList()
)

data class PromoTersedia(
    val syaratDanKetentuan: List<String?>? = emptyList(),
    val listPromo: List<DetailPromo?>? = emptyList()
)

data class DetailPromo(
    val namaPromo: String?,
    val besarDiskon: Long?
)
