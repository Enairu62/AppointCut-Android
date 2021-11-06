package fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DataModels.DataModelHair;
import MyAdapters.MyAdapterHair;
import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHairStyle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHairStyle extends Fragment implements MyAdapterHair.ItemClickListener{

    RecyclerView recyclerView;
    private ArrayList<DataModelHair> list = new ArrayList<>();


    public FragmentHairStyle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentHairStyle.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHairStyle newInstance() {
        FragmentHairStyle fragment = new FragmentHairStyle();
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
        View view =  inflater.inflate(R.layout.fragment_hair_style, container, false);
        View view2 = getActivity().findViewById(R.id.constraintLayoutCustomer);
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) view2.findViewById(R.id.bottomNavCustomer);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        FloatingActionButton search = (FloatingActionButton) view.findViewById(R.id.floatSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentFindBarberShop.newInstance());
                bottomNavCustomer.getMenu().setGroupCheckable(0, false, true);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterHair adapter = new MyAdapterHair(list,this);
        recyclerView.setAdapter(adapter);
        buildListData();

        return view;
    }


    private void openFragment(Fragment fragment) {
        Log.d(TAG, "openFragment: ");
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void buildListData(){
        int images[] = {R.drawable.part_hair,R.drawable.short_hair, R.drawable.short_curly_hair,
                R.drawable.braid_hair,R.drawable.perm_hair,R.drawable.straight_hair};

        list.add(new DataModelHair(images[0],"How To Part Hair", getString(R.string.how_to_part_hair)));
        list.add(new DataModelHair(images[1],"How To Style Short Hair", getString(R.string.how_to_style_short_hair)));
        list.add(new DataModelHair(images[2],"How To Style Short Curly Hair" , getString(R.string.how_to_style_short_curly_hair)));
        list.add(new DataModelHair(images[3],"How To Braid Hair" , getString(R.string.how_to_braid_hair)));
        list.add(new DataModelHair(images[4],"How To Perm Hair" ,getString(R.string.how_to_perm_hair)));
        list.add(new DataModelHair(images[5],"How To Straighten Your Hair" ,getString(R.string.how_to_straighten_hair)));
    }

    @Override
    public void onItemClick(DataModelHair dataModelHair) {
        Fragment fragment =  FragmentHairCompleteInfo.newInstance(dataModelHair.getImage(), dataModelHair.getTitle(), dataModelHair.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}