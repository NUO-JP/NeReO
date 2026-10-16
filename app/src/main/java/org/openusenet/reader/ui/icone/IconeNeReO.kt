/*
 * OpenUsenet — Copyright (C) 2026 OpenUsenet contributors
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Le icone Material: Copyright Google LLC, Apache License 2.0.
 */
package org.openusenet.reader.ui.icone

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.unit.Dp

/*
 * 1.5.2 — LE 75 ICONE DI NeReO, E SOLO QUELLE.
 *
 * PERCHE' ESISTE (13 ottobre 2026, richiesta di Marco: «e' possibile ridurre
 * la grandezza del file APK?»). Misurato sull'APK 1.5.1/163: il 91% dell'APK
 * e' codice compilato, e il 55% di quel codice — 10,1 MB su 18,3 — erano le
 * 11.400 classi di `material-icons-extended`, una per icona. NeReO ne usa 75.
 * R8, che toglierebbe da solo il resto, e' spento per una decisione permanente
 * (i nomi delle enum salvati nel database): questa e' la strada che non la
 * tocca.
 *
 * COME SONO STATE OTTENUTE: NON copiate da un sito, ma LETTE DAL BYTECODE della
 * libreria dentro l'APK 157 (javap: moveTo, lineTo, curveToRelative... con le
 * loro costanti) e riscritte qui nella STESSA forma in cui la libreria le
 * costruisce: ImageVector.Builder + addPath, con gli stessi attributi di
 * `materialPath` (riempimento nero, tratto Butt/Bevel, larghezza e miter 1).
 * Verificato ESEGUENDO: ogni icona costruita da questo file e' stata
 * confrontata, nodo per nodo, con l'originale della libreria — vedi
 * controlli/icone/. Sono le stesse icone di prima, non icone somiglianti.
 *
 * GLI STESSI NOMI di prima, come proprieta' di estensione su Icons.Filled e
 * Icons.AutoMirrored.Filled: nel resto del codice e' cambiato SOLO l'import
 * (da androidx.compose.material.icons.filled.X a questo package).
 *
 * File GENERATO: un'icona nuova si aggiunge rigenerandolo, non a mano.
 */

private var _arrowBackSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.ArrowBack: ImageVector
    get() = _arrowBackSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.ArrowBack",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(20f, 11f)
            horizontalLineTo(7.83f)
            lineToRelative(5.59f, -5.59f)
            lineTo(12f, 4f)
            lineToRelative(-8f, 8f)
            lineToRelative(8f, 8f)
            lineToRelative(1.41f, -1.41f)
            lineTo(7.83f, 13f)
            horizontalLineTo(20f)
            verticalLineToRelative(-2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _arrowBackSpeculare = it }

private var _navigateBeforeSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.NavigateBefore: ImageVector
    get() = _navigateBeforeSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.NavigateBefore",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(15.41f, 7.41f)
            lineTo(14f, 6f)
            lineToRelative(-6f, 6f)
            lineToRelative(6f, 6f)
            lineToRelative(1.41f, -1.41f)
            lineTo(10.83f, 12f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _navigateBeforeSpeculare = it }

private var _navigateNextSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.NavigateNext: ImageVector
    get() = _navigateNextSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.NavigateNext",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(10f, 6f)
            lineTo(8.59f, 7.41f)
            lineTo(13.17f, 12f)
            lineToRelative(-4.58f, 4.59f)
            lineTo(10f, 18f)
            lineToRelative(6f, -6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _navigateNextSpeculare = it }

private var _openInNewSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.OpenInNew: ImageVector
    get() = _openInNewSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.OpenInNew",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 19f)
            horizontalLineTo(5f)
            verticalLineTo(5f)
            horizontalLineToRelative(7f)
            verticalLineTo(3f)
            horizontalLineTo(5f)
            curveToRelative(-1.11f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(14f)
            curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
            horizontalLineToRelative(14f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineToRelative(-7f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(7f)
            close()
            moveTo(14f, 3f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(3.59f)
            lineToRelative(-9.83f, 9.83f)
            lineToRelative(1.41f, 1.41f)
            lineTo(19f, 6.41f)
            verticalLineTo(10f)
            horizontalLineToRelative(2f)
            verticalLineTo(3f)
            horizontalLineToRelative(-7f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _openInNewSpeculare = it }

private var _replySpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.Reply: ImageVector
    get() = _replySpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.Reply",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(10f, 9f)
            verticalLineTo(5f)
            lineToRelative(-7f, 7f)
            lineToRelative(7f, 7f)
            verticalLineToRelative(-4.1f)
            curveToRelative(5f, 0f, 8.5f, 1.6f, 11f, 5.1f)
            curveToRelative(-1f, -5f, -4f, -10f, -11f, -11f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _replySpeculare = it }

private var _sendSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.Send: ImageVector
    get() = _sendSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.Send",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(2.01f, 21f)
            lineTo(23f, 12f)
            lineTo(2.01f, 3f)
            lineTo(2f, 10f)
            lineToRelative(15f, 2f)
            lineToRelative(-15f, 2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _sendSpeculare = it }

private var _subjectSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.Subject: ImageVector
    get() = _subjectSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.Subject",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(14f, 17f)
            lineTo(4f, 17f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(10f)
            verticalLineToRelative(-2f)
            close()
            moveTo(20f, 9f)
            lineTo(4f, 9f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(16f)
            lineTo(20f, 9f)
            close()
            moveTo(4f, 15f)
            horizontalLineToRelative(16f)
            verticalLineToRelative(-2f)
            lineTo(4f, 13f)
            verticalLineToRelative(2f)
            close()
            moveTo(4f, 5f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(16f)
            lineTo(20f, 5f)
            lineTo(4f, 5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _subjectSpeculare = it }

private var _tocSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.Toc: ImageVector
    get() = _tocSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.Toc",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(3f, 9f)
            horizontalLineToRelative(14f)
            lineTo(17f, 7f)
            lineTo(3f, 7f)
            verticalLineToRelative(2f)
            close()
            moveTo(3f, 13f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(-2f)
            lineTo(3f, 11f)
            verticalLineToRelative(2f)
            close()
            moveTo(3f, 17f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(-2f)
            lineTo(3f, 15f)
            verticalLineToRelative(2f)
            close()
            moveTo(19f, 17f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(19f, 7f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(2f)
            lineTo(21f, 7f)
            horizontalLineToRelative(-2f)
            close()
            moveTo(19f, 13f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _tocSpeculare = it }

private var _undoSpeculare: ImageVector? = null

val Icons.AutoMirrored.Filled.Undo: ImageVector
    get() = _undoSpeculare ?: ImageVector.Builder(
        name = "AutoMirrored.Filled.Undo",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12.5f, 8f)
            curveToRelative(-2.65f, 0f, -5.05f, 0.99f, -6.9f, 2.6f)
            lineTo(2f, 7f)
            verticalLineToRelative(9f)
            horizontalLineToRelative(9f)
            lineToRelative(-3.62f, -3.62f)
            curveToRelative(1.39f, -1.16f, 3.16f, -1.88f, 5.12f, -1.88f)
            curveToRelative(3.54f, 0f, 6.55f, 2.31f, 7.6f, 5.5f)
            lineToRelative(2.37f, -0.78f)
            curveTo(21.08f, 11.03f, 17.15f, 8f, 12.5f, 8f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _undoSpeculare = it }

private var _accountTree: ImageVector? = null

val Icons.Filled.AccountTree: ImageVector
    get() = _accountTree ?: ImageVector.Builder(
        name = "Filled.AccountTree",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(22f, 11f)
            verticalLineTo(3f)
            horizontalLineToRelative(-7f)
            verticalLineToRelative(3f)
            horizontalLineTo(9f)
            verticalLineTo(3f)
            horizontalLineTo(2f)
            verticalLineToRelative(8f)
            horizontalLineToRelative(7f)
            verticalLineTo(8f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(10f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(7f)
            verticalLineToRelative(-8f)
            horizontalLineToRelative(-7f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(-2f)
            verticalLineTo(8f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _accountTree = it }

private var _add: ImageVector? = null

val Icons.Filled.Add: ImageVector
    get() = _add ?: ImageVector.Builder(
        name = "Filled.Add",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 13f)
            horizontalLineToRelative(-6f)
            verticalLineToRelative(6f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-6f)
            horizontalLineTo(5f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(6f)
            verticalLineTo(5f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(6f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _add = it }

private var _addCircleOutline: ImageVector? = null

val Icons.Filled.AddCircleOutline: ImageVector
    get() = _addCircleOutline ?: ImageVector.Builder(
        name = "Filled.AddCircleOutline",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(13f, 7f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(4f)
            lineTo(7f, 11f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(4f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-4f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-4f)
            lineTo(13f, 7f)
            close()
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
            reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
            reflectiveCurveTo(17.52f, 2f, 12f, 2f)
            close()
            moveTo(12f, 20f)
            curveToRelative(-4.41f, 0f, -8f, -3.59f, -8f, -8f)
            reflectiveCurveToRelative(3.59f, -8f, 8f, -8f)
            reflectiveCurveToRelative(8f, 3.59f, 8f, 8f)
            reflectiveCurveToRelative(-3.59f, 8f, -8f, 8f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _addCircleOutline = it }

private var _alternateEmail: ImageVector? = null

val Icons.Filled.AlternateEmail: ImageVector
    get() = _alternateEmail ?: ImageVector.Builder(
        name = "Filled.AlternateEmail",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
            horizontalLineToRelative(5f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-5f)
            curveToRelative(-4.34f, 0f, -8f, -3.66f, -8f, -8f)
            reflectiveCurveToRelative(3.66f, -8f, 8f, -8f)
            reflectiveCurveToRelative(8f, 3.66f, 8f, 8f)
            verticalLineToRelative(1.43f)
            curveToRelative(0f, 0.79f, -0.71f, 1.57f, -1.5f, 1.57f)
            reflectiveCurveToRelative(-1.5f, -0.78f, -1.5f, -1.57f)
            lineTo(17f, 12f)
            curveToRelative(0f, -2.76f, -2.24f, -5f, -5f, -5f)
            reflectiveCurveToRelative(-5f, 2.24f, -5f, 5f)
            reflectiveCurveToRelative(2.24f, 5f, 5f, 5f)
            curveToRelative(1.38f, 0f, 2.64f, -0.56f, 3.54f, -1.47f)
            curveToRelative(0.65f, 0.89f, 1.77f, 1.47f, 2.96f, 1.47f)
            curveToRelative(1.97f, 0f, 3.5f, -1.6f, 3.5f, -3.57f)
            lineTo(22f, 12f)
            curveToRelative(0f, -5.52f, -4.48f, -10f, -10f, -10f)
            close()
            moveTo(12f, 15f)
            curveToRelative(-1.66f, 0f, -3f, -1.34f, -3f, -3f)
            reflectiveCurveToRelative(1.34f, -3f, 3f, -3f)
            reflectiveCurveToRelative(3f, 1.34f, 3f, 3f)
            reflectiveCurveToRelative(-1.34f, 3f, -3f, 3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _alternateEmail = it }

private var _block: ImageVector? = null

val Icons.Filled.Block: ImageVector
    get() = _block ?: ImageVector.Builder(
        name = "Filled.Block",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
            reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
            reflectiveCurveTo(17.52f, 2f, 12f, 2f)
            close()
            moveTo(4f, 12f)
            curveToRelative(0f, -4.42f, 3.58f, -8f, 8f, -8f)
            curveToRelative(1.85f, 0f, 3.55f, 0.63f, 4.9f, 1.69f)
            lineTo(5.69f, 16.9f)
            curveTo(4.63f, 15.55f, 4f, 13.85f, 4f, 12f)
            close()
            moveTo(12f, 20f)
            curveToRelative(-1.85f, 0f, -3.55f, -0.63f, -4.9f, -1.69f)
            lineTo(18.31f, 7.1f)
            curveTo(19.37f, 8.45f, 20f, 10.15f, 20f, 12f)
            curveToRelative(0f, 4.42f, -3.58f, 8f, -8f, 8f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _block = it }

private var _cancel: ImageVector? = null

val Icons.Filled.Cancel: ImageVector
    get() = _cancel ?: ImageVector.Builder(
        name = "Filled.Cancel",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.47f, 2f, 2f, 6.47f, 2f, 12f)
            reflectiveCurveToRelative(4.47f, 10f, 10f, 10f)
            reflectiveCurveToRelative(10f, -4.47f, 10f, -10f)
            reflectiveCurveTo(17.53f, 2f, 12f, 2f)
            close()
            moveTo(17f, 15.59f)
            lineTo(15.59f, 17f)
            lineTo(12f, 13.41f)
            lineTo(8.41f, 17f)
            lineTo(7f, 15.59f)
            lineTo(10.59f, 12f)
            lineTo(7f, 8.41f)
            lineTo(8.41f, 7f)
            lineTo(12f, 10.59f)
            lineTo(15.59f, 7f)
            lineTo(17f, 8.41f)
            lineTo(13.41f, 12f)
            lineTo(17f, 15.59f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _cancel = it }

private var _check: ImageVector? = null

val Icons.Filled.Check: ImageVector
    get() = _check ?: ImageVector.Builder(
        name = "Filled.Check",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(9f, 16.17f)
            lineTo(4.83f, 12f)
            lineToRelative(-1.42f, 1.41f)
            lineTo(9f, 19f)
            lineTo(21f, 7f)
            lineToRelative(-1.41f, -1.41f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _check = it }

private var _checkCircle: ImageVector? = null

val Icons.Filled.CheckCircle: ImageVector
    get() = _checkCircle ?: ImageVector.Builder(
        name = "Filled.CheckCircle",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
            reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
            reflectiveCurveTo(17.52f, 2f, 12f, 2f)
            close()
            moveTo(10f, 17f)
            lineToRelative(-5f, -5f)
            lineToRelative(1.41f, -1.41f)
            lineTo(10f, 14.17f)
            lineToRelative(7.59f, -7.59f)
            lineTo(19f, 8f)
            lineToRelative(-9f, 9f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _checkCircle = it }

private var _checklist: ImageVector? = null

val Icons.Filled.Checklist: ImageVector
    get() = _checklist ?: ImageVector.Builder(
        name = "Filled.Checklist",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(22f, 7f)
            horizontalLineToRelative(-9f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(9f)
            verticalLineTo(7f)
            close()
            moveTo(22f, 15f)
            horizontalLineToRelative(-9f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(9f)
            verticalLineTo(15f)
            close()
            moveTo(5.54f, 11f)
            lineTo(2f, 7.46f)
            lineToRelative(1.41f, -1.41f)
            lineToRelative(2.12f, 2.12f)
            lineToRelative(4.24f, -4.24f)
            lineToRelative(1.41f, 1.41f)
            lineTo(5.54f, 11f)
            close()
            moveTo(5.54f, 19f)
            lineTo(2f, 15.46f)
            lineToRelative(1.41f, -1.41f)
            lineToRelative(2.12f, 2.12f)
            lineToRelative(4.24f, -4.24f)
            lineToRelative(1.41f, 1.41f)
            lineTo(5.54f, 19f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _checklist = it }

private var _cleaningServices: ImageVector? = null

val Icons.Filled.CleaningServices: ImageVector
    get() = _cleaningServices ?: ImageVector.Builder(
        name = "Filled.CleaningServices",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(16f, 11f)
            horizontalLineToRelative(-1f)
            verticalLineTo(3f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            horizontalLineToRelative(-2f)
            curveTo(9.9f, 1f, 9f, 1.9f, 9f, 3f)
            verticalLineToRelative(8f)
            horizontalLineTo(8f)
            curveToRelative(-2.76f, 0f, -5f, 2.24f, -5f, 5f)
            verticalLineToRelative(7f)
            horizontalLineToRelative(18f)
            verticalLineToRelative(-7f)
            curveTo(21f, 13.24f, 18.76f, 11f, 16f, 11f)
            close()
            moveTo(19f, 21f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-3f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            reflectiveCurveToRelative(-1f, 0.45f, -1f, 1f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-3f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            reflectiveCurveToRelative(-1f, 0.45f, -1f, 1f)
            verticalLineToRelative(3f)
            horizontalLineTo(9f)
            verticalLineToRelative(-3f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            reflectiveCurveToRelative(-1f, 0.45f, -1f, 1f)
            verticalLineToRelative(3f)
            horizontalLineTo(5f)
            verticalLineToRelative(-5f)
            curveToRelative(0f, -1.65f, 1.35f, -3f, 3f, -3f)
            horizontalLineToRelative(8f)
            curveToRelative(1.65f, 0f, 3f, 1.35f, 3f, 3f)
            verticalLineTo(21f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _cleaningServices = it }

private var _close: ImageVector? = null

val Icons.Filled.Close: ImageVector
    get() = _close ?: ImageVector.Builder(
        name = "Filled.Close",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 6.41f)
            lineTo(17.59f, 5f)
            lineTo(12f, 10.59f)
            lineTo(6.41f, 5f)
            lineTo(5f, 6.41f)
            lineTo(10.59f, 12f)
            lineTo(5f, 17.59f)
            lineTo(6.41f, 19f)
            lineTo(12f, 13.41f)
            lineTo(17.59f, 19f)
            lineTo(19f, 17.59f)
            lineTo(13.41f, 12f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _close = it }

private var _contentCopy: ImageVector? = null

val Icons.Filled.ContentCopy: ImageVector
    get() = _contentCopy ?: ImageVector.Builder(
        name = "Filled.ContentCopy",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(16f, 1f)
            lineTo(4f, 1f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(14f)
            horizontalLineToRelative(2f)
            lineTo(4f, 3f)
            horizontalLineToRelative(12f)
            lineTo(16f, 1f)
            close()
            moveTo(19f, 5f)
            lineTo(8f, 5f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(14f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(11f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(21f, 7f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(19f, 21f)
            lineTo(8f, 21f)
            lineTo(8f, 7f)
            horizontalLineToRelative(11f)
            verticalLineToRelative(14f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _contentCopy = it }

private var _contentCut: ImageVector? = null

val Icons.Filled.ContentCut: ImageVector
    get() = _contentCut ?: ImageVector.Builder(
        name = "Filled.ContentCut",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(9.64f, 7.64f)
            curveToRelative(0.23f, -0.5f, 0.36f, -1.05f, 0.36f, -1.64f)
            curveToRelative(0f, -2.21f, -1.79f, -4f, -4f, -4f)
            reflectiveCurveTo(2f, 3.79f, 2f, 6f)
            reflectiveCurveToRelative(1.79f, 4f, 4f, 4f)
            curveToRelative(0.59f, 0f, 1.14f, -0.13f, 1.64f, -0.36f)
            lineTo(10f, 12f)
            lineToRelative(-2.36f, 2.36f)
            curveTo(7.14f, 14.13f, 6.59f, 14f, 6f, 14f)
            curveToRelative(-2.21f, 0f, -4f, 1.79f, -4f, 4f)
            reflectiveCurveToRelative(1.79f, 4f, 4f, 4f)
            reflectiveCurveToRelative(4f, -1.79f, 4f, -4f)
            curveToRelative(0f, -0.59f, -0.13f, -1.14f, -0.36f, -1.64f)
            lineTo(12f, 14f)
            lineToRelative(7f, 7f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(-1f)
            lineTo(9.64f, 7.64f)
            close()
            moveTo(6f, 8f)
            curveToRelative(-1.1f, 0f, -2f, -0.89f, -2f, -2f)
            reflectiveCurveToRelative(0.9f, -2f, 2f, -2f)
            reflectiveCurveToRelative(2f, 0.89f, 2f, 2f)
            reflectiveCurveToRelative(-0.9f, 2f, -2f, 2f)
            close()
            moveTo(6f, 20f)
            curveToRelative(-1.1f, 0f, -2f, -0.89f, -2f, -2f)
            reflectiveCurveToRelative(0.9f, -2f, 2f, -2f)
            reflectiveCurveToRelative(2f, 0.89f, 2f, 2f)
            reflectiveCurveToRelative(-0.9f, 2f, -2f, 2f)
            close()
            moveTo(12f, 12.5f)
            curveToRelative(-0.28f, 0f, -0.5f, -0.22f, -0.5f, -0.5f)
            reflectiveCurveToRelative(0.22f, -0.5f, 0.5f, -0.5f)
            reflectiveCurveToRelative(0.5f, 0.22f, 0.5f, 0.5f)
            reflectiveCurveToRelative(-0.22f, 0.5f, -0.5f, 0.5f)
            close()
            moveTo(19f, 3f)
            lineToRelative(-6f, 6f)
            lineToRelative(2f, 2f)
            lineToRelative(7f, -7f)
            lineTo(22f, 3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _contentCut = it }

private var _contentPaste: ImageVector? = null

val Icons.Filled.ContentPaste: ImageVector
    get() = _contentPaste ?: ImageVector.Builder(
        name = "Filled.ContentPaste",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 2f)
            horizontalLineToRelative(-4.18f)
            curveTo(14.4f, 0.84f, 13.3f, 0f, 12f, 0f)
            curveToRelative(-1.3f, 0f, -2.4f, 0.84f, -2.82f, 2f)
            lineTo(5f, 2f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(16f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(14f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(21f, 4f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(12f, 2f)
            curveToRelative(0.55f, 0f, 1f, 0.45f, 1f, 1f)
            reflectiveCurveToRelative(-0.45f, 1f, -1f, 1f)
            reflectiveCurveToRelative(-1f, -0.45f, -1f, -1f)
            reflectiveCurveToRelative(0.45f, -1f, 1f, -1f)
            close()
            moveTo(19f, 20f)
            lineTo(5f, 20f)
            lineTo(5f, 4f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(10f)
            lineTo(17f, 4f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(16f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _contentPaste = it }

private var _delete: ImageVector? = null

val Icons.Filled.Delete: ImageVector
    get() = _delete ?: ImageVector.Builder(
        name = "Filled.Delete",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(6f, 19f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(8f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineTo(7f)
            horizontalLineTo(6f)
            verticalLineToRelative(12f)
            close()
            moveTo(19f, 4f)
            horizontalLineToRelative(-3.5f)
            lineToRelative(-1f, -1f)
            horizontalLineToRelative(-5f)
            lineToRelative(-1f, 1f)
            horizontalLineTo(5f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(14f)
            verticalLineTo(4f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _delete = it }

private var _deleteSweep: ImageVector? = null

val Icons.Filled.DeleteSweep: ImageVector
    get() = _deleteSweep ?: ImageVector.Builder(
        name = "Filled.DeleteSweep",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(15f, 16f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(-4f)
            close()
            moveTo(15f, 8f)
            horizontalLineToRelative(7f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(-7f)
            close()
            moveTo(15f, 12f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(-6f)
            close()
            moveTo(3f, 18f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(6f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(13f, 8f)
            lineTo(3f, 8f)
            verticalLineToRelative(10f)
            close()
            moveTo(14f, 5f)
            horizontalLineToRelative(-3f)
            lineToRelative(-1f, -1f)
            lineTo(6f, 4f)
            lineTo(5f, 5f)
            lineTo(2f, 5f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(12f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _deleteSweep = it }

private var _dns: ImageVector? = null

val Icons.Filled.Dns: ImageVector
    get() = _dns ?: ImageVector.Builder(
        name = "Filled.Dns",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(20f, 13f)
            horizontalLineTo(4f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            verticalLineToRelative(6f)
            curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(16f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            verticalLineToRelative(-6f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            close()
            moveTo(7f, 19f)
            curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
            reflectiveCurveToRelative(0.9f, -2f, 2f, -2f)
            reflectiveCurveToRelative(2f, 0.9f, 2f, 2f)
            reflectiveCurveToRelative(-0.9f, 2f, -2f, 2f)
            close()
            moveTo(20f, 3f)
            horizontalLineTo(4f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            verticalLineToRelative(6f)
            curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(16f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            verticalLineTo(4f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            close()
            moveTo(7f, 9f)
            curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
            reflectiveCurveToRelative(0.9f, -2f, 2f, -2f)
            reflectiveCurveToRelative(2f, 0.9f, 2f, 2f)
            reflectiveCurveToRelative(-0.9f, 2f, -2f, 2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _dns = it }

private var _doneAll: ImageVector? = null

val Icons.Filled.DoneAll: ImageVector
    get() = _doneAll ?: ImageVector.Builder(
        name = "Filled.DoneAll",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(18f, 7f)
            lineToRelative(-1.41f, -1.41f)
            lineToRelative(-6.34f, 6.34f)
            lineToRelative(1.41f, 1.41f)
            lineTo(18f, 7f)
            close()
            moveTo(22.24f, 5.59f)
            lineTo(11.66f, 16.17f)
            lineTo(7.48f, 12f)
            lineToRelative(-1.41f, 1.41f)
            lineTo(11.66f, 19f)
            lineToRelative(12f, -12f)
            lineToRelative(-1.42f, -1.41f)
            close()
            moveTo(0.41f, 13.41f)
            lineTo(6f, 19f)
            lineToRelative(1.41f, -1.41f)
            lineTo(1.83f, 12f)
            lineTo(0.41f, 13.41f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _doneAll = it }

private var _download: ImageVector? = null

val Icons.Filled.Download: ImageVector
    get() = _download ?: ImageVector.Builder(
        name = "Filled.Download",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(5f, 20f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(-2f)
            horizontalLineTo(5f)
            verticalLineTo(20f)
            close()
            moveTo(19f, 9f)
            horizontalLineToRelative(-4f)
            verticalLineTo(3f)
            horizontalLineTo(9f)
            verticalLineToRelative(6f)
            horizontalLineTo(5f)
            lineToRelative(7f, 7f)
            lineTo(19f, 9f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _download = it }

private var _drafts: ImageVector? = null

val Icons.Filled.Drafts: ImageVector
    get() = _drafts ?: ImageVector.Builder(
        name = "Filled.Drafts",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(21.99f, 8f)
            curveToRelative(0f, -0.72f, -0.37f, -1.35f, -0.94f, -1.7f)
            lineTo(12f, 1f)
            lineTo(2.95f, 6.3f)
            curveTo(2.38f, 6.65f, 2f, 7.28f, 2f, 8f)
            verticalLineToRelative(10f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(16f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineToRelative(-0.01f, -10f)
            close()
            moveTo(12f, 13f)
            lineTo(3.74f, 7.84f)
            lineTo(12f, 3f)
            lineToRelative(8.26f, 4.84f)
            lineTo(12f, 13f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _drafts = it }

private var _edit: ImageVector? = null

val Icons.Filled.Edit: ImageVector
    get() = _edit ?: ImageVector.Builder(
        name = "Filled.Edit",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(3f, 17.25f)
            verticalLineTo(21f)
            horizontalLineToRelative(3.75f)
            lineTo(17.81f, 9.94f)
            lineToRelative(-3.75f, -3.75f)
            lineTo(3f, 17.25f)
            close()
            moveTo(20.71f, 7.04f)
            curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0f, -1.41f)
            lineToRelative(-2.34f, -2.34f)
            curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0f)
            lineToRelative(-1.83f, 1.83f)
            lineToRelative(3.75f, 3.75f)
            lineToRelative(1.83f, -1.83f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _edit = it }

private var _email: ImageVector? = null

val Icons.Filled.Email: ImageVector
    get() = _email ?: ImageVector.Builder(
        name = "Filled.Email",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(20f, 4f)
            lineTo(4f, 4f)
            curveToRelative(-1.1f, 0f, -1.99f, 0.9f, -1.99f, 2f)
            lineTo(2f, 18f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(16f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(22f, 6f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(20f, 8f)
            lineToRelative(-8f, 5f)
            lineToRelative(-8f, -5f)
            lineTo(4f, 6f)
            lineToRelative(8f, 5f)
            lineToRelative(8f, -5f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _email = it }

private var _enhancedEncryption: ImageVector? = null

val Icons.Filled.EnhancedEncryption: ImageVector
    get() = _enhancedEncryption ?: ImageVector.Builder(
        name = "Filled.EnhancedEncryption",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(18f, 8f)
            horizontalLineToRelative(-1f)
            verticalLineTo(6f)
            curveToRelative(0f, -2.76f, -2.24f, -5f, -5f, -5f)
            reflectiveCurveTo(7f, 3.24f, 7f, 6f)
            verticalLineToRelative(2f)
            horizontalLineTo(6f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(10f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(12f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineTo(10f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(8.9f, 6f)
            curveToRelative(0f, -1.71f, 1.39f, -3.1f, 3.1f, -3.1f)
            reflectiveCurveToRelative(3.1f, 1.39f, 3.1f, 3.1f)
            verticalLineToRelative(2f)
            horizontalLineTo(8.9f)
            verticalLineTo(6f)
            close()
            moveTo(16f, 16f)
            horizontalLineToRelative(-3f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-3f)
            horizontalLineTo(8f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(-3f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _enhancedEncryption = it }

private var _expandLess: ImageVector? = null

val Icons.Filled.ExpandLess: ImageVector
    get() = _expandLess ?: ImageVector.Builder(
        name = "Filled.ExpandLess",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 8f)
            lineToRelative(-6f, 6f)
            lineToRelative(1.41f, 1.41f)
            lineTo(12f, 10.83f)
            lineToRelative(4.59f, 4.58f)
            lineTo(18f, 14f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _expandLess = it }

private var _expandMore: ImageVector? = null

val Icons.Filled.ExpandMore: ImageVector
    get() = _expandMore ?: ImageVector.Builder(
        name = "Filled.ExpandMore",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(16.59f, 8.59f)
            lineTo(12f, 13.17f)
            lineTo(7.41f, 8.59f)
            lineTo(6f, 10f)
            lineToRelative(6f, 6f)
            lineToRelative(6f, -6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _expandMore = it }

private var _fileDownload: ImageVector? = null

val Icons.Filled.FileDownload: ImageVector
    get() = _fileDownload ?: ImageVector.Builder(
        name = "Filled.FileDownload",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 9f)
            horizontalLineToRelative(-4f)
            verticalLineTo(3f)
            horizontalLineTo(9f)
            verticalLineToRelative(6f)
            horizontalLineTo(5f)
            lineToRelative(7f, 7f)
            lineToRelative(7f, -7f)
            close()
            moveTo(5f, 18f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(-2f)
            horizontalLineTo(5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _fileDownload = it }

private var _fileUpload: ImageVector? = null

val Icons.Filled.FileUpload: ImageVector
    get() = _fileUpload ?: ImageVector.Builder(
        name = "Filled.FileUpload",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(9f, 16f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(-6f)
            horizontalLineToRelative(4f)
            lineToRelative(-7f, -7f)
            lineToRelative(-7f, 7f)
            horizontalLineToRelative(4f)
            close()
            moveTo(5f, 18f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(2f)
            lineTo(5f, 20f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _fileUpload = it }

private var _filterAlt: ImageVector? = null

val Icons.Filled.FilterAlt: ImageVector
    get() = _filterAlt ?: ImageVector.Builder(
        name = "Filled.FilterAlt",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(4.25f, 5.61f)
            curveTo(6.27f, 8.2f, 10f, 13f, 10f, 13f)
            verticalLineToRelative(6f)
            curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(2f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            verticalLineToRelative(-6f)
            curveToRelative(0f, 0f, 3.72f, -4.8f, 5.74f, -7.39f)
            curveTo(20.25f, 4.95f, 19.78f, 4f, 18.95f, 4f)
            horizontalLineTo(5.04f)
            curveTo(4.21f, 4f, 3.74f, 4.95f, 4.25f, 5.61f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _filterAlt = it }

private var _formatQuote: ImageVector? = null

val Icons.Filled.FormatQuote: ImageVector
    get() = _formatQuote ?: ImageVector.Builder(
        name = "Filled.FormatQuote",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(6f, 17f)
            horizontalLineToRelative(3f)
            lineToRelative(2f, -4f)
            lineTo(11f, 7f)
            lineTo(5f, 7f)
            verticalLineToRelative(6f)
            horizontalLineToRelative(3f)
            close()
            moveTo(14f, 17f)
            horizontalLineToRelative(3f)
            lineToRelative(2f, -4f)
            lineTo(19f, 7f)
            horizontalLineToRelative(-6f)
            verticalLineToRelative(6f)
            horizontalLineToRelative(3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _formatQuote = it }

private var _fullscreen: ImageVector? = null

val Icons.Filled.Fullscreen: ImageVector
    get() = _fullscreen ?: ImageVector.Builder(
        name = "Filled.Fullscreen",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(7f, 14f)
            lineTo(5f, 14f)
            verticalLineToRelative(5f)
            horizontalLineToRelative(5f)
            verticalLineToRelative(-2f)
            lineTo(7f, 17f)
            verticalLineToRelative(-3f)
            close()
            moveTo(5f, 10f)
            horizontalLineToRelative(2f)
            lineTo(7f, 7f)
            horizontalLineToRelative(3f)
            lineTo(10f, 5f)
            lineTo(5f, 5f)
            verticalLineToRelative(5f)
            close()
            moveTo(17f, 17f)
            horizontalLineToRelative(-3f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(5f)
            verticalLineToRelative(-5f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(3f)
            close()
            moveTo(14f, 5f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(2f)
            lineTo(19f, 5f)
            horizontalLineToRelative(-5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _fullscreen = it }

private var _fullscreenExit: ImageVector? = null

val Icons.Filled.FullscreenExit: ImageVector
    get() = _fullscreenExit ?: ImageVector.Builder(
        name = "Filled.FullscreenExit",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(5f, 16f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-5f)
            lineTo(5f, 14f)
            verticalLineToRelative(2f)
            close()
            moveTo(8f, 8f)
            lineTo(5f, 8f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(5f)
            lineTo(10f, 5f)
            lineTo(8f, 5f)
            verticalLineToRelative(3f)
            close()
            moveTo(14f, 19f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-3f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-5f)
            verticalLineToRelative(5f)
            close()
            moveTo(16f, 8f)
            lineTo(16f, 5f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(5f)
            horizontalLineToRelative(5f)
            lineTo(19f, 8f)
            horizontalLineToRelative(-3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _fullscreenExit = it }

private var _groups: ImageVector? = null

val Icons.Filled.Groups: ImageVector
    get() = _groups ?: ImageVector.Builder(
        name = "Filled.Groups",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 12.75f)
            curveToRelative(1.63f, 0f, 3.07f, 0.39f, 4.24f, 0.9f)
            curveToRelative(1.08f, 0.48f, 1.76f, 1.56f, 1.76f, 2.73f)
            lineTo(18f, 18f)
            horizontalLineTo(6f)
            lineToRelative(0f, -1.61f)
            curveToRelative(0f, -1.18f, 0.68f, -2.26f, 1.76f, -2.73f)
            curveTo(8.93f, 13.14f, 10.37f, 12.75f, 12f, 12.75f)
            close()
            moveTo(4f, 13f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            reflectiveCurveToRelative(-2f, 0.9f, -2f, 2f)
            curveTo(2f, 12.1f, 2.9f, 13f, 4f, 13f)
            close()
            moveTo(5.13f, 14.1f)
            curveTo(4.76f, 14.04f, 4.39f, 14f, 4f, 14f)
            curveToRelative(-0.99f, 0f, -1.93f, 0.21f, -2.78f, 0.58f)
            curveTo(0.48f, 14.9f, 0f, 15.62f, 0f, 16.43f)
            verticalLineTo(18f)
            lineToRelative(4.5f, 0f)
            verticalLineToRelative(-1.61f)
            curveTo(4.5f, 15.56f, 4.73f, 14.78f, 5.13f, 14.1f)
            close()
            moveTo(20f, 13f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            reflectiveCurveToRelative(-2f, 0.9f, -2f, 2f)
            curveTo(18f, 12.1f, 18.9f, 13f, 20f, 13f)
            close()
            moveTo(24f, 16.43f)
            curveToRelative(0f, -0.81f, -0.48f, -1.53f, -1.22f, -1.85f)
            curveTo(21.93f, 14.21f, 20.99f, 14f, 20f, 14f)
            curveToRelative(-0.39f, 0f, -0.76f, 0.04f, -1.13f, 0.1f)
            curveToRelative(0.4f, 0.68f, 0.63f, 1.46f, 0.63f, 2.29f)
            verticalLineTo(18f)
            lineToRelative(4.5f, 0f)
            verticalLineTo(16.43f)
            close()
            moveTo(12f, 6f)
            curveToRelative(1.66f, 0f, 3f, 1.34f, 3f, 3f)
            curveToRelative(0f, 1.66f, -1.34f, 3f, -3f, 3f)
            reflectiveCurveToRelative(-3f, -1.34f, -3f, -3f)
            curveTo(9f, 7.34f, 10.34f, 6f, 12f, 6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _groups = it }

private var _info: ImageVector? = null

val Icons.Filled.Info: ImageVector
    get() = _info ?: ImageVector.Builder(
        name = "Filled.Info",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
            reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
            reflectiveCurveTo(17.52f, 2f, 12f, 2f)
            close()
            moveTo(13f, 17f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-6f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(6f)
            close()
            moveTo(13f, 9f)
            horizontalLineToRelative(-2f)
            lineTo(11f, 7f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _info = it }

private var _keyboardArrowDown: ImageVector? = null

val Icons.Filled.KeyboardArrowDown: ImageVector
    get() = _keyboardArrowDown ?: ImageVector.Builder(
        name = "Filled.KeyboardArrowDown",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(7.41f, 8.59f)
            lineTo(12f, 13.17f)
            lineToRelative(4.59f, -4.58f)
            lineTo(18f, 10f)
            lineToRelative(-6f, 6f)
            lineToRelative(-6f, -6f)
            lineToRelative(1.41f, -1.41f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _keyboardArrowDown = it }

private var _keyboardArrowUp: ImageVector? = null

val Icons.Filled.KeyboardArrowUp: ImageVector
    get() = _keyboardArrowUp ?: ImageVector.Builder(
        name = "Filled.KeyboardArrowUp",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(7.41f, 15.41f)
            lineTo(12f, 10.83f)
            lineToRelative(4.59f, 4.58f)
            lineTo(18f, 14f)
            lineToRelative(-6f, -6f)
            lineToRelative(-6f, 6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _keyboardArrowUp = it }

private var _keyboardDoubleArrowLeft: ImageVector? = null

val Icons.Filled.KeyboardDoubleArrowLeft: ImageVector
    get() = _keyboardDoubleArrowLeft ?: ImageVector.Builder(
        name = "Filled.KeyboardDoubleArrowLeft",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(17.59f, 18f)
            lineToRelative(1.41f, -1.41f)
            lineToRelative(-4.58f, -4.59f)
            lineToRelative(4.58f, -4.59f)
            lineToRelative(-1.41f, -1.41f)
            lineToRelative(-6f, 6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(11f, 18f)
            lineToRelative(1.41f, -1.41f)
            lineToRelative(-4.58f, -4.59f)
            lineToRelative(4.58f, -4.59f)
            lineToRelative(-1.41f, -1.41f)
            lineToRelative(-6f, 6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _keyboardDoubleArrowLeft = it }

private var _keyboardDoubleArrowRight: ImageVector? = null

val Icons.Filled.KeyboardDoubleArrowRight: ImageVector
    get() = _keyboardDoubleArrowRight ?: ImageVector.Builder(
        name = "Filled.KeyboardDoubleArrowRight",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(6.41f, 6f)
            lineToRelative(-1.41f, 1.41f)
            lineToRelative(4.58f, 4.59f)
            lineToRelative(-4.58f, 4.59f)
            lineToRelative(1.41f, 1.41f)
            lineToRelative(6f, -6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(13f, 6f)
            lineToRelative(-1.41f, 1.41f)
            lineToRelative(4.58f, 4.59f)
            lineToRelative(-4.58f, 4.59f)
            lineToRelative(1.41f, 1.41f)
            lineToRelative(6f, -6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _keyboardDoubleArrowRight = it }

private var _link: ImageVector? = null

val Icons.Filled.Link: ImageVector
    get() = _link ?: ImageVector.Builder(
        name = "Filled.Link",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(3.9f, 12f)
            curveToRelative(0f, -1.71f, 1.39f, -3.1f, 3.1f, -3.1f)
            horizontalLineToRelative(4f)
            lineTo(11f, 7f)
            lineTo(7f, 7f)
            curveToRelative(-2.76f, 0f, -5f, 2.24f, -5f, 5f)
            reflectiveCurveToRelative(2.24f, 5f, 5f, 5f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(-1.9f)
            lineTo(7f, 15.1f)
            curveToRelative(-1.71f, 0f, -3.1f, -1.39f, -3.1f, -3.1f)
            close()
            moveTo(8f, 13f)
            horizontalLineToRelative(8f)
            verticalLineToRelative(-2f)
            lineTo(8f, 11f)
            verticalLineToRelative(2f)
            close()
            moveTo(17f, 7f)
            horizontalLineToRelative(-4f)
            verticalLineToRelative(1.9f)
            horizontalLineToRelative(4f)
            curveToRelative(1.71f, 0f, 3.1f, 1.39f, 3.1f, 3.1f)
            reflectiveCurveToRelative(-1.39f, 3.1f, -3.1f, 3.1f)
            horizontalLineToRelative(-4f)
            lineTo(13f, 17f)
            horizontalLineToRelative(4f)
            curveToRelative(2.76f, 0f, 5f, -2.24f, 5f, -5f)
            reflectiveCurveToRelative(-2.24f, -5f, -5f, -5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _link = it }

private var _lock: ImageVector? = null

val Icons.Filled.Lock: ImageVector
    get() = _lock ?: ImageVector.Builder(
        name = "Filled.Lock",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(18f, 8f)
            horizontalLineToRelative(-1f)
            lineTo(17f, 6f)
            curveToRelative(0f, -2.76f, -2.24f, -5f, -5f, -5f)
            reflectiveCurveTo(7f, 3.24f, 7f, 6f)
            verticalLineToRelative(2f)
            lineTo(6f, 8f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(10f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(12f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(20f, 10f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(12f, 17f)
            curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
            reflectiveCurveToRelative(0.9f, -2f, 2f, -2f)
            reflectiveCurveToRelative(2f, 0.9f, 2f, 2f)
            reflectiveCurveToRelative(-0.9f, 2f, -2f, 2f)
            close()
            moveTo(15.1f, 8f)
            lineTo(8.9f, 8f)
            lineTo(8.9f, 6f)
            curveToRelative(0f, -1.71f, 1.39f, -3.1f, 3.1f, -3.1f)
            curveToRelative(1.71f, 0f, 3.1f, 1.39f, 3.1f, 3.1f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _lock = it }

private var _markChatUnread: ImageVector? = null

val Icons.Filled.MarkChatUnread: ImageVector
    get() = _markChatUnread ?: ImageVector.Builder(
        name = "Filled.MarkChatUnread",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(22f, 6.98f)
            verticalLineTo(16f)
            curveToRelative(0f, 1.1f, -0.9f, 2f, -2f, 2f)
            horizontalLineTo(6f)
            lineToRelative(-4f, 4f)
            verticalLineTo(4f)
            curveToRelative(0f, -1.1f, 0.9f, -2f, 2f, -2f)
            horizontalLineToRelative(10.1f)
            curveTo(14.04f, 2.32f, 14f, 2.66f, 14f, 3f)
            curveToRelative(0f, 2.76f, 2.24f, 5f, 5f, 5f)
            curveTo(20.13f, 8f, 21.16f, 7.61f, 22f, 6.98f)
            close()
            moveTo(16f, 3f)
            curveToRelative(0f, 1.66f, 1.34f, 3f, 3f, 3f)
            reflectiveCurveToRelative(3f, -1.34f, 3f, -3f)
            reflectiveCurveToRelative(-1.34f, -3f, -3f, -3f)
            reflectiveCurveTo(16f, 1.34f, 16f, 3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _markChatUnread = it }

private var _markunread: ImageVector? = null

val Icons.Filled.Markunread: ImageVector
    get() = _markunread ?: ImageVector.Builder(
        name = "Filled.Markunread",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(20f, 4f)
            lineTo(4f, 4f)
            curveToRelative(-1.1f, 0f, -1.99f, 0.9f, -1.99f, 2f)
            lineTo(2f, 18f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(16f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(22f, 6f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(20f, 8f)
            lineToRelative(-8f, 5f)
            lineToRelative(-8f, -5f)
            lineTo(4f, 6f)
            lineToRelative(8f, 5f)
            lineToRelative(8f, -5f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _markunread = it }

private var _moreVert: ImageVector? = null

val Icons.Filled.MoreVert: ImageVector
    get() = _moreVert ?: ImageVector.Builder(
        name = "Filled.MoreVert",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 8f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            reflectiveCurveToRelative(-0.9f, -2f, -2f, -2f)
            reflectiveCurveToRelative(-2f, 0.9f, -2f, 2f)
            reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
            close()
            moveTo(12f, 10f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
            reflectiveCurveToRelative(2f, -0.9f, 2f, -2f)
            reflectiveCurveToRelative(-0.9f, -2f, -2f, -2f)
            close()
            moveTo(12f, 16f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
            reflectiveCurveToRelative(2f, -0.9f, 2f, -2f)
            reflectiveCurveToRelative(-0.9f, -2f, -2f, -2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _moreVert = it }

private var _myLocation: ImageVector? = null

val Icons.Filled.MyLocation: ImageVector
    get() = _myLocation ?: ImageVector.Builder(
        name = "Filled.MyLocation",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 8f)
            curveToRelative(-2.21f, 0f, -4f, 1.79f, -4f, 4f)
            reflectiveCurveToRelative(1.79f, 4f, 4f, 4f)
            reflectiveCurveToRelative(4f, -1.79f, 4f, -4f)
            reflectiveCurveToRelative(-1.79f, -4f, -4f, -4f)
            close()
            moveTo(20.94f, 11f)
            curveToRelative(-0.46f, -4.17f, -3.77f, -7.48f, -7.94f, -7.94f)
            lineTo(13f, 1f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2.06f)
            curveTo(6.83f, 3.52f, 3.52f, 6.83f, 3.06f, 11f)
            lineTo(1f, 11f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(2.06f)
            curveToRelative(0.46f, 4.17f, 3.77f, 7.48f, 7.94f, 7.94f)
            lineTo(11f, 23f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2.06f)
            curveToRelative(4.17f, -0.46f, 7.48f, -3.77f, 7.94f, -7.94f)
            lineTo(23f, 13f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2.06f)
            close()
            moveTo(12f, 19f)
            curveToRelative(-3.87f, 0f, -7f, -3.13f, -7f, -7f)
            reflectiveCurveToRelative(3.13f, -7f, 7f, -7f)
            reflectiveCurveToRelative(7f, 3.13f, 7f, 7f)
            reflectiveCurveToRelative(-3.13f, 7f, -7f, 7f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _myLocation = it }

private var _newReleases: ImageVector? = null

val Icons.Filled.NewReleases: ImageVector
    get() = _newReleases ?: ImageVector.Builder(
        name = "Filled.NewReleases",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(23f, 12f)
            lineToRelative(-2.44f, -2.78f)
            lineToRelative(0.34f, -3.68f)
            lineToRelative(-3.61f, -0.82f)
            lineToRelative(-1.89f, -3.18f)
            lineTo(12f, 3f)
            lineTo(8.6f, 1.54f)
            lineTo(6.71f, 4.72f)
            lineToRelative(-3.61f, 0.81f)
            lineToRelative(0.34f, 3.68f)
            lineTo(1f, 12f)
            lineToRelative(2.44f, 2.78f)
            lineToRelative(-0.34f, 3.69f)
            lineToRelative(3.61f, 0.82f)
            lineToRelative(1.89f, 3.18f)
            lineTo(12f, 21f)
            lineToRelative(3.4f, 1.46f)
            lineToRelative(1.89f, -3.18f)
            lineToRelative(3.61f, -0.82f)
            lineToRelative(-0.34f, -3.68f)
            lineTo(23f, 12f)
            close()
            moveTo(13f, 17f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(2f)
            close()
            moveTo(13f, 13f)
            horizontalLineToRelative(-2f)
            lineTo(11f, 7f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(6f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _newReleases = it }

private var _palette: ImageVector? = null

val Icons.Filled.Palette: ImageVector
    get() = _palette ?: ImageVector.Builder(
        name = "Filled.Palette",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.49f, 2f, 2f, 6.49f, 2f, 12f)
            reflectiveCurveToRelative(4.49f, 10f, 10f, 10f)
            curveToRelative(1.38f, 0f, 2.5f, -1.12f, 2.5f, -2.5f)
            curveToRelative(0f, -0.61f, -0.23f, -1.2f, -0.64f, -1.67f)
            curveToRelative(-0.08f, -0.1f, -0.13f, -0.21f, -0.13f, -0.33f)
            curveToRelative(0f, -0.28f, 0.22f, -0.5f, 0.5f, -0.5f)
            horizontalLineTo(16f)
            curveToRelative(3.31f, 0f, 6f, -2.69f, 6f, -6f)
            curveTo(22f, 6.04f, 17.51f, 2f, 12f, 2f)
            close()
            moveTo(17.5f, 13f)
            curveToRelative(-0.83f, 0f, -1.5f, -0.67f, -1.5f, -1.5f)
            curveToRelative(0f, -0.83f, 0.67f, -1.5f, 1.5f, -1.5f)
            reflectiveCurveToRelative(1.5f, 0.67f, 1.5f, 1.5f)
            curveTo(19f, 12.33f, 18.33f, 13f, 17.5f, 13f)
            close()
            moveTo(14.5f, 9f)
            curveTo(13.67f, 9f, 13f, 8.33f, 13f, 7.5f)
            curveTo(13f, 6.67f, 13.67f, 6f, 14.5f, 6f)
            reflectiveCurveTo(16f, 6.67f, 16f, 7.5f)
            curveTo(16f, 8.33f, 15.33f, 9f, 14.5f, 9f)
            close()
            moveTo(5f, 11.5f)
            curveTo(5f, 10.67f, 5.67f, 10f, 6.5f, 10f)
            reflectiveCurveTo(8f, 10.67f, 8f, 11.5f)
            curveTo(8f, 12.33f, 7.33f, 13f, 6.5f, 13f)
            reflectiveCurveTo(5f, 12.33f, 5f, 11.5f)
            close()
            moveTo(11f, 7.5f)
            curveTo(11f, 8.33f, 10.33f, 9f, 9.5f, 9f)
            reflectiveCurveTo(8f, 8.33f, 8f, 7.5f)
            curveTo(8f, 6.67f, 8.67f, 6f, 9.5f, 6f)
            reflectiveCurveTo(11f, 6.67f, 11f, 7.5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _palette = it }

private var _person: ImageVector? = null

val Icons.Filled.Person: ImageVector
    get() = _person ?: ImageVector.Builder(
        name = "Filled.Person",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 12f)
            curveToRelative(2.21f, 0f, 4f, -1.79f, 4f, -4f)
            reflectiveCurveToRelative(-1.79f, -4f, -4f, -4f)
            reflectiveCurveToRelative(-4f, 1.79f, -4f, 4f)
            reflectiveCurveToRelative(1.79f, 4f, 4f, 4f)
            close()
            moveTo(12f, 14f)
            curveToRelative(-2.67f, 0f, -8f, 1.34f, -8f, 4f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(16f)
            verticalLineToRelative(-2f)
            curveToRelative(0f, -2.66f, -5.33f, -4f, -8f, -4f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _person = it }

private var _powerSettingsNew: ImageVector? = null

val Icons.Filled.PowerSettingsNew: ImageVector
    get() = _powerSettingsNew ?: ImageVector.Builder(
        name = "Filled.PowerSettingsNew",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(13f, 3f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(10f)
            horizontalLineToRelative(2f)
            lineTo(13f, 3f)
            close()
            moveTo(17.83f, 5.17f)
            lineToRelative(-1.42f, 1.42f)
            curveTo(17.99f, 7.86f, 19f, 9.81f, 19f, 12f)
            curveToRelative(0f, 3.87f, -3.13f, 7f, -7f, 7f)
            reflectiveCurveToRelative(-7f, -3.13f, -7f, -7f)
            curveToRelative(0f, -2.19f, 1.01f, -4.14f, 2.58f, -5.42f)
            lineTo(6.17f, 5.17f)
            curveTo(4.23f, 6.82f, 3f, 9.26f, 3f, 12f)
            curveToRelative(0f, 4.97f, 4.03f, 9f, 9f, 9f)
            reflectiveCurveToRelative(9f, -4.03f, 9f, -9f)
            curveToRelative(0f, -2.74f, -1.23f, -5.18f, -3.17f, -6.83f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _powerSettingsNew = it }

private var _radioButtonUnchecked: ImageVector? = null

val Icons.Filled.RadioButtonUnchecked: ImageVector
    get() = _radioButtonUnchecked ?: ImageVector.Builder(
        name = "Filled.RadioButtonUnchecked",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
            reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
            reflectiveCurveTo(17.52f, 2f, 12f, 2f)
            close()
            moveTo(12f, 20f)
            curveToRelative(-4.42f, 0f, -8f, -3.58f, -8f, -8f)
            reflectiveCurveToRelative(3.58f, -8f, 8f, -8f)
            reflectiveCurveToRelative(8f, 3.58f, 8f, 8f)
            reflectiveCurveToRelative(-3.58f, 8f, -8f, 8f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _radioButtonUnchecked = it }

private var _refresh: ImageVector? = null

val Icons.Filled.Refresh: ImageVector
    get() = _refresh ?: ImageVector.Builder(
        name = "Filled.Refresh",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(17.65f, 6.35f)
            curveTo(16.2f, 4.9f, 14.21f, 4f, 12f, 4f)
            curveToRelative(-4.42f, 0f, -7.99f, 3.58f, -7.99f, 8f)
            reflectiveCurveToRelative(3.57f, 8f, 7.99f, 8f)
            curveToRelative(3.73f, 0f, 6.84f, -2.55f, 7.73f, -6f)
            horizontalLineToRelative(-2.08f)
            curveToRelative(-0.82f, 2.33f, -3.04f, 4f, -5.65f, 4f)
            curveToRelative(-3.31f, 0f, -6f, -2.69f, -6f, -6f)
            reflectiveCurveToRelative(2.69f, -6f, 6f, -6f)
            curveToRelative(1.66f, 0f, 3.14f, 0.69f, 4.22f, 1.78f)
            lineTo(13f, 11f)
            horizontalLineToRelative(7f)
            verticalLineTo(4f)
            lineToRelative(-2.35f, 2.35f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _refresh = it }

private var _remove: ImageVector? = null

val Icons.Filled.Remove: ImageVector
    get() = _remove ?: ImageVector.Builder(
        name = "Filled.Remove",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 13f)
            horizontalLineTo(5f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _remove = it }

private var _restartAlt: ImageVector? = null

val Icons.Filled.RestartAlt: ImageVector
    get() = _restartAlt ?: ImageVector.Builder(
        name = "Filled.RestartAlt",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 5f)
            verticalLineTo(2f)
            lineTo(8f, 6f)
            lineToRelative(4f, 4f)
            verticalLineTo(7f)
            curveToRelative(3.31f, 0f, 6f, 2.69f, 6f, 6f)
            curveToRelative(0f, 2.97f, -2.17f, 5.43f, -5f, 5.91f)
            verticalLineToRelative(2.02f)
            curveToRelative(3.95f, -0.49f, 7f, -3.85f, 7f, -7.93f)
            curveTo(20f, 8.58f, 16.42f, 5f, 12f, 5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(6f, 13f)
            curveToRelative(0f, -1.65f, 0.67f, -3.15f, 1.76f, -4.24f)
            lineTo(6.34f, 7.34f)
            curveTo(4.9f, 8.79f, 4f, 10.79f, 4f, 13f)
            curveToRelative(0f, 4.08f, 3.05f, 7.44f, 7f, 7.93f)
            verticalLineToRelative(-2.02f)
            curveTo(8.17f, 18.43f, 6f, 15.97f, 6f, 13f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _restartAlt = it }

private var _restoreFromTrash: ImageVector? = null

val Icons.Filled.RestoreFromTrash: ImageVector
    get() = _restoreFromTrash ?: ImageVector.Builder(
        name = "Filled.RestoreFromTrash",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 4f)
            horizontalLineToRelative(-3.5f)
            lineToRelative(-1f, -1f)
            horizontalLineToRelative(-5f)
            lineToRelative(-1f, 1f)
            lineTo(5f, 4f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(14f)
            close()
            moveTo(6f, 7f)
            verticalLineToRelative(12f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(8f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(18f, 7f)
            lineTo(6f, 7f)
            close()
            moveTo(14f, 14f)
            verticalLineToRelative(4f)
            horizontalLineToRelative(-4f)
            verticalLineToRelative(-4f)
            lineTo(8f, 14f)
            lineToRelative(4f, -4f)
            lineToRelative(4f, 4f)
            horizontalLineToRelative(-2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _restoreFromTrash = it }

private var _save: ImageVector? = null

val Icons.Filled.Save: ImageVector
    get() = _save ?: ImageVector.Builder(
        name = "Filled.Save",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(17f, 3f)
            lineTo(5f, 3f)
            curveToRelative(-1.11f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(14f)
            curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
            horizontalLineToRelative(14f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(21f, 7f)
            lineToRelative(-4f, -4f)
            close()
            moveTo(12f, 19f)
            curveToRelative(-1.66f, 0f, -3f, -1.34f, -3f, -3f)
            reflectiveCurveToRelative(1.34f, -3f, 3f, -3f)
            reflectiveCurveToRelative(3f, 1.34f, 3f, 3f)
            reflectiveCurveToRelative(-1.34f, 3f, -3f, 3f)
            close()
            moveTo(15f, 9f)
            lineTo(5f, 9f)
            lineTo(5f, 5f)
            horizontalLineToRelative(10f)
            verticalLineToRelative(4f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _save = it }

private var _saveAlt: ImageVector? = null

val Icons.Filled.SaveAlt: ImageVector
    get() = _saveAlt ?: ImageVector.Builder(
        name = "Filled.SaveAlt",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(19f, 12f)
            verticalLineToRelative(7f)
            lineTo(5f, 19f)
            verticalLineToRelative(-7f)
            lineTo(3f, 12f)
            verticalLineToRelative(7f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(14f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineToRelative(-7f)
            horizontalLineToRelative(-2f)
            close()
            moveTo(13f, 12.67f)
            lineToRelative(2.59f, -2.58f)
            lineTo(17f, 11.5f)
            lineToRelative(-5f, 5f)
            lineToRelative(-5f, -5f)
            lineToRelative(1.41f, -1.41f)
            lineTo(11f, 12.67f)
            lineTo(11f, 3f)
            horizontalLineToRelative(2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _saveAlt = it }

private var _search: ImageVector? = null

val Icons.Filled.Search: ImageVector
    get() = _search ?: ImageVector.Builder(
        name = "Filled.Search",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(15.5f, 14f)
            horizontalLineToRelative(-0.79f)
            lineToRelative(-0.28f, -0.27f)
            curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
            curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
            reflectiveCurveTo(3f, 5.91f, 3f, 9.5f)
            reflectiveCurveTo(5.91f, 16f, 9.5f, 16f)
            curveToRelative(1.61f, 0f, 3.09f, -0.59f, 4.23f, -1.57f)
            lineToRelative(0.27f, 0.28f)
            verticalLineToRelative(0.79f)
            lineToRelative(5f, 4.99f)
            lineTo(20.49f, 19f)
            lineToRelative(-4.99f, -5f)
            close()
            moveTo(9.5f, 14f)
            curveTo(7.01f, 14f, 5f, 11.99f, 5f, 9.5f)
            reflectiveCurveTo(7.01f, 5f, 9.5f, 5f)
            reflectiveCurveTo(14f, 7.01f, 14f, 9.5f)
            reflectiveCurveTo(11.99f, 14f, 9.5f, 14f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _search = it }

private var _selectAll: ImageVector? = null

val Icons.Filled.SelectAll: ImageVector
    get() = _selectAll ?: ImageVector.Builder(
        name = "Filled.SelectAll",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(3f, 5f)
            horizontalLineToRelative(2f)
            lineTo(5f, 3f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            close()
            moveTo(3f, 13f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            lineTo(3f, 11f)
            verticalLineToRelative(2f)
            close()
            moveTo(7f, 21f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            lineTo(7f, 19f)
            verticalLineToRelative(2f)
            close()
            moveTo(3f, 9f)
            horizontalLineToRelative(2f)
            lineTo(5f, 7f)
            lineTo(3f, 7f)
            verticalLineToRelative(2f)
            close()
            moveTo(13f, 3f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(2f)
            lineTo(13f, 3f)
            close()
            moveTo(19f, 3f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(2f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(5f, 21f)
            verticalLineToRelative(-2f)
            lineTo(3f, 19f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            close()
            moveTo(3f, 17f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            lineTo(3f, 15f)
            verticalLineToRelative(2f)
            close()
            moveTo(9f, 3f)
            lineTo(7f, 3f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(2f)
            lineTo(9f, 3f)
            close()
            moveTo(11f, 21f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(19f, 13f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(19f, 21f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(19f, 9f)
            horizontalLineToRelative(2f)
            lineTo(21f, 7f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(19f, 17f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(15f, 21f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(15f, 5f)
            horizontalLineToRelative(2f)
            lineTo(17f, 3f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            close()
            moveTo(7f, 17f)
            horizontalLineToRelative(10f)
            lineTo(17f, 7f)
            lineTo(7f, 7f)
            verticalLineToRelative(10f)
            close()
            moveTo(9f, 9f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(6f)
            lineTo(9f, 15f)
            lineTo(9f, 9f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _selectAll = it }

private var _share: ImageVector? = null

val Icons.Filled.Share: ImageVector
    get() = _share ?: ImageVector.Builder(
        name = "Filled.Share",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(18f, 16.08f)
            curveToRelative(-0.76f, 0f, -1.44f, 0.3f, -1.96f, 0.77f)
            lineTo(8.91f, 12.7f)
            curveToRelative(0.05f, -0.23f, 0.09f, -0.46f, 0.09f, -0.7f)
            reflectiveCurveToRelative(-0.04f, -0.47f, -0.09f, -0.7f)
            lineToRelative(7.05f, -4.11f)
            curveToRelative(0.54f, 0.5f, 1.25f, 0.81f, 2.04f, 0.81f)
            curveToRelative(1.66f, 0f, 3f, -1.34f, 3f, -3f)
            reflectiveCurveToRelative(-1.34f, -3f, -3f, -3f)
            reflectiveCurveToRelative(-3f, 1.34f, -3f, 3f)
            curveToRelative(0f, 0.24f, 0.04f, 0.47f, 0.09f, 0.7f)
            lineTo(8.04f, 9.81f)
            curveTo(7.5f, 9.31f, 6.79f, 9f, 6f, 9f)
            curveToRelative(-1.66f, 0f, -3f, 1.34f, -3f, 3f)
            reflectiveCurveToRelative(1.34f, 3f, 3f, 3f)
            curveToRelative(0.79f, 0f, 1.5f, -0.31f, 2.04f, -0.81f)
            lineToRelative(7.12f, 4.16f)
            curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
            curveToRelative(0f, 1.61f, 1.31f, 2.92f, 2.92f, 2.92f)
            curveToRelative(1.61f, 0f, 2.92f, -1.31f, 2.92f, -2.92f)
            reflectiveCurveToRelative(-1.31f, -2.92f, -2.92f, -2.92f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _share = it }

private var _star: ImageVector? = null

val Icons.Filled.Star: ImageVector
    get() = _star ?: ImageVector.Builder(
        name = "Filled.Star",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineToRelative(-1.64f, -7.03f)
            lineTo(22f, 9.24f)
            lineToRelative(-7.19f, -0.61f)
            lineTo(12f, 2f)
            lineTo(9.19f, 8.63f)
            lineTo(2f, 9.24f)
            lineToRelative(5.46f, 4.73f)
            lineTo(5.82f, 21f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _star = it }

private var _starBorder: ImageVector? = null

val Icons.Filled.StarBorder: ImageVector
    get() = _starBorder ?: ImageVector.Builder(
        name = "Filled.StarBorder",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(22f, 9.24f)
            lineToRelative(-7.19f, -0.62f)
            lineTo(12f, 2f)
            lineTo(9.19f, 8.63f)
            lineTo(2f, 9.24f)
            lineToRelative(5.46f, 4.73f)
            lineTo(5.82f, 21f)
            lineTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineToRelative(-1.63f, -7.03f)
            lineTo(22f, 9.24f)
            close()
            moveTo(12f, 15.4f)
            lineToRelative(-3.76f, 2.27f)
            lineToRelative(1f, -4.28f)
            lineToRelative(-3.32f, -2.88f)
            lineToRelative(4.38f, -0.38f)
            lineTo(12f, 6.1f)
            lineToRelative(1.71f, 4.04f)
            lineToRelative(4.38f, 0.38f)
            lineToRelative(-3.32f, 2.88f)
            lineToRelative(1f, 4.28f)
            lineTo(12f, 15.4f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _starBorder = it }

private var _storage: ImageVector? = null

val Icons.Filled.Storage: ImageVector
    get() = _storage ?: ImageVector.Builder(
        name = "Filled.Storage",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(2f, 20f)
            horizontalLineToRelative(20f)
            verticalLineToRelative(-4f)
            lineTo(2f, 16f)
            verticalLineToRelative(4f)
            close()
            moveTo(4f, 17f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(2f)
            lineTo(4f, 19f)
            verticalLineToRelative(-2f)
            close()
            moveTo(2f, 4f)
            verticalLineToRelative(4f)
            horizontalLineToRelative(20f)
            lineTo(22f, 4f)
            lineTo(2f, 4f)
            close()
            moveTo(6f, 7f)
            lineTo(4f, 7f)
            lineTo(4f, 5f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(2f)
            close()
            moveTo(2f, 14f)
            horizontalLineToRelative(20f)
            verticalLineToRelative(-4f)
            lineTo(2f, 10f)
            verticalLineToRelative(4f)
            close()
            moveTo(4f, 11f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(2f)
            lineTo(4f, 13f)
            verticalLineToRelative(-2f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _storage = it }

private var _swapVert: ImageVector? = null

val Icons.Filled.SwapVert: ImageVector
    get() = _swapVert ?: ImageVector.Builder(
        name = "Filled.SwapVert",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(16f, 17.01f)
            verticalLineTo(10f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(7.01f)
            horizontalLineToRelative(-3f)
            lineTo(15f, 21f)
            lineToRelative(4f, -3.99f)
            horizontalLineToRelative(-3f)
            close()
            moveTo(9f, 3f)
            lineTo(5f, 6.99f)
            horizontalLineToRelative(3f)
            verticalLineTo(14f)
            horizontalLineToRelative(2f)
            verticalLineTo(6.99f)
            horizontalLineToRelative(3f)
            lineTo(9f, 3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _swapVert = it }

private var _sync: ImageVector? = null

val Icons.Filled.Sync: ImageVector
    get() = _sync ?: ImageVector.Builder(
        name = "Filled.Sync",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 4f)
            lineTo(12f, 1f)
            lineTo(8f, 5f)
            lineToRelative(4f, 4f)
            lineTo(12f, 6f)
            curveToRelative(3.31f, 0f, 6f, 2.69f, 6f, 6f)
            curveToRelative(0f, 1.01f, -0.25f, 1.97f, -0.7f, 2.8f)
            lineToRelative(1.46f, 1.46f)
            curveTo(19.54f, 15.03f, 20f, 13.57f, 20f, 12f)
            curveToRelative(0f, -4.42f, -3.58f, -8f, -8f, -8f)
            close()
            moveTo(12f, 18f)
            curveToRelative(-3.31f, 0f, -6f, -2.69f, -6f, -6f)
            curveToRelative(0f, -1.01f, 0.25f, -1.97f, 0.7f, -2.8f)
            lineTo(5.24f, 7.74f)
            curveTo(4.46f, 8.97f, 4f, 10.43f, 4f, 12f)
            curveToRelative(0f, 4.42f, 3.58f, 8f, 8f, 8f)
            verticalLineToRelative(3f)
            lineToRelative(4f, -4f)
            lineToRelative(-4f, -4f)
            verticalLineToRelative(3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _sync = it }

private var _update: ImageVector? = null

val Icons.Filled.Update: ImageVector
    get() = _update ?: ImageVector.Builder(
        name = "Filled.Update",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(21f, 10.12f)
            horizontalLineToRelative(-6.78f)
            lineToRelative(2.74f, -2.82f)
            curveToRelative(-2.73f, -2.7f, -7.15f, -2.8f, -9.88f, -0.1f)
            curveToRelative(-2.73f, 2.71f, -2.73f, 7.08f, 0f, 9.79f)
            reflectiveCurveToRelative(7.15f, 2.71f, 9.88f, 0f)
            curveTo(18.32f, 15.65f, 19f, 14.08f, 19f, 12.1f)
            horizontalLineToRelative(2f)
            curveToRelative(0f, 1.98f, -0.88f, 4.55f, -2.64f, 6.29f)
            curveToRelative(-3.51f, 3.48f, -9.21f, 3.48f, -12.72f, 0f)
            curveToRelative(-3.5f, -3.47f, -3.53f, -9.11f, -0.02f, -12.58f)
            reflectiveCurveToRelative(9.14f, -3.47f, 12.65f, 0f)
            lineTo(21f, 3f)
            verticalLineTo(10.12f)
            close()
            moveTo(12.5f, 8f)
            verticalLineToRelative(4.25f)
            lineToRelative(3.5f, 2.08f)
            lineToRelative(-0.72f, 1.21f)
            lineTo(11f, 13f)
            verticalLineTo(8f)
            horizontalLineTo(12.5f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _update = it }

private var _upload: ImageVector? = null

val Icons.Filled.Upload: ImageVector
    get() = _upload ?: ImageVector.Builder(
        name = "Filled.Upload",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(5f, 20f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(-2f)
            horizontalLineTo(5f)
            verticalLineTo(20f)
            close()
            moveTo(5f, 10f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(6f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(-6f)
            horizontalLineToRelative(4f)
            lineToRelative(-7f, -7f)
            lineTo(5f, 10f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _upload = it }

private var _visibility: ImageVector? = null

val Icons.Filled.Visibility: ImageVector
    get() = _visibility ?: ImageVector.Builder(
        name = "Filled.Visibility",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 4.5f)
            curveTo(7f, 4.5f, 2.73f, 7.61f, 1f, 12f)
            curveToRelative(1.73f, 4.39f, 6f, 7.5f, 11f, 7.5f)
            reflectiveCurveToRelative(9.27f, -3.11f, 11f, -7.5f)
            curveToRelative(-1.73f, -4.39f, -6f, -7.5f, -11f, -7.5f)
            close()
            moveTo(12f, 17f)
            curveToRelative(-2.76f, 0f, -5f, -2.24f, -5f, -5f)
            reflectiveCurveToRelative(2.24f, -5f, 5f, -5f)
            reflectiveCurveToRelative(5f, 2.24f, 5f, 5f)
            reflectiveCurveToRelative(-2.24f, 5f, -5f, 5f)
            close()
            moveTo(12f, 9f)
            curveToRelative(-1.66f, 0f, -3f, 1.34f, -3f, 3f)
            reflectiveCurveToRelative(1.34f, 3f, 3f, 3f)
            reflectiveCurveToRelative(3f, -1.34f, 3f, -3f)
            reflectiveCurveToRelative(-1.34f, -3f, -3f, -3f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _visibility = it }

private var _visibilityOff: ImageVector? = null

val Icons.Filled.VisibilityOff: ImageVector
    get() = _visibilityOff ?: ImageVector.Builder(
        name = "Filled.VisibilityOff",
        defaultWidth = Dp(24f),
        defaultHeight = Dp(24f),
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = false,
    )
    .addPath(
        pathData = PathBuilder().apply {
            moveTo(12f, 7f)
            curveToRelative(2.76f, 0f, 5f, 2.24f, 5f, 5f)
            curveToRelative(0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
            lineToRelative(2.92f, 2.92f)
            curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
            curveToRelative(-1.73f, -4.39f, -6f, -7.5f, -11f, -7.5f)
            curveToRelative(-1.4f, 0f, -2.74f, 0.25f, -3.98f, 0.7f)
            lineToRelative(2.16f, 2.16f)
            curveTo(10.74f, 7.13f, 11.35f, 7f, 12f, 7f)
            close()
            moveTo(2f, 4.27f)
            lineToRelative(2.28f, 2.28f)
            lineToRelative(0.46f, 0.46f)
            curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1f, 12f)
            curveToRelative(1.73f, 4.39f, 6f, 7.5f, 11f, 7.5f)
            curveToRelative(1.55f, 0f, 3.03f, -0.3f, 4.38f, -0.84f)
            lineToRelative(0.42f, 0.42f)
            lineTo(19.73f, 22f)
            lineTo(21f, 20.73f)
            lineTo(3.27f, 3f)
            lineTo(2f, 4.27f)
            close()
            moveTo(7.53f, 9.8f)
            lineToRelative(1.55f, 1.55f)
            curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
            curveToRelative(0f, 1.66f, 1.34f, 3f, 3f, 3f)
            curveToRelative(0.22f, 0f, 0.44f, -0.03f, 0.65f, -0.08f)
            lineToRelative(1.55f, 1.55f)
            curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
            curveToRelative(-2.76f, 0f, -5f, -2.24f, -5f, -5f)
            curveToRelative(0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
            close()
            moveTo(11.84f, 9.02f)
            lineToRelative(3.15f, 3.15f)
            lineToRelative(0.02f, -0.16f)
            curveToRelative(0f, -1.66f, -1.34f, -3f, -3f, -3f)
            lineToRelative(-0.17f, 0.01f)
            close()
        }.nodes,
        fill = SolidColor(Color.Black),
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1f,
    )
    .build().also { _visibilityOff = it }
