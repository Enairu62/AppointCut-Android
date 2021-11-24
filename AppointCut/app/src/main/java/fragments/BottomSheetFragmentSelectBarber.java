package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import DataModels.DataModelSelectBarber;
import com.example.appointcut.models.ShopService;
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

    RecyclerView recyclerView;
    private ArrayList<DataModelSelectBarber> list = new ArrayList<>();
    private ShopService shopService;

    public BottomSheetFragmentSelectBarber() {
        // Required empty public constructor
    }

    /**
     * Creates a new Select Barber Fragment with a given Shop Service
     * @param shopService Shop Service chosen by the user
     */
    public BottomSheetFragmentSelectBarber(ShopService shopService){
        this.shopService = shopService;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BottomSheetFragmentSelectBarber.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetFragmentSelectBarber newInstance() {
        BottomSheetFragmentSelectBarber fragment = new BottomSheetFragmentSelectBarber();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bottom_sheet_select_barber, container, false);
        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        Button btnNext = (Button) view.findViewById(R.id.btnNext);
        Button btn = (Button) view.findViewById(R.id.button);

        BottomSheetDialogFragment  bottomSheetFragmentSelectServices = new BottomSheetFragmentSelectServices();
        BottomSheetDialogFragment  bottomSheetFragmentSelectSchedule = new BottomSheetFragmentSelectSchedule();

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
                dismiss();
                bottomSheetFragmentSelectSchedule.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectSchedule.getTag());
            }
        });
        //change category text
        btn.setText(shopService.getName());


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterSelectBarber adapter = new MyAdapterSelectBarber(list);
        recyclerView.setAdapter(adapter);
        buildListData();
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
    public void onItemClick(DataModelSelectBarber dataModelSelectBarber) {
        /*Fragment fragment =  FragmentHairCompleteInfo.newInstance(dataModelHair.getImage(), dataModelHair.getTitle(), dataModelHair.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }
}
