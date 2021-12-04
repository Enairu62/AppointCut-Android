package online.appointcut.adapters

import online.appointcut.models.ShopService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.databinding.RecyclerRowSelectservicesBinding

class ShopServiceAdapter(private val services: List<ShopService>,
                         private val cb: (ShopService) -> Unit):
    RecyclerView.Adapter<ShopServiceAdapter.ShopServiceHolder>() {

    class ShopServiceHolder(
        private val binding: RecyclerRowSelectservicesBinding,
        private val cb: (ShopService) -> Unit
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(service: ShopService){
            binding.serviceName.text = service.name
            binding.servicePic.visibility = View.GONE
            binding.servicePrice.text = "Php ${service.price}"
            binding.root.setOnClickListener { selectBarber(service) }
        }

        private fun selectBarber(shopService: ShopService){
            cb(shopService)
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