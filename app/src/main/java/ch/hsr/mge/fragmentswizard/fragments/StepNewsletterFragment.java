package ch.hsr.mge.fragmentswizard.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import ch.hsr.mge.fragmentswizard.Constants;
import ch.hsr.mge.fragmentswizard.R;
import ch.hsr.mge.fragmentswizard.UserRegistrationData;

public class StepNewsletterFragment extends Fragment implements View.OnClickListener {

    private Switch newsletter;

    public interface OnNewsletterSubscription {
        void onNewsletterSubscribed(boolean subscribed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_newsletter, container, false);
        root.findViewById(R.id.nextButton).setOnClickListener(this);

        newsletter = (Switch) root.findViewById(R.id.newsletterSwitch);

        UserRegistrationData data = (UserRegistrationData) getArguments().getSerializable(Constants.REGISTRATION_DATA);

        newsletter.setChecked(data.isNewsletter());

        TextView question = (TextView) root.findViewById(R.id.newsletterTextView);
        question.setText(data.getName() + ", möchten Sie unseren Newsletter abonnieren?");

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof View.OnClickListener)) {
            throw new AssertionError("Activity must implement View.OnClickListener!");
        }
        if (!(activity instanceof OnNewsletterSubscription)) {
            throw new AssertionError("Activity must implement OnNewsletterSubscription!");
        }
    }

    @Override
    public void onClick(View v) {
        boolean wantsNewsletter = newsletter.isChecked();
        ((OnNewsletterSubscription) getActivity()).onNewsletterSubscribed(wantsNewsletter);
        ((View.OnClickListener) getActivity()).onClick(v);
    }
}
