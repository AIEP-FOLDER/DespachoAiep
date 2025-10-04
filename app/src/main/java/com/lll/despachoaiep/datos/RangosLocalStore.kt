package com.lll.despachoaiep.datos

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.rangosDataStore by preferencesDataStore(name = "rangos_temperatura")

suspend fun guardarRangosLocal(context: Context, min: Double, max: Double) {
    context.rangosDataStore.edit { prefs ->
        prefs[doublePreferencesKey("minTemp")] = min
        prefs[doublePreferencesKey("maxTemp")] = max
    }
    Log.d("RangosLocal", "Rangos guardados localmente: min=$min, max=$max")

}

suspend fun leerRangosLocal(context: Context): Pair<Double, Double> {
    val prefs = context.rangosDataStore.data.map { it }.first()
    val min = prefs[doublePreferencesKey("minTemp")] ?: -18.0
    val max = prefs[doublePreferencesKey("maxTemp")] ?: -10.0
    return Pair(min, max)
}