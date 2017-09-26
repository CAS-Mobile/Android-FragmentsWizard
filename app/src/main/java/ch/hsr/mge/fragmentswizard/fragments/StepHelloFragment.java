package ch.hsr.mge.fragmentswizard.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.fragmentswizard.R;

public class StepHelloFragment extends Fragment implements View.OnClickListener {

    private View.OnClickListener activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_hello, container, false);
        root.findViewById(R.id.nextButton).setOnClickListener(this);
        return root;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (activity instanceof View.OnClickListener) {
            this.activity = (View.OnClickListener) activity;
        } else {
            throw new AssertionError("Activity must implement View.OnClickListener!");
        }
    }

    @Override
    public void onClick(View view) {
        activity.onClick(view);
    }
}
