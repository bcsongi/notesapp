package ro.bcim1296.database;

import ro.bcim1296.ActivityMain;
import ro.bcim1296.R;
import ro.bcim1296.database.tasks.MyLocationManager;
import ro.bcim1296.fragment.NoteListFragment;
import ro.bcim1296.model.Note;
import ro.bcim1296.model.Note.Priority;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.sun.mail.util.TraceOutputStream;

public class NoteDatabaseLoader implements MyLocationManager.IMyLocationManager{

	private Context ctx;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase mDb;
    private MyLocationManager myLocationManager;
    private Activity activity;
    private NoteListFragment listFragment;
    private Note note;

	public NoteDatabaseLoader(Context ctx) {
		this.ctx = ctx;
	}

	public void open() throws SQLException {
		// DatabaseHelper objektum
		dbHelper = new DatabaseHelper(ctx, Constants.DATABASE_NAME);
		// adatbazis objektum
		mDb = dbHelper.getWritableDatabase();
		// ha nincs meg , akkor letrehozzuk
		dbHelper.onCreate(mDb);
 //       lm = new ActivityMain().getLM();
	}

    // CLOSEDB
	public void close() {
		dbHelper.close();
	}

	// INSERT
	public void createNote(Note note, ActivityMain activity, NoteListFragment listFragment) {
        this.activity = activity;
        this.listFragment = listFragment;
        myLocationManager = new MyLocationManager(activity, this);
        this.note = note;
    }

	// DELETE
	public boolean deleteNote(long rowId) {
		return mDb.delete(Constants.Note.DATABASE_TABLE, Constants.Note.KEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean deleteAllNote() {
		return mDb.delete(Constants.Note.DATABASE_TABLE, null, null) > 0;
	}

	// UPDATE
	public boolean updateProduct(long rowId, Note newNote) {
		ContentValues values = new ContentValues();
		values.put(Constants.Note.KEY_TITLE, newNote.getTitle());
		values.put(Constants.Note.KEY_DUEDATE, newNote.getDueDate());
		values.put(Constants.Note.KEY_DESCRIPTION, newNote.getDescription());
		values.put(Constants.Note.KEY_PRIORITY, newNote.getPriority().name());
        values.put(Constants.Note.KEY_LATITUDE, newNote.getLatitude());
        values.put(Constants.Note.KEY_LONGITUDE, newNote.getLongitude());

        return mDb.update(Constants.Note.DATABASE_TABLE, values,
				Constants.Note.KEY_ROWID + "=" + rowId, null) > 0;
	}

	// minden elem lekerese
	public Cursor fetchAll() {
		// cursor minden rekordra (where = null)
		return mDb.query(Constants.Note.DATABASE_TABLE, new String[] {
				Constants.Note.KEY_ROWID, Constants.Note.KEY_TITLE,
				Constants.Note.KEY_DESCRIPTION, Constants.Note.KEY_DUEDATE,
				Constants.Note.KEY_PRIORITY, Constants.Note.KEY_LATITUDE,
                Constants.Note.KEY_LONGITUDE}, null, null, null, null, null);
	}

	// egy elem lekerese id szerint
    public Note fetchNote(long rowId) {
        // az elere mutato cursor
		Cursor c = mDb.query(Constants.Note.DATABASE_TABLE, new String[] {
				Constants.Note.KEY_ROWID, Constants.Note.KEY_TITLE,
				Constants.Note.KEY_DESCRIPTION, Constants.Note.KEY_DUEDATE,
				Constants.Note.KEY_PRIORITY, Constants.Note.KEY_LATITUDE,
                Constants.Note.KEY_LONGITUDE }, Constants.Note.KEY_ROWID
				+ "=" + rowId, null, null, null, Constants.Note.KEY_TITLE);
		// ha van rekord amire a Cursor mutat
		if (c.moveToFirst())
			return getNoteByCursor(c);

        return null;
	}

    // cursor -> note alakitasa
	public static Note getNoteByCursor(Cursor c) {
		return new Note(c.getLong(c.getColumnIndex(Constants.Note.KEY_ROWID)),
				c.getString(c.getColumnIndex(Constants.Note.KEY_TITLE)), // title
				Priority.valueOf(c.getString(c.getColumnIndex(Constants.Note.KEY_PRIORITY))), // priority
				c.getString(c.getColumnIndex(Constants.Note.KEY_DUEDATE)), // dueDate
				c.getString(c.getColumnIndex(Constants.Note.KEY_DESCRIPTION)), // description
                c.getDouble(c.getColumnIndex(Constants.Note.KEY_LATITUDE)),
                c.getDouble(c.getColumnIndex(Constants.Note.KEY_LONGITUDE))
		);
	}

    public void onChangeLocation(Double latitude, Double longitude) {
        ContentValues values = new ContentValues();

        values.put(Constants.Note.KEY_TITLE, note.getTitle());
        values.put(Constants.Note.KEY_DUEDATE, note.getDueDate());
        values.put(Constants.Note.KEY_DESCRIPTION, note.getDescription());
        values.put(Constants.Note.KEY_PRIORITY, note.getPriority().name());
        values.put(Constants.Note.KEY_LATITUDE, myLocationManager.getLatitude());
        values.put(Constants.Note.KEY_LONGITUDE, myLocationManager.getLongitude());

        if (mDb.insert(Constants.Note.DATABASE_TABLE, null, values) > 0) {
            Toast.makeText(activity, "Inserted" + mDb.getVersion() + " " + myLocationManager.getLatitude() + " " +
                    myLocationManager.getLatitude(), Toast.LENGTH_LONG).show();
            listFragment.refresh();
        } else {
            Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show();
        }

    }

}
