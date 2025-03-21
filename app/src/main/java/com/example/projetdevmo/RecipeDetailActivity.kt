package com.example.projetdevmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate  // Ajout de l'import pour rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext  // Ajout de l'import pour LocalContext

class RecipeDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeId = intent.getStringExtra("RECIPE_ID") ?: ""
        val recipeTitle = intent.getStringExtra("RECIPE_TITLE") ?: "Recipe"
        val recipeLevel = intent.getStringExtra("RECIPE_LEVEL") ?: "beginner"
        val recipeTime = intent.getStringExtra("RECIPE_TIME") ?: "25"

        setContent {
            MaterialTheme {
                RecipeDetailScreen(
                    recipeTitle = recipeTitle,
                    recipeLevel = recipeLevel,
                    recipeTime = recipeTime,
                    onBackClick = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeTitle: String,
    recipeLevel: String,
    recipeTime: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipeTitle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp)
                    .border(1.dp, Color.Black)
                    .clip(RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Croix X
                Box {
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .rotate(45f)
                    )
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .rotate(-45f)
                    )
                }
            }

            // Ingredients section
            Text(
                text = "Ingredients :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // Example ingredient text with links
            IngredientItem(text = "A paragraph of", highlight = "text", isLink = true, linkType = LinkType.UNASSIGNED)
            IngredientItem(text = "A second row of", highlight = "text", isLink = true, linkType = LinkType.WEB)
            IngredientItem(text = "An icon", highlight = "inline", isLink = false, showIcon = true)

            // Difficulty and time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "difficulty : $recipeLevel",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipeTime,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Time",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Tools needed section
            Text(
                text = "Tools needed :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Example tools text with links
            IngredientItem(text = "A paragraph of", highlight = "text", isLink = true, linkType = LinkType.UNASSIGNED)
            IngredientItem(text = "A second row of", highlight = "text", isLink = true, linkType = LinkType.WEB)
            IngredientItem(text = "An icon", highlight = "inline", isLink = false, showIcon = true)

            // Start button
            Button(
                onClick = { /* Start the recipe */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "start the recipe",
                    fontSize = 16.sp
                )
            }
        }
    }
}

enum class LinkType {
    UNASSIGNED, WEB
}

@Composable
fun IngredientItem(
    text: String,
    highlight: String,
    isLink: Boolean,
    linkType: LinkType = LinkType.UNASSIGNED,
    showIcon: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            buildAnnotatedString {
                append("$text ")

                val style = if (isLink) {
                    SpanStyle(
                        color = if (linkType == LinkType.WEB) Color.Blue else Color.Red,
                        textDecoration = TextDecoration.Underline
                    )
                } else {
                    SpanStyle(color = Color.Black)
                }

                withStyle(style) {
                    append(highlight)
                }
            }
        )

        if (showIcon) {
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Blue, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "i",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}