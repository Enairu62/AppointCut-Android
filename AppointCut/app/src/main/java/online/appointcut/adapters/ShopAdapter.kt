package online.appointcut.adapters

import online.appointcut.models.Shop
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import coil.load
import online.appointcut.databinding.RecyclerRowBarbershopBinding
import online.appointcut.customerfragments.FragmentFindShopDirections
import online.appointcut.models.Appointment
import online.appointcut.network.BASE_URL

/**
 * Adapter for Shop Data Class
 * @param shops List of shops to be adopted
 * @param navController Navigation Controller of the parent view
 */
class ShopAdapter(private val shops: List<Shop>,
                  private var navController: NavController
                  ,private val appointment: Appointment): RecyclerView.Adapter<ShopAdapter.ShopHolder>() {


    /**
     * Holder for the Shop data class
     * @param binding View binding used by the holder
     * @param navController Nav controller used by the parent view
     */
    class ShopHolder(
        private var binding: RecyclerRowBarbershopBinding,
        var navController: NavController,
        var appointment: Appointment) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * binds the shop item to the holder
         * @param shop the shop to be bound to the holder
         */
        fun bind(shop: Shop) {
            binding.txtBarberShopName.text = shop.name
            if(shop.imgSrcUrl != null)
                binding.myImages.load("$BASE_URL/${shop.imgSrcUrl}")
            else
                binding.myImages.load(drawable = null)
            binding.txtBarberShopRating.text = shop.rating.toString()
            binding.barberShopRowLayout.setOnClickListener { onHolderClick(shop,appointment) }
            binding.shopAddress.text = shop.address
        }

        /**
         * Navigates to Shop Map fragment on click
         * @param shop The shop clicked by user
         */
        private fun onHolderClick(shop: Shop, appointment: Appointment){
            Log.d("ShopHolder", "OnClick called with ID: ${shop.id}")

            val id = shop.id
            val name = shop.name
            val address = shop.address
            val contact = shop.contact
            val rating = shop.rating

            //set appointment ViewModel
            appointment.apply {
                shopId = shop.id
                shopName = shop.name
            }
            //navigate
            val action: NavDirections = FragmentFindShopDirections
                .actionFragmentFindBarberShopToFragmentBarberShopMap(name, address,
                    contact, rating, id,shop.long?.toFloat()?:0.0F,shop.lat?.toFloat()?:0F)
            navController.navigate(action)
        }

    }

    /**
     * Creates a new Shop Holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        Log.d("ShopAdapter","onCreateViewHolder")
        return ShopHolder(RecyclerRowBarbershopBinding
            .inflate(LayoutInflater.from(parent.context),parent,false),navController
            ,appointment)
    }

    /**
     * Binds the shop data to the holder
     */
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        Log.d("ShopAdapter","onBindViewHOlder")
        holder.bind(shops[position])
    }

    /**
     * retrieves the number of items to be displayed by the recycler view
     */
    override fun getItemCount(): Int {
        Log.d("ShopAdapter","itemCount ${shops.size}")
        return shops.size
    }
}