package ro.bcim1296.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerDialogFragment extends DialogFragment {

	// Log tag
	public static final String TAG = "DatePickerDialogFragment";

	// State
	private Calendar calSelectedDate = Calendar.getInstance();

	// Listener
	private IDatePickerDialogFragment listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

        // Listener lekerese
		if (getTargetFragment() != null) {
			try {
				listener = (IDatePickerDialogFragment) getTargetFragment();
			} catch (ClassCastException ce) {
                ce.printStackTrace();
            }
        }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // aktualis datumra inicializaljuk a datumot
		calSelectedDate.setTime(new Date(System.currentTimeMillis()));
	}

    // datum megjelenitese az aktualis fragmenten
    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(getActivity(), mDateSetListener,
				calSelectedDate.get(Calendar.YEAR),
				calSelectedDate.get(Calendar.MONTH),
				calSelectedDate.get(Calendar.DAY_OF_MONTH));
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// uj datum beallitasa
			calSelectedDate.set(Calendar.YEAR, year);
			calSelectedDate.set(Calendar.MONTH, monthOfYear);
			calSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // ertesitjuk a fragmentunket
			listener.onDateSelected(buildDateText());

			dismiss(); // finish and close
		}
	};

    // felepiti a datumot string formajaban
	private String buildDateText() {
		StringBuilder dateString = new StringBuilder();
		dateString.append(calSelectedDate.get(Calendar.YEAR));
		dateString.append(". ");
		dateString.append(calSelectedDate.get(Calendar.MONTH) + 1);
		dateString.append(". ");
		dateString.append(calSelectedDate.get(Calendar.DAY_OF_MONTH));
		dateString.append(".");

		return dateString.toString();
	}

    // Listener - kommunikal a NoteCreateFragment-el
	public interface IDatePickerDialogFragment {
		public void onDateSelected(String date);
	}

}
