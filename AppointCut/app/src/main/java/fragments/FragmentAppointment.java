package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import DataModels.DataModelAppointmentList;
import MyAdapters.MyAdapterAppointmentList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAppointment extends Fragment {

    private ArrayList<DataModelAppointmentList> listApproved = new ArrayList<>();
    private ArrayList<DataModelAppointmentList> listPending = new ArrayList<>();
    private ArrayList<DataModelAppointmentList> listCompleted = new ArrayList<>();
    TextView emptyApproved, emptyPending, emptyCompleted;
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAppointment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAppointment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAppointment newInstance(String param1, String param2) {
        FragmentAppointment fragment = new FragmentAppointment();
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
                if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutCustomer.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.navigate(R.id.action_fragmentAppointment_to_fragmentFindBarberShop);
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
        view = inflater.inflate(R.layout.fragment_appointment, container, false);
        declareViews();
        displayAppointmentMethod();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buildListDataApproved();
        buildListDataPending();
        buildListDataCompleted();
        emptyApprovedLabel();
        emptyPendingLabel();
        emptyCompletedLabel();
    }

    private void declareViews(){
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
        bottomNavCustomer.setVisibility(View.GONE);

        emptyApproved = (TextView) view.findViewById(R.id.emptyApproved);
        emptyPending = (TextView) view.findViewById(R.id.emptyPending);
        emptyCompleted = (TextView) view.findViewById(R.id.emptyCompleted);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
    }

    private void buildListDataApproved(){
        //example of empty list
        int profilePic[] = {R.drawable.cavalry_barbershop};
        listApproved.add(new DataModelAppointmentList(profilePic[0],"28 Cavalry", "Haircut","November 10, 2021 / 10:00 am - 11:00 am"));
    }

    private void buildListDataPending(){
        int profilePic[] = {R.drawable.cavalry_barbershop};
        //listPending.add(new DataModelAppointmentList(profilePic[0],"28 Cavalry", "Hair Color","November 16, 2021 / 12:00 pm - 01:00 pm"));
    }

    private void buildListDataCompleted(){
        int profilePic[] = {R.drawable.cavalry_barbershop};
        listCompleted.add(new DataModelAppointmentList(profilePic[0],"28 Cavalry", "Massage","November 7, 2021 / 03:00 pm - 04:00 pm"));
        listCompleted.add(new DataModelAppointmentList(profilePic[0],"28 Cavalry", "Haircut","October 19, 2021 / 9:30 am - 10:30 am"));
    }

    private void emptyApprovedLabel(){
        if (listApproved.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyApproved.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyApproved.setVisibility(View.INVISIBLE);
        }
    }

    private void emptyPendingLabel(){
        if (listPending.isEmpty()) {
            recyclerView2.setVisibility(View.GONE);
            emptyPending.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView2.setVisibility(View.VISIBLE);
            emptyPending.setVisibility(View.INVISIBLE);
        }
    }

    private void emptyCompletedLabel(){
        if (listCompleted.isEmpty()) {
            recyclerView3.setVisibility(View.GONE);
            emptyCompleted.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView3.setVisibility(View.VISIBLE);
            emptyCompleted.setVisibility(View.INVISIBLE);
        }
    }

    private void displayAppointmentMethod(){
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);

        MyAdapterAppointmentList adapter1 = new MyAdapterAppointmentList(listApproved);
        MyAdapterAppointmentList adapter2 = new MyAdapterAppointmentList(listPending);
        MyAdapterAppointmentList adapter3 = new MyAdapterAppointmentList(listCompleted);

        recyclerView.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);

        buildListDataApproved();
        buildListDataPending();
        buildListDataCompleted();
        emptyApprovedLabel();
        emptyPendingLabel();
        emptyCompletedLabel();

    }
}