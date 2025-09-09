package com.lll.despachoaiep.presentation.pageHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lll.despachoaiep.R

import com.lll.despachoaiep.presentation.model.Artist
import com.lll.despachoaiep.presentation.model.Player
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.ui.theme.Purple40

@Preview
@Composable
//db: FirebaseFirestore
fun PageHomeScreen(viewModel: PageHomeViewModel = PageHomeViewModel()) {


    val artists: State<List<Artist>> = viewModel.artist.collectAsState()

    val player by viewModel.player.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 0.dp),
        horizontalAlignment = Alignment.Start
    ) {


        Text(
            "Popular artist",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 50.dp)
        )
        //val artists = emptyList<Artist>()

        LazyRow {
            items(artists.value) {
                ArtistItem(it)

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        player?.let {
            PlayerComponent(it)
        }


    }


}

@Composable
fun PlayerComponent(player: Player) {

    val icon = if (player.play == true) R.drawable.ic_pause else R.drawable.ic_play
    Row(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .height(50.dp)
            .fillMaxWidth()
            .background(Purple40),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            player.artist?.name.orEmpty(),
            modifier = Modifier.padding(horizontal = 12.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = icon),
            contentDescription = "play/pause",
            modifier = Modifier
                .size(35.dp)
                .clickable {})
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close",
            modifier = Modifier
                .size(35.dp)
                .clickable {}
        )


    }
}


@Composable
fun ArtistItem(artist: Artist) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 10.dp)

    ) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = artist.image,
            contentDescription = "Aritsts image",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name.orEmpty(), color = Color.White)
    }
}

//@Preview
@Composable
fun ArtistItemPreview() {
    val artist =
        Artist(
            name = "LLLIT",
            description = "LLLIT DESCRIPTION",
            image = "https://i.pinimg.com/1200x/55/1d/e2/551de222801a685f2b218c31e73cc31d.jpg",
            //songs = emptyList()
        )
    ArtistItem(artist = artist)

}


/*
fun createArtist(db: FirebaseFirestore) {


    // nombre de la coleccion en firebase
    // en este caso "artists"

    val random = (1..10000).random()
    val artist = Artist(name = "Random $random", numberOfSong = random)
    val artistsCollection = "artists"

    db.collection(artistsCollection)
        .add(artist)
        .addOnSuccessListener {
            // si todo va bien se ejecuta este codigo
            Log.i("Matias", "Artist added with ID: ${it.id}")
        }
        .addOnFailureListener {
            // si hay un error se ejecuta este codigo
            Log.i("Matias", "Error adding artist")
        }
        .addOnCompleteListener {
            // se ejecuta siempre
            Log.i("Matias", "COMPLETE")
        }





}

 */