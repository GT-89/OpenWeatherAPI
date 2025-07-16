package com.rbc.openweatherapi.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rbc.openweatherapi.databinding.ItemDailyForecastCardBinding
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import com.rbc.openweatherapi.network.NetworkConstants
import kotlin.math.ceil

class FiveDayForecastItemViewHolder(private val binding: ItemDailyForecastCardBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(dailyItem: WeatherSnapshotInfo) {
        val weatherImageUrl = "${NetworkConstants.OPEN_WEATHER_MAP_BASE_URL}/img/w/${dailyItem.weather.first().icon}.png"
        val day = dailyItem.dt_txt.substring(5, 10)
        binding.apply {
            tvTemperature.text = "${ceil(dailyItem.main.temp).toInt()}"
            tvDay.text = day
            Glide.with(binding.root.context)
                .load(weatherImageUrl)
                .into(ivWeatherIcon)
        }
    }

}