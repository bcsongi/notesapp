package ro.bcim1296.fragment;

import ro.bcim1296.R;
import ro.bcim1296.model.Note;
import ro.bcim1296.fragment.DatePickerDialogFragment.IDatePickerDialogFragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NoteCreateFragment extends DialogFragment implements
		IDatePickerDialogFragment {

	// Log tag
	public static final String TAG = "NoteCreateFragment";

	// UI
	private EditText editNoteTitle;
	private Spinner spnrNotePriority;
	private TextView txtDueDate;
	private EditText editNoteDescription;

	// Listener - amivel visszaszolunk a NoteListFragment-nek
	private INoteCreateFragment listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (getTargetFragment() != null) {
			try {
				listener = (INoteCreateFragment) getTargetFragment();
			} catch (ClassCastException ce) { // ha nem implementalna az interfacet
                ce.printStackTrace();
            }
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.createnote, container, false);

		// Dialog cimenek beallitasa
		getDialog().setTitle(R.string.itemCreatenote);

		// UI elem referenciak elkerese
		editNoteTitle = (EditText) root.findViewById(R.id.noteTitle);

		spnrNotePriority = (Spinner) root.findViewById(R.id.notePriority);
		String[] priorities = new String[3];
		priorities[0] = "Low";
		priorities[1] = "Medium";
		priorities[2] = "High";
		spnrNotePriority.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, priorities));

		txtDueDate = (TextView) root.findViewById(R.id.noteDueDate);
		txtDueDate.setText("  -  ");
		txtDueDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});

		editNoteDescription = (EditText) root
				.findViewById(R.id.noteDescription);

		// A gombok esemenykezeloinek beallitasa
		Button btnOk = (Button) root.findViewById(R.id.btnCreatenote);
		btnOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Note.Priority selectedPriority = Note.Priority.LOW;

				switch (spnrNotePriority.getSelectedItemPosition()) {
				case 0:
					selectedPriority = Note.Priority.LOW;
					break;
				case 1:
					selectedPriority = Note.Priority.MEDIUM;
					break;
				case 2:
					selectedPriority = Note.Priority.HIGH;
					break;
				default:
					break;
				}

				if (listener != null) {
                    // ertesitjuk a ListFragment-et hogy szurja be az uj elemet
					listener.onNoteCreated(new Note(editNoteTitle.getText()
							.toString(), selectedPriority, txtDueDate.getText()
							.toString(), editNoteDescription.getText()
							.toString(), 1d, 1d));
				}
                // finish es bezar activity
				dismiss();
			}
		});

		Button btnCancel = (Button) root.findViewById(R.id.btnCancelCreatenote);
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
                // finish es bezar activity
				dismiss();
			}
		});

		return root;

	}

    // datum kivalasztas
	private void showDatePickerDialog() {
		FragmentManager fm = getFragmentManager();

        // a DatePickerDialogFragment implentalja a szukseges metodusokat
		DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
		datePicker.setTargetFragment(this, 0);
		datePicker.show(fm, DatePickerDialogFragment.TAG);
	}

	// Listener implementalasa a datapickertol
	public void onDateSelected(String date) {
		txtDueDate.setText(date);
	}

	// Listener interface
	public interface INoteCreateFragment {
		public void onNoteCreated(Note newNote);
	}
}