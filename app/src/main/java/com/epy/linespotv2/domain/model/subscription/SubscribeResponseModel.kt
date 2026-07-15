package com.epy.linespotv2.domain.model.subscription

data class SubscribeResponseModel(
    val statusCard: StatusCard? = null,
    val packageCard: List<PackageCard>? = null,
    val promo: List<Promo>? = null
)

data class StatusCard(
    val paketAktif: String? = null,
    val kadaluarsa: String? = null,
    val benefit: String? = null
)

data class PackageCard(
    val namaPaket: String? = null,
    val harga: Long? = null,
    val masaBerlaku: String? = null,
    val jumlahDiskon: Long? = null,
    val deskripsi: String? = null,
    val benefit: List<String>? = null
)

data class Promo(
    val sNk: List<String>? = null,
    val promo: List<PromoTerpilih>? = null
)

data class PromoTerpilih(
    val namaPromo: String? = null,
    val deskripsi: String? = null,
    val jumlahDiskon: Long? = null
)
