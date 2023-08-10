package com.martabak.phincon

import android.graphics.drawable.Drawable

data class Lokasi (
    var header: String,
    var subText: String,
    var image : Int,
    //0 -> white bg, 1 -> phincon_blue, 2 -> yellow button
    var color : Int = 0,
        )