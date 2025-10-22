package com.hfad.rickandmorty.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hfad.rickandmorty.data.model.Results
import com.hfad.rickandmorty.ui.theme.aliveColor
import com.hfad.rickandmorty.ui.theme.backgroundCard
import com.hfad.rickandmorty.ui.theme.blueGradient
import com.hfad.rickandmorty.ui.theme.deadColor
import com.hfad.rickandmorty.ui.theme.pinkGradient
import com.hfad.rickandmorty.ui.theme.purpleGradient
import com.hfad.rickandmorty.ui.theme.unknownColor

@Composable
fun HeroCard(hero: Results) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundCard
        )
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Transparent,
                            blueGradient,
                            purpleGradient,
                            pinkGradient,
                            Color.Transparent
                        ),
                        center = Offset.Unspecified
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = if (hero.status == "unknown") unknownColor else if (hero.status == "Alive") aliveColor else deadColor,
                                shape = CircleShape
                            )
                    )
                    Text(
                        text = hero.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (hero.status == "unknown") unknownColor else if (hero.status == "Alive") aliveColor else deadColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(hero.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = hero.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                               width = 1.dp,
                                color = Color.Blue.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ){
                        Text(
                            text = "unknown",
                            style = MaterialTheme.typography.bodyMedium,
                            color = blueGradient
                        )

                    }
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Blue.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ){
                        Text(
                            text = "unknown",
                            style = MaterialTheme.typography.bodyMedium,
                            color = blueGradient
                        )

                    }
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Blue.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ){
                        Text(
                            text = "unknown",
                            style = MaterialTheme.typography.bodyMedium,
                            color = blueGradient
                        )

                    }


                }
            }
        }
    }
}
