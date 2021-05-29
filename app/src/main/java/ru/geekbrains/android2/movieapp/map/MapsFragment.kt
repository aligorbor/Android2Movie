package ru.geekbrains.android2.movieapp.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.*
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.FragmentMapBinding
import java.io.IOException

class MapsFragment : Fragment() {
    private val locationPermissionReqCode = 2277
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = ArrayList()
    private var location = LatLng(47.2329, 39.7183)
    private lateinit var binding: FragmentMapBinding

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(locationChanged: Location) {
            location = LatLng(locationChanged.latitude, locationChanged.longitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }
        val marker = googleMap.addMarker(
            MarkerOptions().position(location).title(getString(R.string.start_marker))
        )
        marker?.let { markers.add(it) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        googleMap.setOnMapLongClickListener { latLng ->
            getAddressAsync(latLng)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(BUNDLE_ADDRESS_STR)?.let { it ->
            binding.searchAddress.setText(it)
        }
        checkPermission()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initButtons()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.map_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_map_mode_normal -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            R.id.action_map_mode_satellite -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.action_map_mode_terrain -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            R.id.action_map_traffic -> {
                map.isTrafficEnabled = !map.isTrafficEnabled
                return true
            }
        }
        return false
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionReqCode
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            provider?.let {
                // Будем получать геоположение через каждые 60 секунд или каждые 100 метров
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L,
                    10f,
                    onLocationListener
                )
            }
        } else {
            val location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.looks_like_location_disabled),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getAddressAsync(LatLng(location.latitude, location.longitude))
            }
        }
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(3f)
            )
        }
    }

    private fun getAddressAsync(locationAsync: LatLng) = with(binding) {
        context?.let {
            val geoCoder = Geocoder(it)
            Thread {
                try {
                    val address =
                        geoCoder.getFromLocation(locationAsync.latitude, locationAsync.longitude, 1)
                    textAddress.post {
                        location = locationAsync
                        textAddress.text = address[0].getAddressLine(0)
                        goToAddress(address, textAddress)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun initButtons() = with(binding) {
        buttonSearch.setOnClickListener {
            val searchText = searchAddress.text.toString()
            goToAddressAsync(searchText, it)
        }
        buttonSearch.callOnClick()
    }

    private fun goToAddressAsync(searchText: String, view: View) {
        val geoCoder = Geocoder(view.context)
        Thread {
            try {
                val address = geoCoder.getFromLocationName(searchText, 1)
                if (address.isNotEmpty()) {
                    goToAddress(address, view)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun goToAddress(address: MutableList<Address>, view: View) {
        view.post {
            location = LatLng(address[0].latitude, address[0].longitude)
            setMarker(address[0].getAddressLine(0))
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location, 15f
                )
            )
        }
    }

    private fun setMarker(searchText: String) {
        val marker = map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )
        markers.add(marker)
        drawLine()
    }

    companion object {
        const val BUNDLE_ADDRESS_STR = "address_str"
        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}