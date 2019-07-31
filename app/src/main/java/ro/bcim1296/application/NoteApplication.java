package ro.bcim1296.application;

import ro.bcim1296.database.NoteDatabaseLoader;

import android.app.Application;

// dbloader letrehozasara szolgal
public class NoteApplication extends Application {
	private static NoteDatabaseLoader dbLoader;

    // getNewInstance
	public static NoteDatabaseLoader getNoteDbLoader() {
		return dbLoader;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		dbLoader = new NoteDatabaseLoader(this);
		// Open db
        dbLoader.open();
	}

	@Override
	public void onTerminate() {
		// Close db
		dbLoader.close();
		
		super.onTerminate();
	}
}