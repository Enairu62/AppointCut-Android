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

import com.example.appointcut.DataModel;
import com.example.appointcut.MyAdapterHairRow;
import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHairStyle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHairStyle extends Fragment implements MyAdapterHairRow.ItemClickListener{

    RecyclerView recyclerView;
    private ArrayList<DataModel> list = new ArrayList<>();
    int images[] = {R.drawable.ic_hairtrendicon,R.drawable.ic_hairtrendicon, R.drawable.ic_hairtrendicon,
            R.drawable.ic_hairtrendicon,R.drawable.ic_hairtrendicon,R.drawable.ic_hairtrendicon};

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
        MyAdapterHairRow adapter = new MyAdapterHairRow(list,this);
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
        list.add(new DataModel(images[0],"Hairstyle 1", "style1"));
        list.add(new DataModel(images[1],"Hairstyle 2", "style2"));
        list.add(new DataModel(images[2],"Hairstyle 3" , "style3"));
        list.add(new DataModel(images[3],"Hairstyle 4" , "style4"));
        list.add(new DataModel(images[4],"Hairstyle 5" ,"style5"));
        list.add(new DataModel(images[5],"Hairstyle 6" ,"style6"));
    }

    @Override
    public void onItemClick(DataModel dataModel) {
        Fragment fragment =  FragmentHairCompleteInfo.newInstance(dataModel.getImage(), dataModel.getTitle(), dataModel.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}