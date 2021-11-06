package fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import DataModels.DataModelSchedule;
import MyAdapters.MyAdapterSchedule;
import com.example.appointcut.R;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSchedule extends Fragment {

    private ArrayList<DataModelSchedule> list = new ArrayList<>();

    public FragmentSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSchedule newInstance() {
        FragmentSchedule fragment = new FragmentSchedule();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutBarber = getActivity().findViewById(R.id.drawerLayoutBarber);
                if(drawerLayoutBarber.isDrawerOpen(GravityCompat.START)){
                    drawerLayoutBarber.closeDrawer(GravityCompat.START);
                }
                else{
                    getActivity().moveTaskToBack(true);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_schedule, container, false);
        Button btnDateRange = (Button) view.findViewById(R.id.btnDateRange);

        /* starts before 6 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -6);

        /* ends after 6 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 6);


        // on below line we are setting up our horizontal calendar view and passing id our calendar view to it.
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                // on below line we are adding a range
                // as start date and end date to our calendar.
                .range(startDate, endDate)
                // on below line we are providing a number of dates
                // which will be visible on the screen at a time.
                .datesNumberOnScreen(7)
                .configure()    // starts configuration.
                .formatTopText("EEEEE")       // default to "MMM".
                .showBottomText(false)
                .colorTextTop(Color.GRAY, Color.WHITE)
                .colorTextMiddle(Color.BLACK, Color.WHITE)
                .colorTextBottom(Color.GRAY, Color.WHITE)
                .end()          // ends configuration.
                // at last we are calling a build method
                // to build our horizontal recycler view.
                .build();
                horizontalCalendar.refresh();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // on below line we are printing date
                // in the logcat which is selected.
                Log.e("TAG", "CURRENT DATE IS " + date);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterSchedule adapter = new MyAdapterSchedule(list);
        recyclerView.setAdapter(adapter);
        buildListData();
        return view;
    }

    private void buildListData(){
        list.add(new DataModelSchedule("9:00 am - 10:00 am","Brent Jansen P. Bolima","Haircut",100.00));
        list.add(new DataModelSchedule("10:00 am - 11:00 am","Arthur Allen Castillo","Haircut, Hair Color",200.00));
        list.add(new DataModelSchedule("2:00 pm - 3:00 pm","Raymond Miguel Gonzalez","Hair Color",100.00 ));
        list.add(new DataModelSchedule("3:30 pm - 4:00 pm","Leila Campos","Haircut",100.00));
        list.add(new DataModelSchedule("4:00 pm - 4:30 pm","Carlex Rol Jalmasco","Haircut",100.00));
        list.add(new DataModelSchedule("5:00 pm - 6:00 pm","Jay Rico","Haircut, Massage",300.00));
    }

}