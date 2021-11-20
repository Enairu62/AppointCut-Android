package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;
import com.example.appointcut.adapters.ShopAdapter;
import com.example.appointcut.network.NetworkJava;

import java.util.ArrayList;

import DataModels.DataModelBarberShop;
import DataModels.Shop;
import MyAdapters.MyAdapterBarberShop;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFindBarberShop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFindBarberShop extends Fragment implements MyAdapterBarberShop.ItemClickListener{

    private ArrayList<Shop> list = new ArrayList<>();

    public FragmentFindBarberShop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentFindBarberShop.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFindBarberShop newInstance() {
        FragmentFindBarberShop fragment = new FragmentFindBarberShop();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentFindBarberShop", "onCreate started");
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutCustomer = getActivity().findViewById(R.id.drawerLayoutCustomer);
                if(drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)){
                    drawerLayoutCustomer.closeDrawer(GravityCompat.START);
                }
                else{
                    getActivity().moveTaskToBack(true);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("FragmentFindBarberShop", "onCreateView started");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_barber_shop, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        try{
            list = NetworkJava.INSTANCE.getShop();
            Log.d("FragmentShop", list.size()+"");
            NavController navController =
                    (NavController) Navigation
                            .findNavController(getActivity(),R.id.fragmentContainerView);
            ShopAdapter adapter = new ShopAdapter(list,navController);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getContext(), "Unable to load Barbershops", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
    }

    @Override
    public void onItemClick(DataModelBarberShop dataModelBarberShop) {
//        NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
//        NavDirections action = FragmentFindBarberShopDirections
//                .actionFragmentFindBarberShopToFragmentBarberShopMap();
//        navController.navigate(action);
    }
}