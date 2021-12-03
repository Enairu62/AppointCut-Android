package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import online.appointcut.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentSelectSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentSelectSchedule extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomSheetFragmentSelectSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomSheetFragmentSelectSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetFragmentSelectSchedule newInstance(String param1, String param2) {
        BottomSheetFragmentSelectSchedule fragment = new BottomSheetFragmentSelectSchedule();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bottom_sheet_select_schedule, container, false);
        Button btnBack = (Button) view.findViewById(R.id.btnBack);

        BottomSheetDialogFragment bottomSheetFragmentSelectBarber = new BottomSheetFragmentSelectBarber();
        BottomSheetDialogFragment bottomSheetFragmentAppointmentDetails = new BottomSheetFragmentAppointmentDetails();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                bottomSheetFragmentSelectBarber.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectBarber.getTag());
            }
        });


        return view;
    }
}