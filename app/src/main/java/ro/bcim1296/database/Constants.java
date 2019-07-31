package ro.bcim1296.database;

public class Constants {

	// Broadcast action, amely az adatbazis modosulasat jelzi
	public static final String ACTION_DATABASE_CHANGED = "hu.bute.daai.amorg.examples.DATABASE_CHANGED";
	
	// fajlnev, amiben az adatbazis lesz
	public static final String DATABASE_NAME = "data.db";
	
	// verzioszam
	public static final int DATABASE_VERSION = 33;

    // osszes belso osztaly DATABASE_CREATE szkriptje osszefuzve
	public static String DATABASE_CREATE_ALL = Note.DATABASE_CREATE;
	
	// osszes belso osztaly DATABASE_DROP szkriptje osszefuzve
	public static String DATABASE_DROP_ALL = Note.DATABASE_DROP;

	/* Note osztaly DB konstansai */
	public static class Note {
		
		// tabla neve
		public static final String DATABASE_TABLE = "note";
		
		// oszlopnevek
		public static final String KEY_ROWID = "_id";
		public static final String KEY_TITLE = "title";
		public static final String KEY_PRIORITY = "priority";
		public static final String KEY_DUEDATE = "dueDate";
		public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_LATITUDE = "latitude";
        public static final String KEY_LONGITUDE = "longitude";

		// sema letrehozo szkript
		public static final String DATABASE_CREATE = "create table if not exists " + DATABASE_TABLE
                + " ( " + KEY_ROWID + " integer primary key autoincrement, "
				+ KEY_TITLE + " text not null, "
			    + KEY_PRIORITY + " text, "
				+ KEY_DUEDATE + " text, "
                + KEY_DESCRIPTION + " text,"
                + KEY_LATITUDE + " text,"
                + KEY_LONGITUDE + " text"
                + "); ";

        // insert script
        public static final String INSERT_TO_DATABASE = "insert into " + DATABASE_TABLE
                + "(" + KEY_TITLE + "," + KEY_PRIORITY + "," + KEY_DUEDATE + "," + KEY_DESCRIPTION + ")"
                + "VALUES";

        // sema torlo szkript
		public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE + "; ";
	}

}
