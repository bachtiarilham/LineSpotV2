package com.epy.linespotv2.data.remote.dto.subscription

import com.google.gson.annotations.SerializedName

data class SubscribeResponseDto(
    @SerializedName("status_card") val statusCard: StatusCardDto?,
    @SerializedName("package_card") val packageCard: List<PackageCardDto>?,
    @SerializedName("promo") val promo: List<PromoDto>?
)

data class StatusCardDto(
    @SerializedName("paket_aktif") val paketAktif: String?,
    @SerializedName("kadaluarsa") val kadaluarsa: String?,
    @SerializedName("benefit") val benefit: String?,
)

data class PackageCardDto(
    @SerializedName("nama_paket") val namaPaket : String?,
    @SerializedName("harga") val harga: Long?,
    @SerializedName("masa_berlaku") val masaBerlaku : String?,
    @SerializedName("jumlah_diskon") val jumlahDiskon : Long?,
    @SerializedName("deskripsi") val deskripsi : String?,
    @SerializedName("benefit") val benefit : List<String>?,
)

data class PromoDto(
    @SerializedName("snk") val sNk : List <String>?,
    @SerializedName("promo") val promo :List <PromoTerpilihDto>?,
)

data class PromoTerpilihDto(
    @SerializedName("nama_promo") val namaPromo : String?,
    @SerializedName("deskripsi") val deskripsi: String?,
    @SerializedName("jumlah_diskon") val jumlahDiskon: Long?,
)