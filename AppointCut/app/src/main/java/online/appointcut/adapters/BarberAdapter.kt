package online.appointcut.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.databinding.RecyclerRowSelectbarberBinding
import online.appointcut.models.Barber

class BarberAdapter(private val list: List<Barber>, private val callback: (Barber) -> Unit):
    RecyclerView.Adapter<BarberAdapter.BarberHolder>() {

    class BarberHolder(private val binding: RecyclerRowSelectbarberBinding):
        RecyclerView.ViewHolder(binding.root){

        /**
         * sets view content to show Barber information
         * @param barber Barber whose info is to be shown
         */
        fun bind(barber: Barber, cb: (Barber) -> Unit) {
            //hide the pic and specialty cuz fuck those
            binding.myProfilePics.visibility = View.GONE
            binding.barberSpecialty.visibility = View.GONE

            binding.barberName.text = barber.fullName
            binding.root.setOnClickListener { cb(barber) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarberHolder {
        return BarberHolder(RecyclerRowSelectbarberBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: BarberHolder, position: Int) {
        holder.bind(list[position], callback)
    }


    override fun getItemCount(): Int {
        return list.size
    }
}