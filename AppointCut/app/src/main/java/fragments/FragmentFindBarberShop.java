package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DataModels.DataModelBarberShop;
import MyAdapters.MyAdapterBarberShop;
import com.example.appointcut.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFindBarberShop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFindBarberShop extends Fragment implements MyAdapterBarberShop.ItemClickListener{

    private ArrayList<DataModelBarberShop> list = new ArrayList<>();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_barber_shop, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterBarberShop adapter = new MyAdapterBarberShop(list,this);
        recyclerView.setAdapter(adapter);
        buildListData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        buildListData();
    }

    private void buildListData(){

        int images[] = {R.drawable.covent_grooming_lounge,R.drawable.the_refined,R.drawable.felipeandsons,
                R.drawable.bossman_barbershop, R.drawable.cavalry_barbershop, R.drawable.the_mens_room,};

        list.add(new DataModelBarberShop(images[0],"Covent Grooming Lounge", "A traditional barbershop and lounge for the modern day gent.#FeelYourBest",
                "The Grand Midori, 160 Legazpi St 1229 Makati, Philippines", 639054090242L, "10:00 am - 7:00 pm (Everyday)", 5.0f, 10.5f));

        list.add(new DataModelBarberShop(images[1],"The Refined", "Grooming • Spa • Networking lounge • A gentlemen’s lifestyle experience",
                "2/F Fort Point II Building, The Fort Strip, Bonifacio Global City 1634 Taguig, Philippines", 639162191129L, "8:00 am - 5:00 pm (Wed - Sun)", 4.0f,17.0f));

        list.add(new DataModelBarberShop(images[2],"Felipe & Sons" , "Felipe and Sons is pioneering concept in lifestyle services for men.",
                "1216, E. Rodriguez Sr. Avenue, New Manila, 1112 Quezon City, Philippines", 639176370323L, "10:00 am - 7:00 pm (Everyday)", 3.0f,3.0f));

        list.add(new DataModelBarberShop(images[3],"BOSSMAN" , "LOOK GOOD CHANGING THE WORLD!",
                "Ayala Malls The 30th, 30 Meralco Ave, Ortigas Center Pasig, Philippines", 639173228861L, "10:00 am - 6:00 pm (Everyday)", 3.5f, 6.6f));

        list.add(new DataModelBarberShop(images[4],"28 Cavalry" ,"BARBERSHOP | POUR OVER COFFEE BAR",
                "Level 4, The Podium Mall, ADB Avenue, Ortigas Center 1550 Mandaluyong, Philippines", 639178972887L, "10:00 am - 9:00 pm (Everyday)", 2.5f, 2.1f));

        list.add(new DataModelBarberShop(images[5],"The Men’s Room" ,"A full line of salon and pampering services designed specifically for a man’s grooming and comfort.",
                "SM Megamall Building C, D, Epifanio de los Santos Ave, Ortigas Center, Mandaluyong, Metro Manila", 639166570105L, "11:00 am - 10:00 pm (Everyday)", 5.0f, 9.0f));
    }

    @Override
    public void onItemClick(DataModelBarberShop dataModelBarberShop) {
        NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
        FragmentFindBarberShopDirections.ActionFragmentFindBarberShopToFragmentBarberShopMap action = FragmentFindBarberShopDirections.actionFragmentFindBarberShopToFragmentBarberShopMap
                (dataModelBarberShop.getBarberShopName(), dataModelBarberShop.getBarberAddress(),dataModelBarberShop.getBarberContact(), dataModelBarberShop.getBarberSchedule(), dataModelBarberShop.getBarberRating(), dataModelBarberShop.getBarberShopDistance());
        navController.navigate(action);

    }
}