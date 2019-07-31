package ro.bcim1296.fragment;

import ro.bcim1296.R;
import ro.bcim1296.model.Note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteDetailsFragment extends Fragment {

	public static final String TAG = "NoteDetailsFragment";

	public static final String KEY_NOTE = "note";

	private TextView noteDescription;

	private static Note selectedNote;

    // az aktualis osztalyomrol uj pedanyt kuld vissza, es beallitja az argumentumokat
	public static NoteDetailsFragment newInstance(String noteDesc) {
		NoteDetailsFragment result = new NoteDetailsFragment();

		Bundle args = new Bundle();
		// string tipusu adat helyez el
        args.putString(KEY_NOTE, noteDesc);
		result.setArguments(args);

		return result;
	}

    public static NoteDetailsFragment newInstance(Note note) {
		NoteDetailsFragment result = new NoteDetailsFragment();

		Bundle args = new Bundle();
        // parce tipusu adat helyez el (hasonlo a serializalt objektumokhoz)
		args.putParcelable(KEY_NOTE, note);
		result.setArguments(args);

		return result;
	}

    public Note getSelectedNote() {
        return selectedNote;
    }

	public static NoteDetailsFragment newInstance(Bundle args) {
		NoteDetailsFragment result = new NoteDetailsFragment();

		result.setArguments(args);

		return result;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			if (getArguments() != null) {
	            // lekeri az aktualis elmunket
				selectedNote = getArguments().getParcelable(KEY_NOTE);
			}
		}
	}

    // a nezet kirajzolasakor hivodik meg
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// megondjuk a layout inflaternek ,hogy melyik osztalybol peldanyositsa magat
        View root = inflater.inflate(R.layout.fragment_note_details, container, false);
		
		noteDescription = (TextView) root.findViewById(R.id.noteDescription);
        // a kivalasztott elem reszleteinek kiirasa kiirasa
		noteDescription.setText(Html.fromHtml("<h3>" + selectedNote.getTitle() + "</h3>"
                + "<h2> Latitude: " + selectedNote.getLatitude() + "</h2>"
                + "<h2> Longitude: " + selectedNote.getLongitude() + "</h2>"
                + selectedNote.getDescription()));
		return root;
	}
}
