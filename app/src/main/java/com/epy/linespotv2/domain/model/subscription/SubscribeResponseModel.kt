package com.epy.linespotv2.domain.model.subscription

data class SubscribeResponseModel(
    val statusCard: StatusCard,
    val packageCard: List<PackageCard>,
    val promo: List<Promo> ? = null,
)

data class StatusCard(
    val paketAktif: String,
    val kadaluarsa: String,
    val benefit: String,
)

data class PackageCard(
    val namaPaket : String,
    val harga: Long,
    val masaBerlaku : String,
    val jumlahDiskon : Long,
    val deskripsi : String,
    val benefit : List<String>,
)

data class Promo(
    val sNk : List <String>,
    val promo :List <PromoTerpilih>,
)

data class PromoTerpilih(
    val namaPromo : String,
    val deskripsi: String,
    val jumlahDiskon: Long,
)

