package com.example.marketing.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.marketing.domain.DugKeywordCandidate
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import androidx.compose.ui.input.pointer.util.VelocityTracker
import kotlin.math.sqrt


@Composable
fun FloatingKeywordCloud(
    candidatesInfo: List<DugKeywordCandidate>,
    modifier: Modifier = Modifier,
    baseColor: Color = Color(0xFF0F4C81), // classic blue
    onItemClick: (DugKeywordCandidate) -> Unit
) {
    /* ---------- 1.  Set‑up ---------- */

    val scope         = rememberCoroutineScope()
    val density       = LocalDensity.current
    val radiusPx      = with(density) { 80.dp.toPx() }   // distance from centre
    val rotation      = remember { Animatable(0f) }      // radians
    val velocityTrack = remember { VelocityTracker() }   // tracks finger speed

    /* ---------- 2.  Gesture detector ---------- */
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { velocityTrack.resetTracking() },
            onDrag = { change, drag ->
                change.consume()                                   // prevent scroll‑parent
                velocityTrack.addPosition(change.uptimeMillis,
                    change.position)        // feed tracker

                val deltaTheta = drag.x / radiusPx                 // px → radians
                scope.launch { rotation.snapTo(rotation.value + deltaTheta) }
            },
            onDragEnd = {
                val vxPxPerSec       = velocityTrack.calculateVelocity().x
                val angularVelocity  = vxPxPerSec / radiusPx       // px/s → rad/s

                scope.launch {
                    rotation.animateDecay(
                        initialVelocity = angularVelocity,
                        animationSpec   = exponentialDecay()
                    )
                }
            }
        )
    }

    /* ---------- 3.  UI ---------- */

    Box(
        modifier
            .fillMaxSize()
            .then(gestureModifier)          // attach gestures
    ) {
        candidatesInfo.forEachIndexed { idx, candidate ->
            // ① Golden‑angle in radians  (≈ 137.5°)
            val goldenAngle = PI * (3.0 - sqrt(5.0))          // Double
            val baseAngle   = idx * goldenAngle               // Double
            val finalAngle  = baseAngle.toFloat() + rotation.value

            // ② Spiral radius grows a little each step
            //    tweak 8.dp to make the spiral tighter or looser
            val r = radiusPx + with(density) { (8.dp * idx).toPx() }

            // ③ Convert polar → Cartesian
            val x = cos(finalAngle) * r
            val y = sin(finalAngle) * r

            Text(
                text   = candidate.keyword,
                color  = baseColor.copy(alpha = 0.9f),
                style  = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                    .clickable { onItemClick(candidate) }
            )
        }
    }
}