package online.appointcut.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import coil.load
import online.appointcut.databinding.DialogHairstyleBinding
import online.appointcut.models.HairStyle
import online.appointcut.network.BASE_URL
import java.lang.IllegalStateException

class HairstyleDialog(private val hairStyle: HairStyle): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding= DialogHairstyleBinding.inflate(requireActivity().layoutInflater)
        Log.d("SetHDialog","$BASE_URL/${hairStyle.imageLink}")
        binding.hairstyleImage.load("$BASE_URL/${hairStyle.imageLink}")
        binding.hairstyleName.text = hairStyle.name

        return activity?.let{
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)
                .setTitle("Hairstyle")
                .setNegativeButton("Close", onNegativeButton)
            positiveText?.let{
                builder.setPositiveButton(it, onPositiveButton)}
            builder.create()
        } ?: throw IllegalStateException("Acitivity cannot be null")
    }

    var onPositiveButton = DialogInterface.OnClickListener { dialogInterface, i ->
        Toast.makeText(requireContext(),"Name: ${hairStyle.name}", Toast.LENGTH_SHORT).show()
    }
    var onNegativeButton = DialogInterface.OnClickListener { dialogInterface, i ->

    }
    var positiveText: String? = null
}