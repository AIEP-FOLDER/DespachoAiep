// java/com/lll/despachoaiep/utils/FusedLocationProviderClient.kt
package com.lll.despachoaiep.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@SuppressLint("MissingPermission")
fun obtenerUbicacionActual(
    context: Context,
    onSuccess: (Location) -> Unit,
    onError: (String) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setMaxUpdates(1)
        .build()



    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                if (location != null) {
                    onSuccess(location)
                } else {
                    onError("No se pudo obtener la ubicación activa.")
                }
            }
        },
        Looper.getMainLooper()
    )

}
