package ro.bcim1296;

import ro.bcim1296.fragment.NoteListFragment;
import ro.bcim1296.model.Note;
import ro.bcim1296.fragment.NoteDetailsFragment;
import ro.bcim1296.fragment.NoteListFragment.INoteListFragment;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class ActivityMain extends FragmentActivity implements INoteListFragment, NoteListFragment.INoteEmailFragment {

	private ViewGroup fragmentContainer;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // UI bealliatsa
        setContentView(ro.bcim1296.R.layout.activity_main);

        // a  fragmentConteiner referenciank null erteket kap ,ha nem a large activity main hivodik meg
        // tehat ,ha nem tabletet vagy vmi mas nagy felbontasu keszuleket hasznal
        fragmentContainer = (ViewGroup) findViewById(ro.bcim1296.R.id.FragmentContainer);
	}

	// INoteListFragment
    public void onNoteSelected(Note selectedNote) {
        // tablet eseten
        if (fragmentContainer != null) {
			FragmentManager fm = getSupportFragmentManager();

            // fragmens modositasanal mindig transation-t hasznalunk
			FragmentTransaction ft = fm.beginTransaction();

			// lecsereljuk a fragment tartalmat, a kivalasztott elemere
            ft.replace(ro.bcim1296.R.id.FragmentContainer, NoteDetailsFragment.newInstance(selectedNote));
			ft.commit();
        } else {
        // mobil telefon eseten
            Intent i = new Intent(this, ActivityNoteDetails.class);
			i.putExtra(NoteDetailsFragment.KEY_NOTE, selectedNote);
			startActivity(i);
		}
	}


    public void onEmailSelected(Note selectedNote) {
        Intent i = new Intent(this, ActivityEmailSending.class);
        i.putExtra(NoteDetailsFragment.KEY_NOTE, selectedNote);
        startActivity(i);
    }

    public LocationManager getLM() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        return lm;
    }


}