package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import online.appointcut.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import DataModels.DataModelSelectServices;
import MyAdapters.MyAdapterSelectService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentSelectServices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentSelectServices extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    private ArrayList<DataModelSelectServices> list = new ArrayList<>();

    public BottomSheetFragmentSelectServices() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BottomSheetFragmentSelectServices.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetFragmentSelectServices newInstance() {
        BottomSheetFragmentSelectServices fragment = new BottomSheetFragmentSelectServices();
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
        View view =  inflater.inflate(R.layout.fragment_bottom_sheet_select_services, container, false);
        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        BottomSheetDialogFragment bottomSheetFragmentSelectBarber = new BottomSheetFragmentSelectBarber();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        recyclerView = (RecyclerView) view.findViewById(R.id.approvedRecycler);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getActivity(),3, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        MyAdapterSelectService adapter = new MyAdapterSelectService(list);
        recyclerView.setAdapter(adapter);
        buildListData();
        return view;
    }

    private void buildListData(){
        //get the pics
        int servicePic[] = {R.drawable.haircolor, R.drawable.haircut, R.drawable.hairtreatment,
                R.drawable.straight_hair};

        //get the services from server


        //add to the list
        list.add(new DataModelSelectServices(servicePic[0],"Hair Color", 150.00f));
        list.add(new DataModelSelectServices(servicePic[1],"Haircut", 50.00f));
        list.add(new DataModelSelectServices(servicePic[2],"Hair Treatment", 250.00f));
        list.add(new DataModelSelectServices(servicePic[3],"Rebond", 50.00f));
        list.add(new DataModelSelectServices(servicePic[1],"Haircut" ,50.00f));
    }
}