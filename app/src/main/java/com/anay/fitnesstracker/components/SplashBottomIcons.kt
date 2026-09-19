package com.anay.fitnesstracker.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anay.fitnesstracker.R

@Composable
fun SplashBottomIcons(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top row: Dumbbell (-28°), Apple (center), Bicep right
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sneakers),
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .rotate(-28f)
                    .offset(y = (-5).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bench),
                contentDescription = null,
                modifier = Modifier
                    .size(46.dp)
                    .offset(y = (-4).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_rope),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .offset(y = (-4).dp)
            )
        }


    }
}