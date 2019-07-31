package ro.bcim1296;

import ro.bcim1296.fragment.NoteDetailsFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityNoteDetails extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // UI bealliatsa
		setContentView(ro.bcim1296.R.layout.activity_details);

        // ha kaptunk parametereket es megvan ,hogy melyik fragment-el dolgozunk
		if (savedInstanceState == null && getIntent().getExtras() != null) {
            // lekerjuk a parameterkent megadott peldanyt
			Bundle args = new Bundle(getIntent().getExtras());
			NoteDetailsFragment detailsFragment = NoteDetailsFragment.newInstance(args);
			
			// Add details fragment
			FragmentManager fm = getSupportFragmentManager();
			
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(ro.bcim1296.R.id.FragmentContainer, detailsFragment);
			ft.commit();
		}
	}

}
