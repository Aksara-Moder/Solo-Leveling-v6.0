package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboard(viewModel: GameViewModel) {
    // For this demo, we'll use a fixed birth date or let the user pick one.
    // In a real app, this would be saved in the database.
    val birthDate = remember { 
        Calendar.getInstance().apply {
            set(1995, Calendar.JUNE, 15, 8, 30) // Example: June 15, 1995
        }.timeInMillis
    }
    
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(10) // Update frequently for the decimal age effect
        }
    }

    Scaffold(
        containerColor = Slate50,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "NEOSYSTEM // LIFE DASHBOARD",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Slate900,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Slate50
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AgeDecimalCard(birthDate, currentTime)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            BentoGrid(birthDate, currentTime)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            BirthLoreCard(birthDate)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                "SYSTEM VERSION 1.0.4 // STABLE",
                style = MaterialTheme.typography.labelSmall,
                color = Slate300,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun AgeDecimalCard(birthDate: Long, currentTime: Long) {
    val ageYears = (currentTime - birthDate).toDouble() / (1000.0 * 60 * 60 * 24 * 365.25)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "CHRONOLOGICAL AGE",
                style = MaterialTheme.typography.labelSmall,
                color = Slate700,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = String.format("%.9f", ageYears),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontFamily = FontFamily.Monospace
                ),
                color = RoyalBlue,
                fontWeight = FontWeight.Black
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                "YEARS ELAPSED SINCE ORIGIN",
                style = MaterialTheme.typography.labelSmall,
                color = Slate300,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun BentoGrid(birthDate: Long, currentTime: Long) {
    val diff = currentTime - birthDate
    
    val years = TimeUnit.MILLISECONDS.toDays(diff) / 365
    val months = (TimeUnit.MILLISECONDS.toDays(diff) % 365) / 30
    val days = (TimeUnit.MILLISECONDS.toDays(diff) % 365) % 30
    val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            MetricCard(Modifier.weight(1f), "YEARS", years.toString(), Icons.Default.CalendarToday)
            Spacer(modifier = Modifier.width(12.dp))
            MetricCard(Modifier.weight(1f), "MONTHS", months.toString(), Icons.Default.DateRange)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            MetricCard(Modifier.weight(1f), "DAYS", days.toString(), Icons.Default.WbSunny)
            Spacer(modifier = Modifier.width(12.dp))
            MetricCard(Modifier.weight(1f), "HOURS", hours.toString(), Icons.Default.AccessTime)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            MetricCard(Modifier.weight(1f), "MINUTES", minutes.toString(), Icons.Default.Timer)
            Spacer(modifier = Modifier.width(12.dp))
            MetricCard(Modifier.weight(1f), "SECONDS", seconds.toString(), Icons.Default.Update)
        }
    }
}

@Composable
fun MetricCard(modifier: Modifier, label: String, value: String, icon: ImageVector) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Slate200),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = RoyalBlue,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Slate900
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Slate300,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BirthLoreCard(birthDate: Long) {
    val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
    val dayOfWeek = sdf.format(Date(birthDate))
    
    val lore = when (dayOfWeek) {
        "Monday" -> "Fair of face, gifted with natural intuition and a calm presence."
        "Tuesday" -> "Full of grace, driven by courage and a pioneering spirit."
        "Wednesday" -> "Full of woe, but possess deep wisdom and a resilient heart."
        "Thursday" -> "Has far to go, destined for great journeys and broad horizons."
        "Friday" -> "Loving and giving, a beacon of compassion and artistic soul."
        "Saturday" -> "Works hard for a living, dedicated, disciplined, and steadfast."
        "Sunday" -> "Bonny and blithe, good and gay, radiating joy and natural leadership."
        else -> "A unique soul with a destiny yet to be fully revealed."
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = RoyalBlue),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "BIRTH WEEKDAY LORE",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Born on a $dayOfWeek",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = lore,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 22.sp
            )
        }
    }
}
