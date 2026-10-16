/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Schermata "Informazioni": versione, autore, licenza e un blocco di dati
 * tecnici copiabile con un tocco.
 *
 * Il pulsante di copia non e' un vezzo: la meta' del tempo speso su una
 * segnalazione se ne va nel ricostruire quale versione girava e su che
 * telefono. Con il blocco pronto da incollare quel giro si salta.
 */
package org.openusenet.reader.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import org.openusenet.reader.ui.icone.ArrowBack
import org.openusenet.reader.ui.icone.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import org.openusenet.reader.ui.rememberAppunti
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.openusenet.reader.Branding
import org.openusenet.reader.BuildConfig
import org.openusenet.reader.R
import org.openusenet.reader.ui.indietroSicuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(nav: NavHostController) {
    // 1.3.5: un solo punto in tutta l'app legge gli appunti (ui/Appunti.kt).
    val appunti = rememberAppunti()
    val ctx = LocalContext.current
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val diagnostica = remember {
        buildString {
            appendLine("${Branding.APP_NAME} ${BuildConfig.VERSION_NAME} (build ${BuildConfig.VERSION_CODE})")
            appendLine(ctx.getString(R.string.about_package, BuildConfig.APPLICATION_ID))
            appendLine("Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
            appendLine(ctx.getString(R.string.about_device, "${Build.MANUFACTURER} ${Build.MODEL}"))
        }.trimEnd()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.about_title)) },
                navigationIcon = {
                    IconButton(onClick = { nav.indietroSicuro() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbar) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            Text(
                Branding.APP_NAME,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                Branding.APP_TAGLINE,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                stringResource(R.string.about_version, BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp),
            )
            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(R.string.about_desc),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            Voce(stringResource(R.string.about_dev), "MCSM (aka Marco)")
            Voce(stringResource(R.string.about_license), stringResource(R.string.about_license_val))
            Voce(stringResource(R.string.about_built_with), "Kotlin, Jetpack Compose, Room")
            Voce(stringResource(R.string.about_protocol), stringResource(R.string.about_protocol_val))

            Spacer(Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(R.string.about_tech),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                stringResource(R.string.about_tech_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp),
            )
            Card(Modifier.fillMaxWidth()) {
                Text(
                    diagnostica,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(12.dp),
                )
            }
            OutlinedButton(
                onClick = {
                    appunti.copia(diagnostica)
                    scope.launch { snackbar.showSnackbar(ctx.getString(R.string.about_tech_copied)) }
                },
                modifier = Modifier.padding(top = 10.dp),
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = null)
                Text("  " + stringResource(R.string.common_copy), style = MaterialTheme.typography.labelLarge)
            }

            Spacer(Modifier.height(24.dp))
            Text(
                "Usenet Still Lives!",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun Voce(etichetta: String, valore: String) {
    Column(Modifier.padding(bottom = 12.dp)) {
        Text(
            etichetta,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(valore, style = MaterialTheme.typography.bodyLarge)
    }
}
