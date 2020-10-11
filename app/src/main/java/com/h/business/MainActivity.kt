package com.h.business

import GetBQuery
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.h.business.network.NetworkService
import com.h.business.ui.ListFragment

/*Client ID
AWFw6rqK2g6hqP7Uq8bG6Q

API Key
yYSQaxs0B2IDJlJ10OTVJwIBoTCPtIrElar0KOMxtGOItCoqvzhipGu35nEvdMQyh2t_W7QtYaiIKX0segCk-6obsJTCgwx6sZsfx--WZeYksCq2qUJcglvrKtaCX3Yx*/

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED


            ) {
                Log.w(TAG, "Here 1")

                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )

                return
            } else {
                val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val longitude: Double = location?.longitude ?: 0.0
                val latitude: Double = location?.latitude ?: 0.0
                Log.w(TAG, "latitude $latitude  longitude$longitude")

                //getB(longitude, latitude)

                val mainFragment = ListFragment.newInstance(longitude, latitude)
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit()


            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.w(TAG, "Here 2 requestCode $requestCode")
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val longitude: Double = location?.longitude ?: 0.0
            val latitude: Double = location?.latitude ?: 0.0
            Log.w(TAG, "latitude $latitude  longitude$longitude")

            //getB(longitude, latitude)

           val mainFragment = ListFragment.newInstance(longitude, latitude)
            supportFragmentManager.beginTransaction()
                .add(R.id.container, mainFragment)
                .commit()
        }


    }

}