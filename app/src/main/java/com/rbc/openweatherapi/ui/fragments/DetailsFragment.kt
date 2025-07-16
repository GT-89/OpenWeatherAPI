package com.rbc.openweatherapi.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.rbc.openweatherapi.R
import com.rbc.openweatherapi.databinding.FragmentDetailsBinding
import com.rbc.openweatherapi.network.NetworkConstants
import com.rbc.openweatherapi.ui.viewmodels.DetailsViewModel
import kotlin.math.ceil


class DetailsFragment: AbstractOwmNavigationFragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        loadDetailedForecastData()
        drawLineChart()
    }

    private fun loadDetailedForecastData() {
        getDetailedDataMap()?.let {
            val data = it.values.first()
            val weatherImageUrl = "${NetworkConstants.OPEN_WEATHER_MAP_BASE_URL}/img/w/${data.weather.first().icon}.png"
            val tempString = resources.getString(R.string.temperature, ceil(data.main.temp).toInt())
            val feelsLikeString = resources.getString(R.string.temperature_feels_like, ceil(data.main.feels_like).toInt())
            val highLowString = resources.getString(R.string.temperature_high_low, ceil(data.main.temp_max).toInt(), ceil(data.main.temp_min).toInt())
            val windString = resources.getString(R.string.weather_details_wind, ceil(data.wind.speed).toInt())
            val humidityString = resources.getString(R.string.weather_details_humidity, data.main.humidity)
            val pressureString = resources.getString(R.string.weather_details_pressure, data.main.pressure)
            val visibilityString = resources.getString(R.string.weather_details_visibility, data.visibility/10)
            binding.apply {
                lFragmentDetails.tvCityName.text = it.keys.first()
                lFragmentDetails.tvWeatherDesc.text = data.weather.first().description
                lFragmentDetails.tvTemperature.text = tempString
                lFragmentDetails.tvFeelsLike.text = feelsLikeString
                lFragmentDetails.tvHighLow.text = highLowString
                tvWind.text = windString
                tvHumidity.text = humidityString
                tvPressure.text = pressureString
                tvVisibility.text = visibilityString
                Glide.with(requireContext())
                    .load(weatherImageUrl)
                    .into(lFragmentDetails.ivWeatherIcon)
            }
        }
    }

    private fun drawLineChart() {
        val lineChart: LineChart = binding.lcForecast
        val lineEntries: List<Entry> = getDataSet()
        val lineDataSet = LineDataSet(lineEntries, "Temperature")
        lineDataSet.color = requireContext().getColor(R.color.secondary_background_color)
        lineDataSet.setCircleColor(Color.BLUE)
        lineDataSet.lineWidth = 2f
        lineDataSet.circleRadius = 4f
        lineDataSet.setDrawValues(false) // Hide values on the line

        val lineData = LineData(lineDataSet)
        lineChart.data = lineData

        lineChart.invalidate() // Refresh the chart
    }

    private fun getDataSet(): List<Entry> {
        val lineEntries: MutableList<Entry> = mutableListOf()
        getThreeHourlyList()?.let {
            it.forEach { entry ->
                val time = entry.dt_txt.substring(11, 13).toFloat()
                val temp = entry.main.temp.toFloat()
                lineEntries.add(Entry(time, temp))
            }
        }

        return lineEntries
    }

}