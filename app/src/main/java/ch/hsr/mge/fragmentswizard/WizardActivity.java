package ch.hsr.mge.fragmentswizard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Stack;

import ch.hsr.mge.fragmentswizard.fragments.StepDoneFragment;
import ch.hsr.mge.fragmentswizard.fragments.StepHelloFragment;
import ch.hsr.mge.fragmentswizard.fragments.StepNameFragment;
import ch.hsr.mge.fragmentswizard.fragments.StepNewsletterFragment;
import ch.hsr.mge.fragmentswizard.fragments.StepSubscribedFragment;

public class WizardActivity extends AppCompatActivity implements View.OnClickListener, StepNameFragment.OnUsernameChangeListener, StepNewsletterFragment.OnNewsletterSubscription {

    /**
     * Pages enumeriert alle Wizard-Pages, damit wir uns den aktuellen Zustand merken können.
     * */
    enum Pages {HELLO, NAME, NEWSLETTER, THANKS, DONE}

    /**
     * Wegen des Back-Buttons müssen wir den Stack den wir mit den Activities automatisch bekommen
     * sozusagen nachbauen. Beim Aufruf einer neuen Wizard-Seite wird diese auf dem Stack abgelegt,
     * und beim Back-Button wieder entfernt.
     * */
    private Stack<Pages> pages = new Stack<>();

    private UserRegistrationData userRegistrationData;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        userRegistrationData = new UserRegistrationData();
        fragmentManager = getFragmentManager();

        // Wir starten mit dem StepHelloFragment
        pages.push(Pages.HELLO);
        switchTo(new StepHelloFragment());
    }

    @Override
    public void onClick(View v) {
        switch (pages.peek()) {
            case HELLO:
                pages.push(Pages.NAME);
                switchTo(new StepNameFragment());
                break;
            case NAME:
                pages.push(Pages.NEWSLETTER);
                switchTo(new StepNewsletterFragment());
                break;
            case NEWSLETTER:
                pages.push(Pages.THANKS);
                if (userRegistrationData.isNewsletter()) {
                    switchTo(new StepSubscribedFragment());
                } else {
                    switchTo(new StepDoneFragment());
                }
                break;
            case THANKS:
                pages.push(Pages.DONE);
                switchTo(new StepDoneFragment());
                break;
            case DONE:
                pages.clear();
                pages.push(Pages.HELLO);
                // Die Registration ist fertig, für den naechsten Benutzer erstellen wir ein
                // neues Datenobjekt.
                userRegistrationData = new UserRegistrationData();
                switchTo(new StepHelloFragment());
                break;
        }
    }

    private void switchTo(Fragment fragment) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.REGISTRATION_DATA, userRegistrationData);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {
        // Wenn der Stack leer wird will der Benutzer die App wohl beenden, dies erreichen wir
        // durch ein finishen der Activity
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            pages.pop();
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onUsernameChanged(String userName) {
        userRegistrationData.setName(userName);
    }

    @Override
    public void onNewsletterSubscribed(boolean subscribed) {
        userRegistrationData.setNewsletter(subscribed);
    }
}
