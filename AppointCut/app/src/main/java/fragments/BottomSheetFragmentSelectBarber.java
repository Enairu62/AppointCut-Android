package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import DataModels.DataModelSelectBarber;
import MyAdapters.MyAdapterSelectBarber;
import com.example.appointcut.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentSelectBarber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentSelectBarber extends BottomSheetDialogFragment{


    private static MyAdapterSelectBarber adapter;
    RecyclerView recyclerView;
    private ArrayList<DataModelSelectBarber> list = new ArrayList<>();

    public BottomSheetFragmentSelectBarber() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    public static BottomSheetFragmentSelectBarber newInstance() {
        BottomSheetFragmentSelectBarber fragment = new BottomSheetFragmentSelectBarber();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bottom_sheet_select_barber, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapterSelectBarber(list);
        recyclerView.setAdapter(adapter);
        buildListData();

        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        Button btnNext = (Button) view.findViewById(R.id.btnNext);

        BottomSheetDialogFragment  bottomSheetFragmentSelectServices = new BottomSheetFragmentSelectServices();
        BottomSheetDialogFragment  bottomSheetFragmentSelectSchedule = new BottomSheetFragmentSelectSchedule();
        BottomSheetDialogFragment  bottomSheetFragmentAppointmentDetails = new BottomSheetFragmentAppointmentDetails();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
               bottomSheetFragmentSelectServices.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectServices.getTag());
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected() != null) {
                    dismiss();
                    bottomSheetFragmentSelectSchedule.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectSchedule.getTag());
                } else {
                    showToast("Please choose your barber!");
                }
            }
        });

        return view;
    }



    private void buildListData(){
        int profilePic[] = {R.drawable.sampleprofilepic_bolima,R.drawable.sampleprofilepic_leila,R.drawable.sampleprofilepic_arthur,
                R.drawable.sampleprofilepic_miguel,R.drawable.sampleprofilepic_carlex};

        list.add(new DataModelSelectBarber(profilePic[0],"Brent Jansen P. Bolima", "Haircut, Hair Color"));
        list.add(new DataModelSelectBarber(profilePic[1],"Leila Bianca Campos", "Haircut, Hair Color"));
        list.add(new DataModelSelectBarber(profilePic[2],"Arthur Allen Castillo", "Haircut"));
        list.add(new DataModelSelectBarber(profilePic[3],"Raymond Miguel Gonzalez", "Haircut"));
        list.add(new DataModelSelectBarber(profilePic[4],"Carlex Rol Jalmasco" ,"Haircut, Massage"));
    }

    public static Bundle passDataIntoAppointment(){
        Bundle args = new Bundle();
        args.putString("barberName", adapter.getSelected().getNamess());
        args.putString("barberSpecialty", adapter.getSelected().getSpecialty());
        args.putInt("barberPicture", adapter.getSelected().getProfilePic());
        return args;
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
