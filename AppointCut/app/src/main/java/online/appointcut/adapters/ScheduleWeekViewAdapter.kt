package online.appointcut.adapters

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import online.appointcut.models.WeekViewEvent
import java.util.*


class ScheduleWeekViewAdapter(
    private val loadMoreHandler: (Calendar, Calendar) -> Unit,
    private val onEmptyClick: (Calendar) -> Unit
) : WeekView.PagingAdapter<WeekViewEvent>() {
    private var idCounter = 0.toLong()

    override fun onCreateEntity(
        item: WeekViewEvent
    ): WeekViewEntity {


        Log.d("SWVAdapter","${item.startTime.time} -> ${item.endTime.time}")

        return WeekViewEntity.Event.Builder(item)
            .setId(idCounter++)
            .setTitle(item.title)
            .setStartTime(item.startTime)
            .setEndTime(item.endTime)
            .build()
    }

    override fun onLoadMore(startDate: Calendar, endDate: Calendar) {
        Log.d("SWVAdapter", "onLoadMore triggered")
        loadMoreHandler(startDate, endDate)
    }

    override fun onEmptyViewClick(time: Calendar) {
        super.onEmptyViewClick(time)
        onEmptyClick(time)
    }

    override fun onEventClick(data: WeekViewEvent) {
        Toast.makeText(context,"Time slot is taken", Toast.LENGTH_SHORT).show()
    }
}