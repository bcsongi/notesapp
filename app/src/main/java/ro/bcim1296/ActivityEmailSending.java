package ro.bcim1296;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ro.bcim1296.fragment.NoteDetailsFragment;
import ro.bcim1296.model.Note;

public class ActivityEmailSending extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // UI bealliatsa
        setContentView(R.layout.activity_details);

        // ha kaptunk parametereket es megvan ,hogy melyik fragment-el dolgozunk
  /*      if (savedInstanceState == null && getIntent().getExtras() != null) {
            // lekerjuk a parameterkent megadott peldanyt
            Bundle args = new Bundle(getIntent().getExtras());
            NoteDetailsFragment detailsFragment = NoteDetailsFragment.newInstance(args);

            Note note = detailsFragment.getSelectedNote();

        }
 */   }

}
