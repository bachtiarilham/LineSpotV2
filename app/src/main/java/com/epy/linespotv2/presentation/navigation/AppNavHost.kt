package com.epy.linespotv2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epy.linespotv2.presentation.riwayat.RiwayatViewModel
import com.epy.linespotv2.presentation.auth.login.LoginScreen
import com.epy.linespotv2.presentation.auth.register.RegisterScreen
//import com.epy.linespotv2.presentation.hasil_bayar_parkir.HasilBayarParkirScreen
import com.epy.linespotv2.presentation.home_customer.HomeCustomerScreen
import com.epy.linespotv2.presentation.home_jukir.HomeJukirScreen
import com.epy.linespotv2.presentation.input_manual.InputManualScreen
import com.epy.linespotv2.presentation.input_manual.InputManualViewModel
import com.epy.linespotv2.presentation.input_manual.PembayaranScreen
import com.epy.linespotv2.presentation.laporan.LaporanFilterScreen
import com.epy.linespotv2.presentation.laporan.LaporanScreen
import com.epy.linespotv2.presentation.laporan.LaporanViewModel
import com.epy.linespotv2.presentation.post_qr.ScanScreen
import com.epy.linespotv2.presentation.riwayat.DetilRiwayatScreen
import com.epy.linespotv2.presentation.riwayat.RiwayatFilterScreen
import com.epy.linespotv2.presentation.riwayat.RiwayatScreen
import com.epy.linespotv2.presentation.settings.screen.BantuanFaqScreen
import com.epy.linespotv2.presentation.settings.screen.KeamananScreen
import com.epy.linespotv2.presentation.settings.screen.MetodePembayaranScreen
import com.epy.linespotv2.presentation.settings.screen.PrivasiScreen
import com.epy.linespotv2.presentation.settings.screen.ProfilScreen
import com.epy.linespotv2.presentation.settings.screen.SettingsScreen
import com.epy.linespotv2.presentation.settings.screen.SyaratKetentuanScreen
import com.epy.linespotv2.presentation.settings.screen.TentangAplikasiScreen
import com.epy.linespotv2.presentation.splash.SplashScreen
import com.epy.linespotv2.presentation.subscribe.BenefitScreenPopUp
import com.epy.linespotv2.presentation.subscribe.EnterPromoScreenPopUp
import com.epy.linespotv2.presentation.subscribe.SubscribeScreen
import com.epy.linespotv2.presentation.subscribe.SubscribeScreenPopUpScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateToCustomerHome = {
                    navController.navigate("home_customer") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToJukirHome = {
                    navController.navigate("home_jukir") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
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
                    navController.navigate("home_customer") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToJukirHome = {
                    navController.navigate("home_jukir") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("home_customer") {
            HomeCustomerScreen(
                onNavigateToPayment = { navController.navigate("scan") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToTopUp = { navController.navigate("topup") },
                onNavigateToSubscription = { navController.navigate("subscribe") },
                onNavigateToBooking = { navController.navigate("booking") },
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToLayananLain = { navController.navigate("layananlainnya") },
                onNavigateToPromo = { navController.navigate("promo") }
            )
        }

        composable("home_jukir") {
            HomeJukirScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToNotification = { navController.navigate("notifikasi") },
                onNavigateToRiwayat = { navController.navigate("riwayat_filter") },
                onNavigateToScanTicket = { navController.navigate("scan") },
                onNavigateToInputManual = { navController.navigate("inputmanual") },
                onNavigateToLaporan = { navController.navigate("laporanFilter") },
                onNavigateToBantuan = { navController.navigate("bantuan") },
                onNavigateToTopUp = { navController.navigate("topup") },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("settings") {
            SettingsScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToProfil = { navController.navigate("profil") },
                onNavigateToMetodePembayaran = { navController.navigate("metode_pembayaran") },
                onNavigateToKeamanan = { navController.navigate("keamanan") },
                onNavigateToPrivasi = { navController.navigate("privasi") },
                onNavigateToBantuanFaq = { navController.navigate("bantuan_faq") },
                onNavigateToSyaratKetentuan = { navController.navigate("syarat_ketentuan") },
                onNavigateToTentangAplikasi = { navController.navigate("tentang_aplikasi") }
            )
        }

        composable("profil") {
            ProfilScreen(onBack = { navController.popBackStack() })
        }

        composable("keamanan") {
            KeamananScreen(onBack = { navController.popBackStack() })
        }

        composable("metode_pembayaran") {
            MetodePembayaranScreen(onBack = { navController.popBackStack() })
        }

        composable("privasi") {
            PrivasiScreen(onBack = { navController.popBackStack() })
        }

        composable("bantuan_faq") {
            BantuanFaqScreen(onBack = { navController.popBackStack() })
        }

        composable("syarat_ketentuan") {
            SyaratKetentuanScreen(onBack = { navController.popBackStack() })
        }

        composable("tentang_aplikasi") {
            TentangAplikasiScreen(onBack = { navController.popBackStack() })
        }

        composable("scan") {
            ScanScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHasilBayarParkir = {
                    navController.navigate("hasil_bayar_parkir")
                }
            )
        }

//        composable("hasil_bayar_parkir") {
//            HasilBayarParkirScreen(
//                onBack = { navController.popBackStack() },
//                onBackToHome = {
//                    navController.navigate("home_customer") {
//                        popUpTo("scan") { inclusive = true }
//                    }
//                }
//            )
//        }

        composable("topup") {
        }

        composable("subscribe") {
            SubscribeScreen(
                onBack = { navController.popBackStack() },
                onChoosePackage = { navController.navigate("subscribepopup") },
                onOpenBenefit = { navController.navigate("benefitpaket") },
                onOpenPromoCode = { navController.navigate("promoberlangganan") }
            )
        }

        composable("subscribepopup") {
            SubscribeScreenPopUpScreen(
                onClose = { navController.popBackStack() },
                onSubscribeNow = {}
            )
        }

        composable("benefitpaket") {
            BenefitScreenPopUp(
                onClose = { navController.popBackStack() },
                onAcknowledge = { navController.popBackStack() }
            )
        }

        composable("promoberlangganan") {
            EnterPromoScreenPopUp(
                onClose = { navController.popBackStack() },
                onApply = {},
                onSelectPromo = {}
            )
        }

        composable("layananlainnya") {
        }

        composable("promo") {
        }

        composable("booking") {
        }

        composable("notifikasi") {
        }

        composable("riwayat") {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("riwayat_filter")
            }
            val riwayatViewModel: RiwayatViewModel = hiltViewModel(parentEntry)
            RiwayatScreen(
                onBack = { navController.popBackStack() },
                onNavigateToDetail = { navController.navigate("detail_riwayat") },
                viewModel = riwayatViewModel
            )
        }

        composable("riwayat_filter") {
            RiwayatFilterScreen(
                onCancel = { navController.popBackStack() },
                onNavigateToRiwayat = {
                    navController.navigate("riwayat") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("detail_riwayat") {
            DetilRiwayatScreen()
        }

        composable("laporanFilter") {
            LaporanFilterScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { navController.navigate("laporan") {
                    launchSingleTop = true
                } }
            )
        }

        composable("laporan") {
//            LaporanScreen(
//                onBack = { navController.popBackStack() },
//                onOpenFilter = { navController.navigate("laporanFilter") }
//            )
            val parentEntry = remember(it) {
                navController.getBackStackEntry("laporanFilter")
            }
            val laporanViewModel: LaporanViewModel = hiltViewModel(parentEntry)
            LaporanScreen(
                onBack = { navController.popBackStack() },
                viewModel = laporanViewModel
            )
        }

        composable("inputmanual") {
            val inputManualViewModel: InputManualViewModel = hiltViewModel(it)
            InputManualScreen(
                onBack = { navController.popBackStack() },
                onNavigateToPembayaran = { navController.navigate("pembayaran") },
                viewModel = inputManualViewModel
            )
        }

        composable("pembayaran") {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("inputmanual")
            }
            val inputManualViewModel: InputManualViewModel = hiltViewModel(parentEntry)
            PembayaranScreen(
                onBack = { navController.popBackStack() },
                viewModel = inputManualViewModel
            )
        }
    }
}
