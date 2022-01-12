package online.appointcut.customerfragments.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import online.appointcut.models.HairStyle
import online.appointcut.network.ApcService

class HairStyleViewModel : ViewModel(){
    private var _hairstyles = MutableLiveData<List<HairStyle>>()
    public val hairstyles: LiveData<List<HairStyle>> = _hairstyles

    fun getHairStyles(){
        viewModelScope.launch {
            val list = ApcService.retrofitService.getHairstyles()
            Log.d("HSVM", "Retrieved Hairstyles")
            for(l in list){
                l.description = ApcService.retrofitService.getHairstyleDescription(l.name)
            }
            _hairstyles.value = list
        }
    }
}