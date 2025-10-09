package com.lll.despachoaiep.datos.network

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class OpenRouteServiceClient {
    private val client = OkHttpClient()

    fun obtenerRuta(
        origenLat: Double,
        origenLon: Double,
        destinoLat: Double,
        destinoLon: Double,
        onSuccess: (List<Pair<Double, Double>>, Double, Double) -> Unit,
        onError: (String) -> Unit

    ) {
        val url = "${OpenRouteServiceConfig.BASE_URL}v2/directions/driving-car"
        val coordinates = JSONArray().apply {
            put(JSONArray().apply {
                put(origenLon)
                put(origenLat)
            })
            put(JSONArray().apply {
                put(destinoLon)
                put(destinoLat)
            })
        }
        val jsonBody = JSONObject().apply {
            put("coordinates", coordinates)
        }
        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", OpenRouteServiceConfig.API_KEY)
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OpenRouteService", "Error: ${e.message}")
                onError(e.message ?: "Error desconocido")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onError("Error HTTP: ${response.code}")
                    return
                }

                val body = response.body?.string()

                Log.d("OpenRouteService", "Código HTTP: ${response.code}")
                Log.d("OpenRouteService", "Cuerpo: $body")

                if (body == null) {
                    onError("Respuesta vacía")
                    return
                }

                try {
                    val json = JSONObject(body)
                    val geometryString = json
                        .getJSONArray("routes")
                        .getJSONObject(0)
                        .getString("geometry")
                    val ruta = decodePolyline(geometryString)

                    //-----------------------------------------------
                    val summary = json
                        .getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONObject("summary")

                    Log.d("OpenRouteService", "summary: $summary")
                    val distanciaKm = summary.getDouble("distance") / 1000.0
                    val duracionMin = summary.getDouble("duration") / 60.0


                    Log.d("OpenRouteService", "Distancia: $distanciaKm Km")
                    Log.d("OpenRouteService", "Duración: $duracionMin m")

                    onSuccess(ruta, distanciaKm, duracionMin)
                } catch (e: Exception) {
                    onError("Error al parsear la ruta: ${e.message}")
                }
            }
        })
    }

}


//------------
/*
Se ha implementado un decodificador de geometría Polyline para interpretar correctamente la respuesta de OpenRouteService. Esta función reconstruye los puntos geográficos del trayecto, permitiendo su visualización en OSMDroid y asegurando trazabilidad visual del despacho.
 */
fun decodePolyline(polyline: String): List<Pair<Double, Double>> {
    val coordinates = mutableListOf<Pair<Double, Double>>()
    var index = 0
    val len = polyline.length
    var lat = 0
    var lng = 0

    while (index < len) {
        var b: Int
        var shift = 0
        var result = 0
        do {
            b = polyline[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if ((result and 1) != 0) (result shr 1).inv() else result shr 1
        lat += dlat

        shift = 0
        result = 0
        do {
            b = polyline[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if ((result and 1) != 0) (result shr 1).inv() else result shr 1
        lng += dlng

        coordinates.add(Pair(lat / 1E5, lng / 1E5))
    }

    return coordinates
}
