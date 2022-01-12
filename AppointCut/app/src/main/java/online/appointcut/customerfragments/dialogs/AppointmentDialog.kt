package online.appointcut.customerfragments.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import online.appointcut.databinding.DialogCancelAppointmentBinding
import online.appointcut.models.Appointment
import java.lang.IllegalStateException

open class AppointmentDialog(private val appointment: Appointment): DialogFragment() {

    var onPositiveAnswer: (() -> Unit)? = null
    var onNegativeAnswer: (() -> Unit)? = null
    var message: String? = null
    var positiveText: String? = null
    var negativeText: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val binding = DialogCancelAppointmentBinding.inflate(inflater,null,false)

            message?.let { m ->
                binding.message.text = m
            }

            binding.shop.text = appointment.shopName
            binding.dateTime.text = "${appointment.date} | ${appointment.timeIn.split(":")[0]}:${appointment.timeIn.split(":")[1]}"

            builder.setView(
                binding!!.root
            )
                .setPositiveButton(positiveText?:"Yes"){dialog, id ->
                    onPositiveAnswer?.invoke()
                }
                .setNegativeButton(negativeText?:"No") { dialog, id ->
                    onNegativeAnswer?.invoke()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}