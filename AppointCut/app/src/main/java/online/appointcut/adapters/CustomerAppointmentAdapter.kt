package online.appointcut.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.databinding.RecyclerRowAppointmentListBinding
import online.appointcut.models.Appointment

class CustomerAppointmentAdapter(
    private val list: List<Appointment>,
    private val clickHandler: ((Appointment) -> Unit)? = null
) :
    RecyclerView.Adapter<CustomerAppointmentAdapter.AppointmentHolder>() {

    class AppointmentHolder(private var binding: RecyclerRowAppointmentListBinding,
    private val cancelHandler: ((Appointment) -> Unit)?) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var appointment: Appointment
         fun bind(appointment: Appointment){
             this.appointment = appointment
             binding.barberShopName.text = appointment.shopName
             binding.barberShopService.text = appointment.serviceName
             val year = appointment.date.split('-')[0].toInt()
             val month = appointment.date.split('-')[1].toInt()
             val date = appointment.date.split('-')[2].toInt()+1
             val hour = appointment.timeIn.split(':')[0].toInt()
             val minute = appointment.timeIn.split(':')[1].toInt()

             binding.barberShopSchedule.text =
                 "$year-$month-$date || $hour:${minute.toString().padStart(2,'0')}"
             binding.root.setOnClickListener { onCancel(this.appointment) }
         }

        private fun onCancel(appointment: Appointment) {
            cancelHandler?.invoke(appointment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        return AppointmentHolder(
            RecyclerRowAppointmentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickHandler
        )
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}