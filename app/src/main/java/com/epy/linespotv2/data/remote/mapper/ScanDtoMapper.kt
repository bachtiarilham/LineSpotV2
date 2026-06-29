package com.epy.linespotv2.data.remote.mapper

//fun ScanDetailDto.toDomain(): ScanDetail = ScanDetail(
//    customerId   = customerId ?: 0L,
//    customerName = customerName ?: "Pelanggan",
//    lokasi       = lokasi ?: "-",
//    duration     = duration ?: "-",
//    isMember     = isMember ?: false,
//    total        = total ?: 0L,
//    breakdown    = breakdown?.map { it.toDomain() }.orEmpty()
//)
//
//fun PriceItemDto.toDomain(): PriceItem = PriceItem(
//    label  = label.orEmpty(),
//    amount = amount ?: 0L
//)
//
//fun PaymentInfoDto.toDomain(): PaymentInfo = PaymentInfo(
//    type        = when (type?.uppercase()) {
//        "VIRTUAL_ACCOUNT" -> PaymentType.VIRTUAL_ACCOUNT
//        else              -> PaymentType.QRIS
//    },
//    qrisString  = qrisString,
//    vaNumber    = vaNumber,
//    bankName    = bankName,
//    amount      = amount ?: 0L,
//    expiredAt   = expiredAt.orEmpty()
//)