package fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appointcut.R;
import com.example.appointcut.SignUp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfileCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfileCustomer extends Fragment {

    Button editProfile, saveProfile, cancel;
    EditText firstName, lastName, email, contact, pass, confirm;
    TextView textViewConfirm;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;

    public FragmentProfileCustomer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfileCustomer newInstance() {
        FragmentProfileCustomer fragment = new FragmentProfileCustomer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DrawerLayout drawerLayoutCustomer = getActivity().findViewById(R.id.drawerLayoutCustomer);
                BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
                NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
                if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                    drawerLayoutCustomer.closeDrawer(GravityCompat.START);
                }
                else {
                    navController.navigate(R.id.action_fragmentProfileCustomer_to_fragmentFindBarberShop);
                    bottomNavCustomer.setVisibility(View.VISIBLE);
                    bottomNavCustomer.getMenu().setGroupCheckable(0, true, true);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_customer, container, false);
        BottomNavigationView bottomNavCustomer = (BottomNavigationView) getActivity().findViewById(R.id.bottomNavCustomer);
        bottomNavCustomer.setVisibility(View.GONE);

        firstName = (EditText) view.findViewById(R.id.editFirstName);
        lastName = (EditText) view.findViewById(R.id.editLastName);
        email = (EditText) view.findViewById(R.id.editEmail);
        contact = (EditText) view.findViewById(R.id.editContact);
        pass = (EditText) view.findViewById(R.id.editPass);
        confirm = (EditText) view.findViewById(R.id.editConfirm);
        textViewConfirm = (TextView) view.findViewById(R.id.textViewConfirm);

        Bundle customerProfile = SignUp.passDataIntoProfile();
        mParam1 = customerProfile.getString("profileFirstName");
        mParam2 = customerProfile.getString("profileLastName");
        mParam3 = customerProfile.getString("profileEmail");
        mParam4 = customerProfile.getString("profileContact");
        mParam5 = customerProfile.getString("profilePass");
        firstName.setText(mParam1);
        lastName.setText(mParam2);
        email.setText(mParam3);
        contact.setText(mParam4);
        pass.setText(mParam5);

        editProfile = (Button) view.findViewById(R.id.btnEditProfile);
        saveProfile = (Button) view.findViewById(R.id.btnSaveProfile);
        cancel = (Button) view.findViewById(R.id.btnCancel);

        buttonMethods();
        return view;
    }

    private void buttonMethods(){
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName.setEnabled(true);
                lastName.setEnabled(true);
                email.setEnabled(true);
                contact.setEnabled(true);
                pass.setEnabled(true);
                pass.setText("");
                textViewConfirm.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
                confirm.setEnabled(true);
                saveProfile.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.INVISIBLE);
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                contact.setEnabled(false);
                pass.setEnabled(false);
                confirm.setEnabled(false);
                textViewConfirm.setVisibility(View.INVISIBLE);
                confirm.setVisibility(View.INVISIBLE);
                saveProfile.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                editProfile.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                contact.setEnabled(false);
                pass.setEnabled(false);
                confirm.setEnabled(false);
                textViewConfirm.setVisibility(View.INVISIBLE);
                confirm.setVisibility(View.INVISIBLE);
                saveProfile.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                editProfile.setVisibility(View.VISIBLE);
            }
        });

    }
}