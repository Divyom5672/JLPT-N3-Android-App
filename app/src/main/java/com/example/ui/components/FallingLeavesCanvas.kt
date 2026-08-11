package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.sin
import kotlin.random.Random

private class LeafData(
    var xRatio: Float,
    var yRatio: Float,
    val sizePx: Float,
    val speed: Float,
    val swayAmplitude: Float,
    val swayFrequency: Float,
    val rotationSpeed: Float,
    var currentRotation: Float,
    val color: Color,
    val alpha: Float,
    var swayTime: Float
)

@Composable
fun FallingLeavesCanvas(
    modifier: Modifier = Modifier,
    leafCount: Int = 22
) {
    val leafColors = remember {
        listOf(
            Color(0xFFE65100), // Terracotta Orange
            Color(0xFFFFB300), // Gold
            Color(0xFFBF360C), // Deep Crimson
            Color(0xFFF57C00), // Amber
            Color(0xFFD84315)  // Rust Red
        )
    }

    val leaves = remember {
        List(leafCount) {
            LeafData(
                xRatio = Random.nextFloat(),
                yRatio = Random.nextFloat() * 1.2f - 0.2f,
                sizePx = Random.nextFloat() * 16f + 12f,
                speed = Random.nextFloat() * 0.12f + 0.08f,
                swayAmplitude = Random.nextFloat() * 0.04f + 0.02f,
                swayFrequency = Random.nextFloat() * 2f + 1f,
                rotationSpeed = Random.nextFloat() * 40f - 20f,
                currentRotation = Random.nextFloat() * 360f,
                color = leafColors.random(),
                alpha = Random.nextFloat() * 0.4f + 0.5f,
                swayTime = Random.nextFloat() * 10f
            )
        }
    }

    var frameTimeNanos by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        var previousTimeNanos = 0L
        while (true) {
            withFrameNanos { nanos ->
                if (previousTimeNanos != 0L) {
                    val deltaSeconds = (nanos - previousTimeNanos) / 1_000_000_000f
                    leaves.forEach { leaf ->
                        leaf.swayTime += deltaSeconds
                        leaf.yRatio += leaf.speed * deltaSeconds
                        leaf.xRatio += sin(leaf.swayTime * leaf.swayFrequency) * leaf.swayAmplitude * deltaSeconds
                        leaf.currentRotation += leaf.rotationSpeed * deltaSeconds

                        if (leaf.yRatio > 1.15f) {
                            leaf.yRatio = -0.15f
                            leaf.xRatio = Random.nextFloat()
                        }
                    }
                }
                previousTimeNanos = nanos
                frameTimeNanos = nanos
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        @Suppress("UNUSED_VARIABLE")
        val drawTick = frameTimeNanos

        val width = size.width
        val height = size.height

        leaves.forEach { leaf ->
            val x = leaf.xRatio * width
            val y = leaf.yRatio * height

            rotate(degrees = leaf.currentRotation, pivot = Offset(x, y)) {
                drawMomijiLeaf(
                    center = Offset(x, y),
                    size = leaf.sizePx,
                    color = leaf.color.copy(alpha = leaf.alpha)
                )
            }
        }
    }
}

private fun DrawScope.drawMomijiLeaf(
    center: Offset,
    size: Float,
    color: Color
) {
    val path = Path().apply {
        val cx = center.x
        val cy = center.y
        val r = size

        moveTo(cx, cy - r)
        quadraticTo(cx + r * 0.3f, cy - r * 0.6f, cx + r * 0.8f, cy - r * 0.5f)
        quadraticTo(cx + r * 0.4f, cy - r * 0.1f, cx + r, cy)
        quadraticTo(cx + r * 0.4f, cy + r * 0.3f, cx + r * 0.6f, cy + r * 0.7f)
        quadraticTo(cx + r * 0.1f, cy + r * 0.4f, cx, cy + r * 0.9f)
        quadraticTo(cx - r * 0.1f, cy + r * 0.4f, cx - r * 0.6f, cy + r * 0.7f)
        quadraticTo(cx - r * 0.4f, cy + r * 0.3f, cx - r, cy)
        quadraticTo(cx - r * 0.4f, cy - r * 0.1f, cx - r * 0.8f, cy - r * 0.5f)
        quadraticTo(cx - r * 0.3f, cy - r * 0.6f, cx, cy - r)
        close()
    }
    drawPath(path = path, color = color)
}
