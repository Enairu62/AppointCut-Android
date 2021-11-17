package fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appointcut.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentAppointmentDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentAppointmentDetails extends BottomSheetDialogFragment {

    private String serviceName;
    private float servicePrice;
    private String barberName;
    private String barberSpecialty;
    private int barberPicture;

    TextView txtBarberName, txtBarberSpecialty;
    ImageView imageViewBarberPicture;
    Button btnBookNow;
    ImageButton btnEditService, btnEditBarber;

    public BottomSheetFragmentAppointmentDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BottomSheetFragmentAppointmentDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetFragmentAppointmentDetails newInstance() {
        BottomSheetFragmentAppointmentDetails fragment = new BottomSheetFragmentAppointmentDetails();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.disableDrag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_appointment_details, container, false);
        btnBookNow = (Button) view.findViewById(R.id.btnBookNowXML);
        btnEditBarber = (ImageButton) view.findViewById(R.id.btnEditBarberXML);
        btnEditService = (ImageButton) view.findViewById(R.id.btnEditServiceXML);
        txtBarberName = (TextView) view.findViewById(R.id.txtBarberNameXML);
        txtBarberSpecialty = (TextView) view.findViewById(R.id.txtBarberSpecialtyXML);
        imageViewBarberPicture = (ImageView) view.findViewById(R.id.imageBarberXML);

        bundleMethods();
        buttonMethods();
        return view;
    }


    private void openDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_appointment_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels* 0.90);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void bundleMethods(){
        Bundle service = BottomSheetFragmentSelectServices.passDataIntoAppointment();

        serviceName = service.getString("serviceName");
       /* ArrayList<String> list = new ArrayList<>();
        for( int x = 0; x < 10; x++ )
        {
            list.add(serviceName);
        }

        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayoutService);
        for( int i = 0; i < 5; i++ )
        {
            TextView textView = new TextView(getContext());
            textView.setText(serviceName);
            constraintLayout.addView(textView);
        }*/

        Bundle barber = BottomSheetFragmentSelectBarber.passDataIntoAppointment();

        barberName = barber.getString("barberName");
        barberSpecialty = barber.getString("barberSpecialty");
        barberPicture = barber.getInt("barberPicture");
        txtBarberName.setText(barberName);
        txtBarberSpecialty.setText(barberSpecialty);
        imageViewBarberPicture.setImageResource(barberPicture);
    }

    private void buttonMethods(){
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                openDialog();
            }
        });
        btnEditBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment  bottomSheetFragmentSelectBarber = new BottomSheetFragmentSelectBarber();
                bottomSheetFragmentSelectBarber.show(getActivity().getSupportFragmentManager(), bottomSheetFragmentSelectBarber.getTag());
            }
        });
    }
}