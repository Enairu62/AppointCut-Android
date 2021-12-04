package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DataModels.DataModelHair;
import MyAdapters.MyAdapterHair;
import online.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHairTrend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHairTrend extends Fragment implements MyAdapterHair.ItemClickListener{

    RecyclerView recyclerView;
    private ArrayList<DataModelHair> list = new ArrayList<>();


    public FragmentHairTrend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentHairTrend.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHairTrend newInstance() {
        FragmentHairTrend fragment = new FragmentHairTrend();
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
        View view =  inflater.inflate(R.layout.fragment_hair_trend, container, false);
        View view2 = getActivity().findViewById(R.id.constraintLayoutCustomer);
      BottomNavigationView bottomNavCustomer = (BottomNavigationView) view2.findViewById(R.id.bottomNavCustomer);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.approvedRecycler);

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
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void buildListData(){
        int images[] = {R.drawable.high_and_tight_haircut,R.drawable.spiky_hair,R.drawable.taper_fade_with_slick_back,
                R.drawable.undercut,R.drawable.low_fade,R.drawable.buzz_cut};

        list.add(new DataModelHair(images[0],"High and Tight", getString(R.string.high_and_tight)));
        list.add(new DataModelHair(images[1],"Spiky Hair", getString(R.string.spiky_hair)));
        list.add(new DataModelHair(images[2],"Taper Fade with Slick Back", getString(R.string.taper_fade)));
        list.add(new DataModelHair(images[3],"Undercut", getString(R.string.undercut)));
        list.add(new DataModelHair(images[4],"Low Fade" ,getString(R.string.low_fade) ));
        list.add(new DataModelHair(images[5],"Buzz Cut" ,getString(R.string.buzz_cut)));
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