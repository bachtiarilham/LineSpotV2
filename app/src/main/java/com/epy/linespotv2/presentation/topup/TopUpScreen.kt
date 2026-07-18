package com.epy.linespotv2.presentation.topup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.Black
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.presentation.topup.ui_model.TopUpNominalUiModel
import com.epy.linespotv2.presentation.topup.ui_model.TopUpPaymentMethodUiModel
import com.epy.linespotv2.presentation.topup.ui_model.TopUpUiModel

@Composable
fun TopUpScreen(
    onBack: () -> Unit = {},
    onTopUpCreated: (String) -> Unit = {},
    viewModel: TopUpViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.onIntent(TopUpIntent.LoadPage)
    }

    LaunchedEffect(state.topUpEffect) {
        when (val effect = state.topUpEffect) {
            TopUpEffect.NavigateBack -> {
                onBack()
                viewModel.consumeEffect()
            }
            is TopUpEffect.TopUpCreated -> {
                onTopUpCreated(effect.topupCode)
                viewModel.consumeEffect()
            }
            is TopUpEffect.ShowMessage,
            is TopUpEffect.TopUpPaid,
            is TopUpEffect.TopUpFailed,
            null -> Unit
        }
    }

    TopUpScreenContent(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun TopUpScreenContent(
    state: TopUpState,
    onIntent: (TopUpIntent) -> Unit
) {
    val uiModel = state.topUpUiModel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
    ) {
        TopUpTopBar(
            title = uiModel.title,
            onBack = { onIntent(TopUpIntent.ClickBack) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            if (state.isLoadingPage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = SmartBlue)
                }
            } else {
                Text(
                    text = "Nominal",
                    color = Black,
                    style = MaterialTheme.typography.titleMedium
                )

                NominalGrid(
                    options = uiModel.nominalOptions,
                    onSelect = { onIntent(TopUpIntent.SelectNominal(it)) }
                )

                NominalInputField(
                    value = uiModel.customNominalInput,
                    onValueChange = { onIntent(TopUpIntent.ChangeCustomNominal(it)) }
                )

                Text(
                    text = "Metode Pembayaran",
                    color = Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    uiModel.paymentMethods.forEach { method ->
                        PaymentMethodCard(
                            item = method,
                            onClick = { onIntent(TopUpIntent.SelectPaymentMethod(method.code)) }
                        )
                    }
                }

                if (!state.error.isNullOrBlank()) {
                    Text(
                        text = state.error,
                        color = Color(0xFFD92D20),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                SubmitButton(
                    enabled = uiModel.isSubmitEnabled && !state.isSubmitting,
                    isLoading = state.isSubmitting,
                    onClick = { onIntent(TopUpIntent.SubmitTopUp) }
                )
            }

            SecurityFooter()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TopUpTopBar(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF071B44), DarkBlue)
                )
            )
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Back",
                tint = White,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                color = White,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(54.dp))
        }
    }
}

@Composable
private fun NominalGrid(
    options: List<TopUpNominalUiModel>,
    onSelect: (Long) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        options.chunked(3).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                rowItems.forEach { item ->
                    NominalChip(
                        option = item,
                        modifier = Modifier.weight(1f),
                        onClick = { onSelect(item.amount) }
                    )
                }
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun NominalChip(
    option: TopUpNominalUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val background = if (option.isSelected) SmartBlue else White
    val contentColor = if (option.isSelected) White else Black
    val borderColor = if (option.isSelected) SmartBlue else Color(0xFFD9DEE8)

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = option.label.ifBlank { option.amount.toRupiah() },
            color = contentColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun NominalInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Input Nominal",
                color = GreyText,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PaymentMethodCard(
    item: TopUpPaymentMethodUiModel,
    onClick: () -> Unit
) {
    Surface(
        color = White,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (item.isSelected) 1.5.dp else 1.dp,
                color = if (item.isSelected) SmartBlue else Color(0xFFD9DEE8),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (item.isSelected) SmartBlue.copy(alpha = 0.12f) else Color(0xFFF4F6FA)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = item.title,
                    tint = if (item.isSelected) SmartBlue else DarkBlue,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subtitle,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (item.isSelected) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(SmartBlue)
                )
            }
        }
    }
}

@Composable
private fun SubmitButton(
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (enabled) SmartBlue else SmartBlue.copy(alpha = 0.45f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = White,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Lanjutkan Top Up",
                    color = White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun SecurityFooter() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Top up aman & terpercaya",
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(SmartBlue.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Security",
                tint = SmartBlue,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(SmartBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified",
                tint = White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TopUpScreenPreview() {
    MaterialTheme {
        TopUpScreenContent(
            state = TopUpState(
                topUpUiModel = TopUpUiModel(
                    nominalOptions = listOf(
                        TopUpNominalUiModel(optionId = 1L, amount = 50_000L, label = "Rp 50.000"),
                        TopUpNominalUiModel(optionId = 2L, amount = 100_000L, label = "Rp 100.000", isSelected = true),
                        TopUpNominalUiModel(optionId = 3L, amount = 200_000L, label = "Rp 200.000")
                    ),
                    paymentMethods = listOf(
                        TopUpPaymentMethodUiModel(
                            paymentMethodId = 1L,
                            title = "Virtual Account",
                            subtitle = "BCA_VA",
                            code = "BCA_VA",
                            isSelected = true
                        ),
                        TopUpPaymentMethodUiModel(
                            paymentMethodId = 2L,
                            title = "E-Wallet",
                            subtitle = "GOPAY",
                            code = "GOPAY"
                        )
                    ),
                    selectedNominalAmount = 100_000L,
                    selectedPaymentMethodCode = "BCA_VA",
                    isSubmitEnabled = true
                )
            ),
            onIntent = {}
        )
    }
}
