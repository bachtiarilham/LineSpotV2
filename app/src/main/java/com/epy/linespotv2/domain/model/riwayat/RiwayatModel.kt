package com.epy.linespotv2.domain.model.riwayat

data class RiwayatModel(
    val sections: List<RiwayatSection> = emptyList(),
)

data class RiwayatSection(
    val date: String,
    val items: List<RiwayatItem> = emptyList()
)

data class RiwayatItem(
    val code: String,
    val plateNumber: String,
    val vehicleType: String,
    val time: String,
    val amount: Long,
    val isEntry: Boolean
)

enum class RiwayatTransactionFilter {
    ALL,
    MASUK,
    KELUAR
}

enum class RiwayatPaymentFilter {
    ALL,
    TUNAI,
    NON_TUNAI
}

enum class RiwayatVehicleFilter {
    ALL,
    MOTOR,
    MOBIL
}
