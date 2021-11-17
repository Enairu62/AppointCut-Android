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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import DataModels.DataModelAppointmentList;
import DataModels.DataModelMessages;
import MyAdapters.MyAdapterAppointmentList;
import MyAdapters.MyAdapterMessage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAppointment extends Fragment {

    private ArrayList<DataModelAppointmentList> appointmentLists = new ArrayList<>();
    private ArrayList<DataModelAppointmentList>  approvedLists = new ArrayList<>();
    private ArrayList<DataModelAppointmentList> pendingLists = new ArrayList<>();
    private ArrayList<DataModelAppointmentList> completedLists = new ArrayList<>();
    TextView emptyApproved, emptyPending, emptyCompleted;
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    View view;
    EditText search;
    MyAdapterAppointmentList adapter1, adapter2, adapter3, adapter4;

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
        searchAppointment();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        approvedLists.clear();
        completedLists.clear();
        pendingLists.clear();
       displayAppointmentMethod();
    }

    private void declareViews(){
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
        bottomNavCustomer.setVisibility(View.GONE);

        emptyApproved = (TextView) view.findViewById(R.id.emptyApproved);
        emptyPending = (TextView) view.findViewById(R.id.emptyPending);
        emptyCompleted = (TextView) view.findViewById(R.id.emptyCompleted);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);

        search = (EditText) view.findViewById(R.id.inputSearch);
    }

    private void buildListData(){
        int profilePic[] = {R.drawable.cavalry_barbershop};
        appointmentLists.add(new DataModelAppointmentList(profilePic[0],"28 Cavalry", "Hair Color","November 16, 2021 / 12:00 pm - 01:00 pm","Pending"));
        appointmentLists.add(new DataModelAppointmentList(profilePic[0],"BOSSMAN", "Haircut","November 10, 2021 / 10:00 am - 11:00 am","Approved"));
        appointmentLists.add(new DataModelAppointmentList(profilePic[0],"Felipe & Sons", "Massage","November 7, 2021 / 03:00 pm - 04:00 pm","Completed"));
        appointmentLists.add(new DataModelAppointmentList(profilePic[0],"The Refined", "Haircut","October 19, 2021 / 9:30 am - 10:30 am","Completed"));
    }


    private void displayAppointmentMethod(){
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());

        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);

        isListContainMethod(appointmentLists);


    }

    public boolean isListContainMethod(ArrayList<DataModelAppointmentList> list) {
        buildListData();
        adapter2 = new MyAdapterAppointmentList(approvedLists);
        adapter3 = new MyAdapterAppointmentList(pendingLists);
        adapter4 = new MyAdapterAppointmentList(completedLists);
        for (DataModelAppointmentList item : list) {
            if (item.getStatus().contains("Approved")) {
                approvedLists.add(item);
                recyclerView1.setAdapter(adapter2);
            }
            else if (item.getStatus().contains("Pending")) {
                pendingLists.add(item);
                recyclerView2.setAdapter(adapter3);
            }
            else if (item.getStatus().contains("Completed")) {
                completedLists.add(item);
                recyclerView3.setAdapter(adapter4);
            }
        }
        return true;
    }

    private void searchAppointment(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<DataModelAppointmentList> filteredList1 = new ArrayList<>();
        for (DataModelAppointmentList item : appointmentLists) {
            if (item.getBarbershopName().toLowerCase().contains(text.toLowerCase())) {
                filteredList1.add(item);
            }
        }
        adapter1.filterList(filteredList1);
    }
}