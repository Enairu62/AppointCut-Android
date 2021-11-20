package com.example.appointcut.adapters

import DataModels.ShopService
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appointcut.R
import com.example.appointcut.databinding.RecyclerRowSelectservicesBinding

class ShopServiceAdapter(private val services: List<ShopService>,
                         private val cb: (String) -> Unit):
    RecyclerView.Adapter<ShopServiceAdapter.ShopServiceHolder>() {

    class ShopServiceHolder(
        private val binding: RecyclerRowSelectservicesBinding,
        private val cb: (String) -> Unit
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(service: ShopService){
            binding.serviceName.text = service.name
            binding.servicePic.visibility = View.GONE
            binding.servicePrice.text = "Php ${service.price}"
            binding.root.setOnClickListener { selectBarber(service.name) }
        }

        private fun selectBarber(name: String){
            cb(name)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopServiceHolder {
        return ShopServiceHolder(
            RecyclerRowSelectservicesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false),
            cb
        )
    }


    override fun onBindViewHolder(holder: ShopServiceHolder, position: Int) {
        holder.bind(services[position])
    }


    override fun getItemCount(): Int {
        return services.size
    }
}