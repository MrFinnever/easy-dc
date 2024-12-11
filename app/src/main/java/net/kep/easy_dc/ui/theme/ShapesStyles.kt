package net.kep.easy_dc.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import net.kep.easy_dc.data.theme.CustomShapes
import net.kep.easy_dc.data.theme.enums.Corners

internal fun getShapeStyles(cornerSize: Corners): CustomShapes {
    return when (cornerSize) {
        Corners.SmallRounded ->  CustomShapes(
            corner = RoundedCornerShape(size = 5.dp)
        )
        Corners.Rounded ->  CustomShapes(
            corner = RoundedCornerShape(size = 10.dp)
        )
        Corners.BigRounded ->  CustomShapes(
            corner = RoundedCornerShape(size = 15.dp)
        )
        Corners.SuperRounded ->  CustomShapes(
            corner = RoundedCornerShape(size = 20.dp)
        )
        Corners.Flat ->  CustomShapes(
            corner = RoundedCornerShape(size = 0.dp)
        )
    }
}