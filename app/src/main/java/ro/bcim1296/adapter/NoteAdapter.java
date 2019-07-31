package ro.bcim1296.adapter;

import ro.bcim1296.R;
import ro.bcim1296.model.Note;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {

    private final List<Note> notes;

    static class ViewHolder {
        ImageView imageViewIcon;
        TextView textViewTitle;
        TextView textViewDueDate;
    }

    public NoteAdapter(final Context context, final ArrayList<Note> notes) {
    	this.notes = notes;
    }
    
    public void addItem(Note newNote)
    {
    	notes.add(newNote);
    }

    public int getCount() {
        return notes.size();
    }

    public Object getItem(int position) {
        return notes.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // Sor megjelenenitesenek beallitasa
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder; 
    	if (convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	convertView = inflater.inflate(R.layout.noterow, parent, false);

            // lekerjuk az eroforrasokat
            holder = new ViewHolder();
	    	holder.imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewPriority); 
	    	holder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle); 
	    	holder.textViewDueDate = (TextView) convertView.findViewById(R.id.textViewDueDate);

	    	convertView.setTag(holder); 
    	} else { 
    		holder = (ViewHolder) convertView.getTag(); 
    	}

        // az aktualis pozicion levo elem lekerese
        final Note note = notes.get(position);

        // priority lekerese az elemtol es kep beallitasa
        switch (note.getPriority()) {
			case LOW:
				holder.imageViewIcon.setImageResource(R.drawable.low);
				break;
			case MEDIUM:
				holder.imageViewIcon.setImageResource(R.drawable.medium);
				break;
			case HIGH:
				holder.imageViewIcon.setImageResource(R.drawable.high);
				break;
			default:
				holder.imageViewIcon.setImageResource(R.drawable.high);
				break;
		}

        // ItemTitle es ItemDueDate lekerese az elemtol es beallitasa
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDueDate.setText(note.getDueDate());

        return convertView;
    }

    // egy elem torlese
    public void deleteItem(Note note) {
        if (notes.contains(note)) {
        	notes.remove(note);
        }
    }
}
