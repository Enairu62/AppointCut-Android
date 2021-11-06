package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appointcut.HomePageCustomer;
import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNotifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotifications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNotifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotifications.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotifications newInstance(String param1, String param2) {
        FragmentNotifications fragment = new FragmentNotifications();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutCustomer = getActivity().findViewById(R.id.drawerLayoutCustomer);
                BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
                NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
                View view2 = getActivity().findViewById(R.id.toolbar);
                ImageView imageNotif = view2.findViewById(R.id.imageNotif);
                if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutCustomer.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.popBackStack();
                    imageNotif.setVisibility(View.VISIBLE);
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
        View view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        View view2 = getActivity().findViewById(R.id.toolbar);
        ImageView imageNotif = view2.findViewById(R.id.imageNotif);
        imageNotif.setVisibility(View.GONE);
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
        bottomNavCustomer.setVisibility(View.GONE);
        return view;
    }
}