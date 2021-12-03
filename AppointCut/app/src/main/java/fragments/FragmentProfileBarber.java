package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import online.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfileBarber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfileBarber extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfileBarber() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfileBarber.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfileBarber newInstance(String param1, String param2) {
        FragmentProfileBarber fragment = new FragmentProfileBarber();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutBarber = getActivity().findViewById(R.id.drawerLayoutBarber);
                NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
                BottomNavigationView bottomNavBarber = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavBarber);
                if (drawerLayoutBarber.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutBarber.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.navigate(R.id.action_fragmentProfileBarber_to_fragmentSchedule);
                    bottomNavBarber.setVisibility(View.VISIBLE);
                    bottomNavBarber.getMenu().setGroupCheckable(0, true, true);
                    bottomNavBarber.setSelectedItemId(R.id.fragmentSchedule);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_barber, container, false);
        BottomNavigationView bottomNavBarber = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavBarber);
        bottomNavBarber.setVisibility(View.GONE);
        return view;
    }
}