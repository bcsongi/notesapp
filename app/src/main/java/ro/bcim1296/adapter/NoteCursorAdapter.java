package ro.bcim1296.adapter;

import ro.bcim1296.R;
import ro.bcim1296.model.Note;
import ro.bcim1296.database.NoteDatabaseLoader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteCursorAdapter extends CursorAdapter {

	public NoteCursorAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		
		View row = inflater.inflate(R.layout.noterow, null);
		bindView(row, context, cursor);
		
		return row;
	}
	
	// UI elemek feltoltese
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// referencia a UI elemekre lekerese
		TextView titleTV = (TextView) view.findViewById(R.id.textViewTitle);
		TextView dueDateTV = (TextView) view.findViewById(R.id.textViewDueDate);
		ImageView priorityIV = (ImageView) view.findViewById(R.id.imageViewPriority);
		
		// note peldanyositasa cursorbol
		Note note = NoteDatabaseLoader.getNoteByCursor(cursor);
		
		// UI elemek
		titleTV.setText(note.getTitle());
		dueDateTV.setText(note.getDueDate());
		switch (note.getPriority()) {
			case HIGH:
				priorityIV.setImageResource(R.drawable.high);
				break;
			case MEDIUM:
				priorityIV.setImageResource(R.drawable.medium);
				break;
			case LOW:
				priorityIV.setImageResource(R.drawable.low);
				break;
		}
	}
	
	@Override
	public Note getItem(int position) {
		getCursor().moveToPosition(position);
		
		return NoteDatabaseLoader.getNoteByCursor(getCursor());
	}
}
