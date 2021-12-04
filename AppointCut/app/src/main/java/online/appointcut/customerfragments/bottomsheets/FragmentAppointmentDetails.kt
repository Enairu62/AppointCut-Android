package online.appointcut.customerfragments.bottomsheets

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import online.appointcut.R
import online.appointcut.databinding.FragmentBottomSheetAppointmentDetailsBinding
import online.appointcut.models.Appointment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.FragmentBarberShopMapDirections


class FragmentAppointmentDetails : BottomSheetDialogFragment() {
    private val sharedViewModel: Appointment by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentBottomSheetAppointmentDetailsBinding
            .inflate(inflater,container,false).apply{
                btnBookNow.setOnClickListener {
                    lifecycleScope.launchWhenCreated {
                        sharedViewModel.setAppointment(requireContext()) {
                            openDialog()
                        }
                    }
                }
                txtService2.text = sharedViewModel.serviceName
                txtBarberName.text = sharedViewModel.employeeName
                txtPrice.text = "Php ${sharedViewModel.amountDue}"
                schedule.text = "${sharedViewModel.date} / ${sharedViewModel.timeIn} - ${sharedViewModel.timeOut}"
            }.root
    }

    private fun openDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_appointment_success)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
        dialog.window!!.setLayout(width, height)

        val btnContinue = dialog.findViewById<View>(R.id.btnContinue) as Button
        btnContinue.setOnClickListener { dialog.dismiss() }

        dialog.findViewById<Button>(R.id.payNow).setOnClickListener {
            findNavController().navigate(
                FragmentBarberShopMapDirections
                    .actionFragmentBarberShopMapToPaymentFragment())
            dialog.dismiss()
            dismiss()
        }
        dialog.setOnDismissListener {
            dismiss()
        }

        dialog.show()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BottomSheetFragmentAppointmentDetails.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): FragmentAppointmentDetails {
            val fragment = FragmentAppointmentDetails()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}