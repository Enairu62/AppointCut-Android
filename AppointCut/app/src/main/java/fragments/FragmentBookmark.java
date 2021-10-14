package fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appointcut.DataModel;
import com.example.appointcut.MyAdapterHairRow;
import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBookmark#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBookmark extends Fragment implements MyAdapterHairRow.ItemClickListener {

    RecyclerView recyclerView1, recyclerView2;
    private ArrayList<DataModel> list1 = new ArrayList<>();
    private ArrayList<DataModel> list2 = new ArrayList<>();
    int images[] = {R.drawable.ic_hairtrendicon,R.drawable.ic_hairtrendicon, R.drawable.ic_hairtrendicon,
            R.drawable.ic_hairtrendicon,R.drawable.ic_hairtrendicon,R.drawable.ic_hairtrendicon};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBookmark() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBookmark.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBookmark newInstance(String param1, String param2) {
        FragmentBookmark fragment = new FragmentBookmark();
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
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        View view2 = getActivity().findViewById(R.id.constraintLayoutCustomer);
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) view2.findViewById(R.id.bottomNavCustomer);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);

       bottomNavCustomer.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavCustomer.getMenu().setGroupCheckable(0, true, true);
                switch (item.getItemId()) {
                    case R.id.bottomNavAR:
                        openFragment(FragmentCameraAR.newInstance("", ""));
                        bottomNavCustomer.setVisibility(View.GONE);
                        return true;
                    case R.id.bottomNavHairStyle:
                        openFragment(FragmentHairStyle.newInstance());
                        return true;
                    case R.id.bottomNavHairTrends:
                        openFragment(FragmentHairTrend.newInstance());
                        return true;
                    case R.id.bottomNavMessage:
                        openFragment(FragmentMessage.newInstance("", ""));
                        return true;
                }
                return false;
            }
        });

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        MyAdapterHairRow adapter1 = new MyAdapterHairRow(list1,this);
        MyAdapterHairRow adapter2 = new MyAdapterHairRow(list2,this);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);

        buildListData1();
        buildListData2();
        return view;
    }

    private void buildListData1(){
        list1.add(new DataModel(images[0],"Hairtrend 1", "trend1"));
        list1.add(new DataModel(images[1],"Hairtrend 2", "trend2"));
        list1.add(new DataModel(images[2],"Hairtrend 3" , "trend3"));
        list1.add(new DataModel(images[3],"Hairtrend 4" , "trend4"));
        list1.add(new DataModel(images[4],"Hairtrend 5" ,"trend5"));
        list1.add(new DataModel(images[5],"Hairtrend 6" ,"trend6"));
    }

    private void buildListData2(){
        list2.add(new DataModel(images[0],"Hairstyle 1", "style1"));
        list2.add(new DataModel(images[1],"Hairstyle 2", "style2"));
        list2.add(new DataModel(images[2],"Hairstyle 3" , "style3"));
        list2.add(new DataModel(images[3],"Hairstyle 4" , "style4"));
        list2.add(new DataModel(images[4],"Hairstyle 5" ,"style5"));
        list2.add(new DataModel(images[5],"Hairstyle 6" ,"style6"));
    }

    private void openFragment(Fragment fragment) {
        Log.d(TAG, "openFragment: ");
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClick(DataModel dataModel) {

    }
}