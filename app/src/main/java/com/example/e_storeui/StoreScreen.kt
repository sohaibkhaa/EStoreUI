package com.example.e_storeui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_storeui.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


private const val TAG = "StoreScreen"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StoreScreen() {
    val pagerState = rememberPagerState(0)
    Column(modifier = Modifier) {
        TopBar(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileSection(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Tabs(pagerState = pagerState)
        Products(
            products = Constants.PRODUCTS_LIST
        )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
    }
}

@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_asus), contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = LightGrey
                )
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Top seller", fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(
                    color = Color(
                        0xFFfff4dc
                    )
                )
                .padding(4.dp),
            color = Color(0xFFffa185)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Asus Official Store",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue)
            ) {
                Text(text = "Turn on", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Notifications, contentDescription = "Notification",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xfff1f2f3)),
            ) {
                Text(text = "Chat", color = DarkBlue)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Outlined.Email, contentDescription = "Message",
                    tint = DarkBlue
                )
            }

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Products" to Icons.Default.Home,
        "Newest" to Icons.Default.Home,
        "Popular" to Icons.Default.Home,
        "Category" to Icons.Default.Home,
    )
    val scope = rememberCoroutineScope()
    var selectedTabIndex by remember {
        mutableStateOf(pagerState.currentPage)
    }
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,
        contentColor = BluishGrey,
        indicator = { tabPosition ->
            Box(
                Modifier
                    .customTabIndicatorOffset(
                        tabPosition[selectedTabIndex],
                        6.dp
                    )
                    .height(6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(DarkBlue)
            )
        },
        modifier = Modifier.border(
            width = 1.dp,
            color = Color.White
        )
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        text = list[index].first,
                    )
                },
                selectedContentColor = DarkBlue,
                unselectedContentColor = BluishGrey,
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@Composable
fun Products(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products.size) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = LightGrey,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = products[it].images[0], contentDescription = "Product image",
                    modifier = Modifier
                        .height(140.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = products[it].name, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = products[it].brand, fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "$" + products[it].price, fontSize = 18.sp,
                            color = Color.Black, fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_forward),
                            tint = Color.Magenta,
                            contentDescription = "View Details",
                            modifier = Modifier
                                .width(42.dp)
                                .background(
                                    color = Color(0xFFf2f8fd),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        )
                    }

                }

            }
        }
    }
}