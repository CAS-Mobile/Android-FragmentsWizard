package ch.hsr.mge.fragmentswizard.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hsr.mge.fragmentswizard.Constants;
import ch.hsr.mge.fragmentswizard.R;
import ch.hsr.mge.fragmentswizard.UserRegistrationData;

public class StepDoneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_done, container, false);
        root.findViewById(R.id.nextButton).setOnClickListener((View.OnClickListener) getActivity());

        TextView thanks = (TextView) root.findViewById(R.id.thanksTextView);

        UserRegistrationData data = (UserRegistrationData) getArguments().getSerializable(Constants.REGISTRATION_DATA);

        String text;
        if (data.isNewsletter()) {
            text = "Sie sind angemeldet, " + data.getName() + ", und werden unseren Newsletter bald erhalten!";
        } else {
            text = "Sie sind angemeldet, " + data.getName() + ", leider ohne Newsletter!";
        }
        thanks.setText(text);
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof View.OnClickListener)) {
            throw new AssertionError("Activity must implement View.OnClickListener!");
        }
    }
}
