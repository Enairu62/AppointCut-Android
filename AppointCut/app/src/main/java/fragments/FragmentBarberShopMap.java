package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.appointcut.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBarberShopMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBarberShopMap extends Fragment {


    // TODO: Rename and change types of parameters
    private String shopName;
    private String shopAddress;
    private String shopContact;
    private float shopRating;
    private float mParam5;
    private float mParam6;


    public FragmentBarberShopMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBarberShopMap newInstance() {
        FragmentBarberShopMap fragment = new FragmentBarberShopMap();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            FragmentBarberShopMapArgs args = FragmentBarberShopMapArgs.fromBundle(getArguments());
            shopName = args.getName();
            shopAddress = args.getAddress();
            shopContact = args.getContact();
            shopRating = args.getRating();
//            mParam5 = args.getBarbershopRating();
//            mParam6 = args.getBarbershopDistance();
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutCustomer = getActivity().findViewById(R.id.drawerLayoutCustomer);
                BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                NavController navController = navHostFragment.getNavController();
                if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutCustomer.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.navigate(R.id.action_fragmentBarberShopMap_to_fragmentFindBarberShop);
                    bottomNavCustomer.setVisibility(View.VISIBLE);
                    bottomNavCustomer.getMenu().setGroupCheckable(0, true, true);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barber_shop_map, container, false);
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
        bottomNavCustomer.setVisibility(View.GONE);

        displayMap();

        //bind views
        TextView shopName = (TextView) view.findViewById(R.id.txtShopName);
        TextView shopAddress = (TextView) view.findViewById(R.id.txtShopAddress);
        TextView shopContact = (TextView) view.findViewById(R.id.txtShopContact);
        TextView shopSched = (TextView) view.findViewById(R.id.txtShopSched);
        RatingBar shopRating = (RatingBar) view.findViewById(R.id.shopRatingBar);
        TextView shopDistance = (TextView) view.findViewById(R.id.txtShopDistance);

        //set contents
        shopName.setText(this.shopName);
        shopAddress.setText(this.shopAddress);
        shopContact.setText(String.valueOf(this.shopContact));
        shopSched.setText(this.shopRating+"");
        shopRating.setRating(mParam5);
        shopDistance.setText(mParam6 + " km");

        //set onclick to button
        Button btnSetAppointment = view.findViewById(R.id.btnSetAppointment);
        btnSetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAppointmentMethod();
            }
        });

        return view;
    }

    private void buttonAppointmentMethod(){
        BottomSheetDialogFragment bottomSheetFragmentSelectServices = new BottomSheetFragmentSelectServices();
        bottomSheetFragmentSelectServices.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectServices.getTag());
    }

    private void displayMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " " + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
    }

}