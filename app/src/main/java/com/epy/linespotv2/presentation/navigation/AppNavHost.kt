package com.epy.linespotv2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epy.linespotv2.presentation.auth.login.LoginScreen
import com.epy.linespotv2.presentation.auth.register.RegisterScreen
import com.epy.linespotv2.presentation.home_customer.HomeScreen
import com.epy.linespotv2.presentation.home_jukir.HomeJukirScreen
import com.epy.linespotv2.presentation.input_manual.InputManualScreen
import com.epy.linespotv2.presentation.input_manual.PembayaranScreen
import com.epy.linespotv2.presentation.laporan.LaporanFilterScreen
import com.epy.linespotv2.presentation.laporan.LaporanScreen
import com.epy.linespotv2.presentation.riwayat.DetilRiwayatScreen
import com.epy.linespotv2.presentation.riwayat.RiwayatFilterScreen
import com.epy.linespotv2.presentation.riwayat.RiwayatScreen
import com.epy.linespotv2.presentation.post_qr.ScanScreen
import com.epy.linespotv2.presentation.settings.screen.SettingsScreen
import com.epy.linespotv2.presentation.splash.SplashScreen
import com.epy.linespotv2.presentation.subscribe.BenefitScreenPopUp
import com.epy.linespotv2.presentation.subscribe.EnterPromoScreenPopUp
import com.epy.linespotv2.presentation.subscribe.SubscribeScreen
import com.epy.linespotv2.presentation.subscribe.SubscribeScreenPopUpScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable ("splash"){
            SplashScreen(
                onNavigateToCustomerHome = {
                    navController.navigate("home_customer") {
                        popUpTo("splash") {inclusive = true}
                    }
                },
                onNavigateToJukirHome = {
                    navController.navigate("home_jukir") {
                        popUpTo("splash") {inclusive = true}
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") {inclusive = true}
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(

                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateToCustomerHome = {
                    // Pop up to login agar user tidak bisa back ke login
                    navController.navigate("home_customer") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToJukirHome = {
                    // Pop up to login
                    navController.navigate("home_jukir") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = {
                    // kembali ke login, hapus register dari backstack
                    navController.popBackStack()
                }
            )
        }

        composable("home_customer") {
            HomeScreen(
                onNavigateToPayment = { navController.navigate("scan") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToTopUp = { navController.navigate("topup")},
                onNavigateToSubscription = { navController.navigate("subscribe")},
                onNavigateToBooking = {navController.navigate("booking")},
                onNavigateToLogin = {navController.navigate("login")},
                onNavigateToLayananLain = {navController.navigate("layananlain")},
                onNavigateToPromo = {navController.navigate("promo")},
            )
        }

        composable("home_jukir") {
            HomeJukirScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToNotification = { navController.navigate("notification")},
                onNavigateToRiwayat = { navController.navigate("RiwayatFilter") },
                onNavigateToScanTicket = { navController.navigate("ScanTicket")},
                onNavigateToInputManual = {navController.navigate("inputmanual")},
                onNavigateToLaporan = { navController.navigate("laporanFilter") },
                onNavigateToBantuan = { navController.navigate("bantuan")},
                onNavigateToTopUp = {navController.navigate("topup")},
                onNavigateToLogin = {navController.navigate("login")},
            )
        }

        composable("settings") {
            SettingsScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }  // clear seluruh backstack
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Scanner ───────────────────────────────────────────────────────────
        composable("scan") {
            ScanScreen(
                onNavigateToHasilBayarParkir = {
                    // ScanResultHolder sudah diisi ScanViewModel sebelum ini dipanggil
                    navController.navigate("payment")
                }
            )
        }

        // ── Payment (struk + eksekusi) ────────────────────────────────────────
//        composable("payment") {
//            PaymentScreen(
//                onNavigateBackToScan = {
//                    // kembali ke scan, hapus payment dari backstack
//                    navController.popBackStack("scan", inclusive = false)
//                }
//            )
//        }

        composable ("topup") {
            //isi sama top up screen
        }
        // ── Subscribe ───────────────────────────────────────────────────────────
        composable ("subscribe") {
            SubscribeScreen (
                onBack = { navController.popBackStack() },
                onChoosePackage = { navController.navigate("subscribepopup")},
                onOpenBenefit = {navController.navigate("benefitpaket")},
                onOpenPromoCode = {navController.navigate("promoberlangganan")},
            )
        }

        composable ("subscribepopup"){
            SubscribeScreenPopUpScreen(
                onClose = {navController.popBackStack()},
                onSubscribeNow = {navController.navigate("")}
            )
        }

        composable ("benefitpaket"){
            BenefitScreenPopUp (
                onClose = {navController.popBackStack()},
                onAcknowledge = {navController.popBackStack()},
            )
        }

        composable ("promoberlangganan"){
            EnterPromoScreenPopUp (
                onClose = {navController.popBackStack()},
                onApply = {navController.navigate("")},
                onSelectPromo = {navController.navigate("")}
            )
        }

        composable ("layananlainnya") {
            // isi sama layanan lainnya screen
        }

        composable ("promo") {
            //isi sama promo screen
        }

        composable ("booking") {
            //isi sama booking screen
        }

        composable ("notifikasi") {
            //isi sama notif screen
        }

        // ── Riwayat ──────────────────────────────────────────────────────────
        composable ("riwayat"){
            RiwayatScreen (
                onBack = {navController.popBackStack()},
                onNavigateToDetail = {navController.navigate("DetailRiwayat")},
            )
        }

        composable ("RiwayatFilter"){
            RiwayatFilterScreen (
                onCancel = { navController.popBackStack() },
                onNavigateToRiwayat = { navController.navigate("riwayat") }
            )
        }

        composable ("DetailRiwayat"){
            DetilRiwayatScreen (
                //nanti diisi yah
            )
        }

        // ── Laporan ──────────────────────────────────────────────────────────

        composable("laporanFilter") {
            LaporanFilterScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { navController.navigate("laporan") }
            )
        }

        composable("laporan") {
            LaporanScreen(
                onBack = { navController.popBackStack() },
                onOpenFilter = { navController.navigate("laporanFilter") }
            )
        }

        //── Input Manual ──────────────────────────────────────────────────────────

        composable("inputmanual"){
            InputManualScreen(
            onNavigateToPembayaran = {navController.navigate("pembayaran")},
            )
        }

        composable("pembayaran"){
            PembayaranScreen(
            )
        }
    }
}
