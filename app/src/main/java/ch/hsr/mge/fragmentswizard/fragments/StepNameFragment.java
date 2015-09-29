package ch.hsr.mge.fragmentswizard.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ch.hsr.mge.fragmentswizard.R;

public class StepNameFragment extends Fragment {

    public interface OnUsernameChangeListener {
        void onUsernameChanged(String userName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_name, container, false);
        root.findViewById(R.id.nextButton).setOnClickListener((View.OnClickListener) getActivity());
        EditText name = (EditText) root.findViewById(R.id.nameEditText);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((OnUsernameChangeListener) getActivity()).onUsernameChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof View.OnClickListener)) {
            throw new AssertionError("Activity must implement View.OnClickListener!");
        }
        if (!(activity instanceof OnUsernameChangeListener)) {
            throw new AssertionError("Activity must implement OnUsernameChangeListener!");
        }
    }
}
