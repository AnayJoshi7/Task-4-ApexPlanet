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
fun SplashTopIcons(
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
                painter = painterResource(id = R.drawable.ic_dumbell),
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .rotate(-28f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_apple),
                contentDescription = null,
                modifier = Modifier
                    .size(46.dp)
                    .offset(y = (-4).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bicep1),
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )
        }

        // Bottom row: Supplement jar, Bicep center, Shaker bottle (-24°)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_protein),
                contentDescription = null,
                modifier = Modifier.size(38.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bicep2),
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp)
                    .offset(x = (-8).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_shaker),
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .rotate(-24f)
            )
        }
    }
}