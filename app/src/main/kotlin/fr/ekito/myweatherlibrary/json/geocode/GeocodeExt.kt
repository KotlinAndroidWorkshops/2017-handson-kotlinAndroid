package fr.ekito.myweatherlibrary.json.geocode

fun Geocode.getLocation(): Location? = results.firstOrNull()?.geometry?.location