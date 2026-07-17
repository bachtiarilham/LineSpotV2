package com.epy.linespotv2.data.remote.dto.subscription

import com.google.gson.annotations.SerializedName

data class SubscribeResponseDto(
    @SerializedName("package_name")
    val activePackageName: String?,

    @SerializedName("package_expired")
    val activePackageExpired: String?,

    @SerializedName("benefit_package")
    val activePackageBenefits: List<String?>? = emptyList(),

    @SerializedName("list_paket")
    val listPaket: ListPaket?,

    @SerializedName("promo_tersedia")
    val promoTersedia: PromoTersedia?
)

data class ListPaket(
    @SerializedName("bulanan")
    val bulanan: List<DetailPaket?>? = emptyList(),

    @SerializedName("enam_bulan")
    val enamBulan: List<DetailPaket?>? = emptyList(),

    @SerializedName("tahunan")
    val tahunan: List<DetailPaket?>? = emptyList()
)

data class DetailPaket(
    @SerializedName("nama_paket")
    val namaPaket: String?,

    @SerializedName("harga")
    val harga: Long?,

    @SerializedName("coverage_lokasi")
    val coverageLokasi: List<String?>? = emptyList(),

    @SerializedName("benefit_package")
    val benefitPackage: List<String?>? = emptyList()
)

data class PromoTersedia(
    @SerializedName("syarat_dan_ketentuan")
    val syaratDanKetentuan: List<String?>? = emptyList(),

    @SerializedName("each_promo")
    val listPromo: List<DetailPromo?>? = emptyList()
)

data class DetailPromo(
    @SerializedName("nama_promo")
    val namaPromo: String?,

    @SerializedName("besar_diskon")
    val besarDiskon: Long? // Gunakan Double/Float jika diskon berupa persentase desimal (misal 15.5)
)