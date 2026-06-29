package com.epy.linespotv2.data.remote.api

import com.epy.linespotv2.core.network.ApiEnvelope
import com.epy.linespotv2.data.remote.dto.AuthLoginRequestDto
import com.epy.linespotv2.data.remote.dto.AuthLoginResultDto
import com.epy.linespotv2.data.remote.dto.HasilBayarParkirDto
import com.epy.linespotv2.data.remote.dto.HomeDto
import com.epy.linespotv2.data.remote.dto.InputManualRequestDto
import com.epy.linespotv2.data.remote.dto.LaporanDto
import com.epy.linespotv2.data.remote.dto.LaporanFilterRequestDto
import com.epy.linespotv2.data.remote.dto.LokasiDto
import com.epy.linespotv2.data.remote.dto.PembayaranDto
import com.epy.linespotv2.data.remote.dto.RefreshTokenRequestDto
import com.epy.linespotv2.data.remote.dto.RegisterRequestDto
import com.epy.linespotv2.data.remote.dto.RegisterResultDto
import com.epy.linespotv2.data.remote.dto.RiwayatDto
import com.epy.linespotv2.data.remote.dto.RiwayatRequestDto
import com.epy.linespotv2.data.remote.dto.SubmitQrRequestDto
import com.epy.linespotv2.data.remote.dto.SubscribeDto
import com.epy.linespotv2.data.remote.dto.UserProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET ("api/v2/linespot/home")
    suspend fun getHome() : ApiEnvelope<HomeDto>

    @POST("api/v2/linespot/auth/login")
    suspend fun login(
        @Body request: AuthLoginRequestDto
    ): ApiEnvelope<AuthLoginResultDto>

    @POST("api/v2/linespot/auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequestDto
    ): ApiEnvelope<AuthLoginResultDto>

    @POST("api/v2/linespot/auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): ApiEnvelope<RegisterResultDto>

//    @POST("api/v2/linespot/scan")
//    suspend fun submitQr(
//        @Body body: SubmitQrRequest
//    ): ApiEnvelope<ScanDetailDto>
//
//    // Tahap 3 — eksekusi bayar
//    @POST("api/v2/linespot/pay")
//    suspend fun executePayment(
//        @Body body: ExecutePaymentRequest
//    ): ApiEnvelope<PaymentInfoDto?>   // null response body = member Rp0

    @GET("api/v2/linespot/users/me")
    suspend fun getCurrentUser(): ApiEnvelope<UserProfileDto>

    @GET("api/v2/linespot/subscribe")
    suspend fun getPage() : ApiEnvelope<SubscribeDto>

    @POST("api/v2/linespot/riwayat")
    suspend fun getRiwayatPage(
        @Body request: RiwayatRequestDto
    ) : ApiEnvelope<RiwayatDto>

    @POST("api/v2/linespot/laporan")
    suspend fun getLaporanPage(
        @Body request: LaporanFilterRequestDto
    ) : ApiEnvelope<LaporanDto>

    @GET("api/v2/linespot/get_lokasi")
    suspend fun getLokasi() : ApiEnvelope<LokasiDto>

    @POST("api/v2/linespot/parking")
    suspend fun postParking(
        @Body body: InputManualRequestDto
    ) : ApiEnvelope<PembayaranDto>

    @GET("api/v2/linespot/parking/{sessionId}/status")
    suspend fun getPembayaranStatus(
        @Path("sessionId") sessionId: Long
    ): ApiEnvelope<PembayaranDto>

    @POST("api/v2/linespot/parking/payment")
    suspend fun postPaymentParking(
        @Body body: SubmitQrRequestDto
    ) : ApiEnvelope<HasilBayarParkirDto>

//    @GET("api/v2/linespot/riwayat_filter")
//    suspend fun getFilter() : ApiEnvelope<RiwayatFilterDto>

}

//    @POST("api/v2/linespot/auth/login")
//    suspend fun login(
//        @Body request: AuthLoginRequestDto
//    ): ApiEnvelope<AuthLoginResultDto>

//    @POST("api/v2/linespot/auth/refresh")
//    suspend fun refreshToken(
//        @Body request: RefreshTokenRequestDto
//    ): ApiEnvelope<AuthLoginResultDto>

//    @GET ("api/v2/linespot/dashboard")
//    suspend fun getHome(customerId : String) : ApiEnvelope<HomeDto>
//
//    @GET("api/v2/linespot/users/me")
//    suspend fun getCurrentUser(): ApiEnvelope<UserProfileDto>
//
//    @GET("api/v2/linespot/subscription/me")
//    suspend fun getSubscriptionStatus(): ApiEnvelope<SubscriptionStatusDto>
//
//    @GET("api/v2/linespot/parking/zones")
//    suspend fun getZones(): ApiEnvelope<ParkingZonesDto>
//
//    @GET("api/v2/linespot/home/dashboard")
//    suspend fun getHomeDashboard(): ApiEnvelope<HomeDashboardDto>
//
//    @GET("api/v2/linespot/parking/tariffs")
//    suspend fun getParkingTariffs(
//        @Query("lokasi_id") lokasiId: Long
//    ): ApiEnvelope<ParkingTariffListDto>
//
//    @GET("api/v2/linespot/parking/spots/availability")
//    suspend fun getAvailability(
//        @Query("lokasi_id") lokasiId: Long
//    ): ApiEnvelope<ParkingAvailabilityDto>
//
//    @POST("api/v2/linespot/pricing/estimate")
//    suspend fun estimatePricing(
//        @Body request: PricingEstimateRequestDto
//    ): ApiEnvelope<PricingEstimateDto>
//
//    @POST("api/v2/linespot/pricing-flat/estimate")
//    suspend fun estimateFlatPricing(
//        @Body request: PricingEstimateRequestDto
//    ): ApiEnvelope<FlatPricingEstimateDto>
//
//    @GET("api/v2/linespot/sessions/active")
//    suspend fun getActiveSession(
//        @Query("plate_number") plateNumber: String
//    ): ApiEnvelope<ActiveSessionDto>
//
//    @GET("api/v2/linespot/sessions-flat/active")
//    suspend fun getActiveFlatSession(
//        @Query("plate_number") plateNumber: String
//    ): ApiEnvelope<ActiveSessionDto>
//
//    @GET("api/v2/linespot/sessions")
//    suspend fun listSessions(
//        @Query("plate_number") plateNumber: String? = null,
//        @Query("status") status: String? = null
//    ): ApiEnvelope<SessionListDto>
//
//    @POST("api/v2/linespot/sessions/{id}/end")
//    suspend fun endSession(
//        @Path("id") sessionId: String,
//        @Body request: SessionEndRequestDto
//    ): ApiEnvelope<SessionEndResultDto>
//
//    @POST("api/v2/linespot/sessions-flat/{id}/end")
//    suspend fun endFlatSession(
//        @Path("id") sessionId: String,
//        @Body request: SessionFlatEndRequestDto
//    ): ApiEnvelope<SessionEndResultDto>
//
//    @Multipart
//    @POST("api/v2/linespot/payments/flat")
//    suspend fun createFlatPayment(
//        @Part("plate_number") plateNumber: RequestBody,
//        @Part("id_lokasi") lokasiId: RequestBody,
//        @Part("id_zona") zonaId: RequestBody,
//        @Part("id_jukir") jukirId: RequestBody,
//        @Part("customer_id") customerId: RequestBody?,
//        @Part("id_jen_bayar") jenisBayarId: RequestBody,
//        @Part("id_jen_kend") jenisKendaraanId: RequestBody,
//        @Part("amount") amount: RequestBody,
//        @Part("method") method: RequestBody,
//        @Part("membership_code") membershipCode: RequestBody?,
//        @Part("is_member") isMember: RequestBody,
//        @Part proofImage: MultipartBody.Part?
//    ): ApiEnvelope<SessionEndResultDto>
//
//    @POST("api/v2/linespot/payments/onstreet/prepare")
//    suspend fun prepareOnStreetPayment(
//        @Body request: OnStreetPaymentPrepareRequestDto
//    ): ApiEnvelope<OnStreetPreparedPaymentDto>
//
//    @POST("api/v2/linespot/payments/onstreet/complete")
//    suspend fun completeOnStreetPayment(
//        @Body request: OnStreetPaymentCompleteRequestDto
//    ): ApiEnvelope<SessionEndResultDto>
//
//    @GET("api/v2/linespot/payments/{id}")
//    suspend fun getPayment(
//        @Path("id") paymentId: String
//    ): ApiEnvelope<PaymentDto>
//
//    @GET("api/v2/linespot/history")
//    suspend fun getPaymentHistory(
//        @Query("period") period: String,
//        @Query("week_offset") weekOffset: Int = 0
//    ): ApiEnvelope<HistorySummaryDto>
//
//    @GET("api/v2/linespot/history/transactions")
//    suspend fun getHistoryTransactions(
//        @Query("period") period: String,
//        @Query("week_offset") weekOffset: Int = 0,
//        @Query("page") page: Int = 1,
//        @Query("limit") limit: Int = 8
//    ): ApiEnvelope<HistoryTransactionListDto>
//
//    @GET("api/v2/linespot/history/transactions/{id}")
//    suspend fun getHistoryTransactionDetail(
//        @Path("id") transactionId: Long
//    ): ApiEnvelope<HistoryTransactionDetailDto>
//
//    @GET("api/v2/linespot/history/weekly-chart")
//    suspend fun getHistoryWeeklyChart(
//        @Query("week_offset") weekOffset: Int = 0
//    ): ApiEnvelope<HistoryWeeklyChartDto>
//
//    @GET("api/v2/linespot/topup/overview")
//    suspend fun getTopUpOverview(): ApiEnvelope<TopUpOverviewDto>
//
//    @POST("api/v2/linespot/topup/orders")
//    suspend fun createTopUpOrder(
//        @Body request: CreateTopUpOrderRequestDto
//    ): ApiEnvelope<TopUpOrderDto>
//
//    @POST("api/v2/linespot/topup/orders/settle")
//    suspend fun settleTopUpOrder(
//        @Body request: SettleTopUpOrderRequestDto
//    ): ApiEnvelope<TopUpOrderDto>
//}
