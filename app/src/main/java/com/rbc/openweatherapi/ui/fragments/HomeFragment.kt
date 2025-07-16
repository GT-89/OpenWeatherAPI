package com.rbc.openweatherapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rbc.openweatherapi.MainActivity
import com.rbc.openweatherapi.R
import com.rbc.openweatherapi.databinding.FragmentHomeBinding
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import com.rbc.openweatherapi.models.response.CurrentWeatherResponse
import com.rbc.openweatherapi.models.response.FiveDayThreeHourForecastResponse
import com.rbc.openweatherapi.models.statemachine.HomeScreenEvents
import com.rbc.openweatherapi.models.statemachine.HomeScreenStates
import com.rbc.openweatherapi.network.NetworkConstants
import com.rbc.openweatherapi.ui.adapters.FiveDayForecastAdapter
import com.rbc.openweatherapi.ui.interfaces.IFiveDayForecastTouchListener
import com.rbc.openweatherapi.ui.viewmodels.HomeViewModel
import kotlin.math.ceil

class HomeFragment: AbstractOwmNavigationFragment(), IFiveDayForecastTouchListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvDailyForecast: RecyclerView
    private var currentCity: String? = null
    private var threeHourList: List<WeatherSnapshotInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        with(homeViewModel) {
            homeScreenStates.observe(viewLifecycleOwner) {
                when(it) {
                    is HomeScreenStates.InitialState            ->  checkIfCoordinatesExist()
                    is HomeScreenStates.DailyForecastSelected   ->  displayDetailedForecastData()
                }
            }
            currentWeatherData.observe(viewLifecycleOwner) {
                it?.let { currWeatherData ->
                    loadCurrentWeatherData(currWeatherData)
                }
            }
            fiveDayForecastData.observe(viewLifecycleOwner) {
                it?.let { weekForeCast ->
                    loadDailyForecastData(weekForeCast)
                }
            }
            homeScreenDataRetrieved.observe(viewLifecycleOwner) {
                showNetworkProgressBar(!it)
            }
        }
    }

    private fun checkIfCoordinatesExist() {
        showNetworkProgressBar(true)
        getCoordinates()?.let {
            homeViewModel.handleEvent(HomeScreenEvents.RetrieveWeatherData(it.lat, it.lon, 5))
        } ?: run {
            checkPermissionsAndRetrieveGeolocationData() {
                getCurrentLocationIfLastLocationIsNull {
                    getCoordinates()?.let {
                        homeViewModel.handleEvent(HomeScreenEvents.RetrieveWeatherData(it.lat, it.lon, 5))
                    }
                }
            }
        }
    }

    private fun displayDetailedForecastData() {
        activity?.let {
            if(it is MainActivity) {
                it.displayDetailedFragment()
            }
        }
    }

    private fun loadCurrentWeatherData(currentWeatherData: CurrentWeatherResponse) {
        val weatherImageUrl = "${NetworkConstants.OPEN_WEATHER_MAP_BASE_URL}/img/w/${currentWeatherData.weather.first().icon}.png"
        val tempString = resources.getString(R.string.temperature, ceil(currentWeatherData.main.temp).toInt())
        val feelsLikeString = resources.getString(R.string.temperature_feels_like, ceil(currentWeatherData.main.feels_like).toInt())
        val highLowString = resources.getString(R.string.temperature_high_low, ceil(currentWeatherData.main.temp_max).toInt(), ceil(currentWeatherData.main.temp_min).toInt())
        binding.lFragmentDetails.apply {
            tvCityName.text = currentWeatherData.name
            tvWeatherDesc.text = currentWeatherData.weather.first().description
            tvTemperature.text = tempString
            tvFeelsLike.text = feelsLikeString
            tvHighLow.text = highLowString
            Glide.with(requireContext())
                .load(weatherImageUrl)
                .into(ivWeatherIcon)
        }
    }

    private fun loadDailyForecastData(dailyForecastData: FiveDayThreeHourForecastResponse) {
        threeHourList = dailyForecastData.list
        currentCity = "${dailyForecastData.city.name}, ${dailyForecastData.city.country}"
        val filteredList = dailyForecastData
            .list
            .filter { it.dt_txt.contains("00:00:00") }

        rvDailyForecast = binding.rvDailyForecast.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = FiveDayForecastAdapter(filteredList, this@HomeFragment)
        }
    }

    override fun onDailyItemClicked(item: WeatherSnapshotInfo) {
        val day = item.dt_txt.substring(0, 10)
        threeHourList?.let { list ->
            val filteredList = list.filter { it.dt_txt.substring(0, 10) == day }
            setThreeHourlyList(filteredList)
        }
        val dataMap = hashMapOf<String, WeatherSnapshotInfo>()
        currentCity?.let { dataMap[it] = item }
        setDetailedDataMap(dataMap)
        homeViewModel.handleEvent(HomeScreenEvents.DisplayDetailedWeatherForecast)
    }

}