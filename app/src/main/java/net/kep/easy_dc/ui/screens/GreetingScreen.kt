package net.kep.easy_dc.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.R
import net.kep.easy_dc.ui.navigation.NavRoutes
import net.kep.easy_dc.ui.theme.LocalColors
import net.kep.easy_dc.ui.theme.LocalTextStyles

@Composable
fun GreetingScreen(
    mainNavCon: NavController,
    calcNavCon: NavController
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = LocalTextStyles.current.bigTitle.fontSize,
                color = LocalColors.current.onBackground,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp)
            )
            Button(
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalColors.current.primary,
                    contentColor = LocalColors.current.onPrimary
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .height(70.dp)
                    .width(210.dp),
                onClick = {
                    calcNavCon.navigate(route = NavRoutes.Calculator.route)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.calculation),
                    fontSize = LocalTextStyles.current.bigButtonsText.fontSize
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalColors.current.primary,
                    contentColor = LocalColors.current.onPrimary
                ),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .padding(10.dp)
                    .height(70.dp)
                    .width(210.dp),
                onClick = {
                    mainNavCon.navigate(route = NavRoutes.Guide.route)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.guide),
                    fontSize = LocalTextStyles.current.bigButtonsText.fontSize
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    locale = "ru"
)
@Composable
fun GreetingScreenPreviewRu() {
    val mainNav = rememberNavController()
    val calcNav = rememberNavController()
    GreetingScreen(
        mainNavCon = mainNav,
        calcNavCon = calcNav
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    val mainNav = rememberNavController()
    val calcNav = rememberNavController()
    GreetingScreen(
        mainNavCon = mainNav,
        calcNavCon = calcNav
    )
}