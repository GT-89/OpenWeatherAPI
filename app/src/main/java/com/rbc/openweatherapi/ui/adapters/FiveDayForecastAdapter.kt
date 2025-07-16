package com.rbc.openweatherapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbc.openweatherapi.databinding.ItemDailyForecastCardBinding
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import com.rbc.openweatherapi.ui.interfaces.IFiveDayForecastTouchListener
import com.rbc.openweatherapi.ui.viewholders.FiveDayForecastItemViewHolder

class FiveDayForecastAdapter(private val fiveDayForecastList: List<WeatherSnapshotInfo>,
                             private val fiveDayForecastTouchListenerImpl: IFiveDayForecastTouchListener): RecyclerView.Adapter<FiveDayForecastItemViewHolder>() {

    private lateinit var binding: ItemDailyForecastCardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FiveDayForecastItemViewHolder {
        binding = ItemDailyForecastCardBinding.inflate(LayoutInflater.from(parent.context))
        return FiveDayForecastItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FiveDayForecastItemViewHolder, position: Int) {
        val dailyItem = fiveDayForecastList[position]
        holder.apply {
            bind(dailyItem)
            itemView.setOnClickListener {
                fiveDayForecastTouchListenerImpl.onDailyItemClicked(dailyItem)
            }
        }
    }

    override fun getItemCount(): Int = fiveDayForecastList.size
}