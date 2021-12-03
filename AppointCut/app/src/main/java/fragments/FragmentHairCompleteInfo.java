package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import online.appointcut.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHairCompleteInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHairCompleteInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int ARG_PARAM1 = 0;
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private String mParam3;


    public FragmentHairCompleteInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHairCompleteInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHairCompleteInfo newInstance(int param1, String param2, String param3) {
        FragmentHairCompleteInfo fragment = new FragmentHairCompleteInfo();
        Bundle args = new Bundle();
        args.putInt(String.valueOf(ARG_PARAM1), param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(String.valueOf(ARG_PARAM1));
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hair_complete_info, container, false);
        ImageView myImages = view.findViewById(R.id.myImages);
        TextView titleItems = view.findViewById(R.id.titleItems);
        TextView descriptionItems = view.findViewById(R.id.descriptionItems);
        myImages.setImageResource(mParam1);
        titleItems.setText(mParam2);
        descriptionItems.setText(mParam3);
        return view;
    }

}