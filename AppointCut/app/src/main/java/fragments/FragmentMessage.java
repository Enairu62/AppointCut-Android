package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import DataModels.DataModelMessages;
import MyAdapters.MyAdapterMessage;
import com.example.appointcut.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMessage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMessage extends Fragment implements MyAdapterMessage.ItemClickListener{

    private ArrayList<DataModelMessages> list = new ArrayList<>();

    public FragmentMessage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentMessage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMessage newInstance() {
        FragmentMessage fragment = new FragmentMessage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutCustomer = getActivity().findViewById(R.id.drawerLayoutCustomer);
                NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
                if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutCustomer.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.navigate(R.id.action_fragmentMessage_to_fragmentFindBarberShop);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_message, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterMessage adapter = new MyAdapterMessage(list,this);
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
        int profilePic[] = {R.drawable.sampleprofilepic_bolima,R.drawable.sampleprofilepic_leila,R.drawable.sampleprofilepic_arthur, R.drawable.sampleprofilepic_miguel,R.drawable.sampleprofilepic_carlex};

        list.add(new DataModelMessages(profilePic[0],"Brent Jansen P. Bolima", "Hello. How are you today?", "15 mins"));
        list.add(new DataModelMessages(profilePic[1],"Leila Bianca Campos", "Magandang umaga sa iyo!", "9:37 am"));
        list.add(new DataModelMessages(profilePic[2],"Arthur Allen Castillo", "Have a pleasant day!", "7:01 am"));
        list.add(new DataModelMessages(profilePic[3],"Raymond Miguel Gonzalez", "Can I ask you a favor?", "11:23 pm"));
        list.add(new DataModelMessages(profilePic[4],"Carlex Rol Jalmasco" ,"Congratulations! BCCG passed Capstone 2", "8:52 pm" ));
    }

    public void onItemClick(DataModelMessages dataModelMessages) {
        NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
        navController.navigate(R.id.action_fragmentMessage_to_fragmentChat);
    }

}