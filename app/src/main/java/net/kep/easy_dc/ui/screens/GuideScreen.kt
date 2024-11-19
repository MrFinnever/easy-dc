package net.kep.easy_dc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import net.kep.easy_dc.R

@Composable
fun GuideScreen() {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(R.raw.theory),
        isZoomEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
