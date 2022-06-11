package com.jetpack.mirroreffectwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jetpack.mirroreffectwithcompose.ui.theme.MirrorEffectWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrorEffectWithComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Mirror Effect With Compose",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            MirrorEffectWithCompose()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MirrorEffectWithCompose() {
    Mirror {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier =  Modifier
                .fillMaxWidth(0.8f)
                .height(300.dp)
                .clip(RoundedCornerShape(25.dp))
        )
    }
}

object HalfSizeShape: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Rectangle(
        Rect(Offset(0f, size.height / 2), Size(size.width, size.height))
    )
}

@Composable
fun Mirror(
    content: @Composable () -> Unit
) {
    Column {
        content()
        Box(
            modifier = Modifier
                .graphicsLayer {
                    alpha = 0.99f
                    rotationZ = 180f
                }
                .drawWithContent {
                    val colors = listOf(Color.Transparent, Color.White)
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.DstIn
                    )
                }
                .blur(
                    radiusX = 1.dp,
                    radiusY = 3.dp,
                    BlurredEdgeTreatment.Unbounded
                ) // it's only support versions is 1.1.0-alpha03
                .clip(
                    HalfSizeShape
                )
        ) {
            content()
        }
    }
}





















