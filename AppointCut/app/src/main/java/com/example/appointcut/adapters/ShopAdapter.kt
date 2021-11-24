package com.example.appointcut.adapters

import com.example.appointcut.models.Shop
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appointcut.databinding.RecyclerRowBarbershopBinding
import com.example.appointcut.network.BASE_URL
import fragments.FragmentFindBarberShopDirections

/**
 * Adapter for Shop Data Class
 * @param shops List of shops to be adopted
 * @param navController Navigation Controller of the parent view
 */
class ShopAdapter(private val shops: List<Shop>,
                  private var navController: NavController): RecyclerView.Adapter<ShopAdapter.ShopHolder>() {


    /**
     * Holder for the Shop data class
     * @param binding View binding used by the holder
     * @param navController Nav controller used by the parent view
     */
    class ShopHolder(
        private var binding: RecyclerRowBarbershopBinding,
        var navController: NavController) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * binds the shop item to the holder
         * @param shop the shop to be bound to the holder
         */
        fun bind(shop: Shop) {
            binding.txtBarberShopName.text = shop.name
            binding.myImages.load("$BASE_URL/${shop.imgSrcUrl}")
            binding.txtBarberShopRating.text = shop.rating.toString()
            binding.barberShopRowLayout.setOnClickListener { onHolderClick(shop) }
        }

        /**
         * Navigates to Shop Map fragment on click
         * @param shop The shop clicked by user
         */
        private fun onHolderClick(shop: Shop){
            Log.d("ShopHolder", "OnClick called with ID: ${shop.id}")

            val id = shop.id
            val name = shop.name
            val address = shop.address
            val contact = shop.contact
            val rating = shop.rating

            val action: NavDirections = FragmentFindBarberShopDirections
                .actionFragmentFindBarberShopToFragmentBarberShopMap(name, address, contact, rating, id)
            navController.navigate(action)
        }

    }

    /**
     * Creates a new Shop Holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        Log.d("ShopAdapter","onCreateViewHolder")
        return ShopHolder(RecyclerRowBarbershopBinding
            .inflate(LayoutInflater.from(parent.context),parent,false),navController)
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