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
import com.example.appointcut.MyAdapterBarberShopRow;
import com.example.appointcut.MyAdapterHairRow;
import com.example.appointcut.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFindBarberShop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFindBarberShop extends Fragment implements MyAdapterBarberShopRow.ItemClickListener{

    RecyclerView recyclerView;
    private ArrayList<DataModel> list = new ArrayList<>();

    int images[] = {R.drawable.ic_work,R.drawable.ic_work,R.drawable.ic_work,
            R.drawable.ic_work, R.drawable.ic_work, R.drawable.ic_work,};

    public FragmentFindBarberShop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentFindBarberShop.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFindBarberShop newInstance() {
        FragmentFindBarberShop fragment = new FragmentFindBarberShop();
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
        View view = inflater.inflate(R.layout.fragment_find_barber_shop, container, false);
        View view2 = getActivity().findViewById(R.id.constraintLayoutCustomer);
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) view2.findViewById(R.id.bottomNavCustomer);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        /*bottomNavCustomer.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavCustomer.getMenu().setGroupCheckable(0, true, true);
                switch (item.getItemId()) {
                    case R.id.bottomNavAR:
                        openFragment(FragmentCameraAR.newInstance("",""));
                        bottomNavCustomer.setVisibility(View.GONE);
                        return true;
                    case R.id.bottomNavHairStyle:
                        openFragment(FragmentHairStyle.newInstance());
                        return true;
                    case R.id.bottomNavHairTrends:
                        openFragment(FragmentHairTrend.newInstance());
                        return true;
                    case R.id.bottomNavMessage:
                        openFragment(FragmentMessage.newInstance("",""));
                        return true;
                }
                return false;
            }
        });*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterBarberShopRow adapter = new MyAdapterBarberShopRow(list,this);
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
        list.add(new DataModel(images[0],"BarberShop 1", "shop1"));
        list.add(new DataModel(images[1],"BarberShop 2", "shop2"));
        list.add(new DataModel(images[2],"BarberShop 3" , "shop3"));
        list.add(new DataModel(images[3],"BarberShop 4" , "shop4"));
        list.add(new DataModel(images[4],"BarberShop 5" ,"shop5"));
        list.add(new DataModel(images[5],"BarberShop 6" ,"shop6"));
    }

    @Override
    public void onItemClick(DataModel dataModel) {
        /*Fragment fragment =  FragmentHairCompleteInfo.newInstance(dataModel.getImage(), dataModel.getTitle(), dataModel.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }
}