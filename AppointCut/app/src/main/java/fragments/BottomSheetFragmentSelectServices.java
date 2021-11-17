package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appointcut.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import DataModels.DataModelSelectBarber;
import DataModels.DataModelSelectServices;
import MyAdapters.MyAdapterSelectBarber;
import MyAdapters.MyAdapterSelectService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentSelectServices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentSelectServices extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    private static MyAdapterSelectService adapter;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bottom_sheet_select_services, container, false);
        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        Button btnNext = (Button) view.findViewById(R.id.btnNext);
        BottomSheetDialogFragment bottomSheetFragmentSelectBarber = new BottomSheetFragmentSelectBarber();
        BottomSheetDialogFragment  bottomSheetFragmentAppointmentDetails = new BottomSheetFragmentAppointmentDetails();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getServiceName());
                        stringBuilder.append("\n");
                    }
                    showToast(stringBuilder.toString().trim());
                    dismiss();
                    bottomSheetFragmentSelectBarber.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectBarber.getTag());
                } else {
                    showToast("Please select a service!");
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getActivity(),3, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyAdapterSelectService(list);
        recyclerView.setAdapter(adapter);
        buildListData();
        return view;
    }


    private void buildListData(){
        int servicePic[] = {R.drawable.haircolor, R.drawable.haircut, R.drawable.hairtreatment,
                R.drawable.straight_hair};

        list.add(new DataModelSelectServices(servicePic[0],"Hair Color", 150.00f));
        list.add(new DataModelSelectServices(servicePic[1],"Haircut", 50.00f));
        list.add(new DataModelSelectServices(servicePic[2],"Hair Treatment", 250.00f));
        list.add(new DataModelSelectServices(servicePic[3],"Rebond", 50.00f));
        list.add(new DataModelSelectServices(servicePic[1],"Haircut" ,50.00f));
    }

    public static Bundle passDataIntoAppointment(){
        Bundle args = new Bundle();
        for (int i = 0; i < adapter.getSelected().size(); i++) {
            args.putString("serviceName", adapter.getSelected().get(i).getServiceName());
            args.putFloat("servicePrice", adapter.getSelected().get(i).getServicePrice());
        }
        return args;
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}