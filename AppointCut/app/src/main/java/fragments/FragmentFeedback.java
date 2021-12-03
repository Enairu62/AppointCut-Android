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

import DataModels.DataModelFeedback;
import MyAdapters.MyAdapterFeedback;
import online.appointcut.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFeedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFeedback extends Fragment {

    private ArrayList<DataModelFeedback> arrayFeedBack = new ArrayList<>();

    public FragmentFeedback() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFeedback newInstance() {
        FragmentFeedback fragment = new FragmentFeedback();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawerLayout drawerLayoutBarber = getActivity().findViewById(R.id.drawerLayoutBarber);
        NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayoutBarber.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutBarber.closeDrawer(GravityCompat.START);
                }
               else {
                    navController.navigate(R.id.action_fragmentFeedback_to_fragmentSchedule);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feedback, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.approvedRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterFeedback adapter = new MyAdapterFeedback(arrayFeedBack);
        recyclerView.setAdapter(adapter);
        buildListData();
        return view;
    }

    private void buildListData(){
        arrayFeedBack.add(new DataModelFeedback("October 24, 2021","Brent Jansen Bolima",5.0f,"Very Good!"));
        arrayFeedBack.add(new DataModelFeedback("October 25, 2021","Leila Campos",3.5f,"I am satisfied with the outcome."));
        arrayFeedBack.add(new DataModelFeedback("October 26, 2021","Arthur Allen Castillo",2.5f,"So disappointing!"));
        arrayFeedBack.add(new DataModelFeedback("October 27, 2021","Raymond Miguel Gonzalez",4.5f,"Excellent services" ));
        arrayFeedBack.add(new DataModelFeedback("October 28, 2021","Carlex Rol Jalmasco",2.0f,"I will not recommend this barber to others."));
        arrayFeedBack.add(new DataModelFeedback("October 29, 2021","Jay Rico",3.0f,"His service is fine."));
        arrayFeedBack.add(new DataModelFeedback("October 30, 2021","John Eric Macaraig",4.0f,"Great job."));
    }
}