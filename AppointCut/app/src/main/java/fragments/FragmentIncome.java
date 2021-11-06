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

import DataModels.DataModelIncome;

import MyAdapters.MyAdapterIncome;
import com.example.appointcut.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIncome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIncome extends Fragment {

    private ArrayList<DataModelIncome> list = new ArrayList<>();

    public FragmentIncome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentReports.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIncome newInstance() {
        FragmentIncome fragment = new FragmentIncome();
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
                if (drawerLayoutBarber.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutBarber.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.navigate(R.id.action_fragmentIncome_to_fragmentSchedule);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterIncome adapter = new MyAdapterIncome(list);
        recyclerView.setAdapter(adapter);
        buildListData();
        return view;
    }
    private void buildListData(){
        list.add(new DataModelIncome("0ctober 24, 2021","Haircut",100.00,100.00, 100.00));
        list.add(new DataModelIncome("0ctober 25, 2021","Hair Color",200.00,200.00, 200.00));
        list.add(new DataModelIncome("0ctober 26, 2021","Haircut",100.00,100.00, 100.00));
        list.add(new DataModelIncome("0ctober 27, 2021","Haircut",150.00,150.00, 150.00));
        list.add(new DataModelIncome("0ctober 28, 2021","Haircut",150.00,150.00, 150.00));
        list.add(new DataModelIncome("0ctober 29, 2021","Haircut",100.00,100.00, 100.00));
        list.add(new DataModelIncome("0ctober 30, 2021","Hair Color",200.00,200.00, 200.00));

    }
}
