package online.appointcut.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import online.appointcut.databinding.RecyclerRowScheduleBinding
import online.appointcut.models.Appointment
import online.appointcut.network.BASE_URL

class BarberAppointmentAdapter(private val appointments: List<Appointment>): RecyclerView.Adapter<BarberAppointmentAdapter.AppointmentHolder>(){
    class AppointmentHolder(val binding: RecyclerRowScheduleBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(appointment: Appointment){
                binding.txtSchedName.text = appointment.customerName
                binding.txtSchedRange.text = "${appointment.timeIn}\n${appointment.timeOut}"
                binding.txtSchedServices.text = appointment.serviceName
                appointment.hairStyle?.let{
                    Log.d("BAptAdpt", "$BASE_URL/${it.imageLink}")
                    binding.scheduleHairstyleImage.load("$BASE_URL/${it.imageLink}")
                }
                binding.root.setOnClickListener {  }
            }
    }

    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        return AppointmentHolder(
            RecyclerRowScheduleBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.bind(appointments[position])
        appointments[position].hairStyle?.let{
            itemClickListener?.let{listener ->
                holder.binding.root.setOnClickListener {
                    listener.onItemClick(appointments[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    fun interface ItemClickListener{
        fun onItemClick(appointment: Appointment)
    }

    fun setOnItemClickListener(listener: ItemClickListener){
        itemClickListener = listener
    }
}