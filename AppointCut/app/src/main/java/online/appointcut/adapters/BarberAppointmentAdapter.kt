package online.appointcut.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.databinding.RecyclerRowScheduleBinding
import online.appointcut.models.Appointment

class BarberAppointmentAdapter(private val appointments: List<Appointment>): RecyclerView.Adapter<BarberAppointmentAdapter.AppointmentHolder>(){
    class AppointmentHolder(private val binding: RecyclerRowScheduleBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(appointment: Appointment){
                binding.txtSchedName.text = appointment.customerName
                binding.txtSchedRange.text = "${appointment.timeIn}\n${appointment.timeOut}"
                binding.txtSchedServices.text = appointment.serviceName
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        return AppointmentHolder(
            RecyclerRowScheduleBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.bind(appointments[position])
    }

    override fun getItemCount(): Int {
        return appointments.size
    }
}