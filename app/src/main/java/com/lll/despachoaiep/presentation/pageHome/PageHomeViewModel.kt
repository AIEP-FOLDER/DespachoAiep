package com.lll.despachoaiep.presentation.pageHome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lll.despachoaiep.presentation.model.Artist
import com.lll.despachoaiep.presentation.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class PageHomeViewModel : ViewModel() {
    private val database = Firebase.database

    private var db: FirebaseFirestore = Firebase.firestore

    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist: StateFlow<List<Artist>> = _artist

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player


    init {
        /*
        repeat(20){
           loadData()
        }
        */

        getArtists()
        getPlayer()
    }

    private fun getPlayer() {
        viewModelScope.launch {
            collectPlayer().collect {
                val player = it.getValue(Player::class.java)
                _player.value = player
            }
        }

    }
    /*
    private fun loadData() {
        // nombre de la coleccion en firebase
        // en este caso "artists"

        val random = (1..10000).random()
        val artist = Artist(
            name = "Random $random",
            description = "Description ",
            image = "https://i.pinimg.com/1200x/55/1d/e2/551de222801a685f2b218c31e73cc31d.jpg"
        )
        val artistsCollection = "artists"

        db.collection(artistsCollection)
            .add(artist)

    }
     */

    private fun getArtists() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            _artist.value = result
        }
    }

    suspend fun getAllArtists(): List<Artist> {
        return try {

            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Artist::class.java)
                }

        } catch (e: Exception) {
            Log.i("Mts [getAllArtists]", e.toString())
            emptyList()
        }
    }

    private fun collectPlayer(): Flow<DataSnapshot> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("matias log", "Error ${error.message}")
                close(error.toException())
            }

        }
        val ref = database.reference.child("player")
        ref.addValueEventListener(listener)
        awaitClose {
            ref.removeEventListener(listener)
        }

    }

    fun onPlaySelected() {
        if (player.value != null) {
            val currentPlayer = _player.value?.copy(play = !player.value?.play!!)
            val ref = database.reference.child("player")
            ref.setValue(currentPlayer)
        }

    }


}