package ro.bcim1296.database.tasks;

import ro.bcim1296.database.Constants;
import ro.bcim1296.database.NoteDatabaseLoader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

public class DeleteByIDTask extends AsyncTask<Long, Void, Void> {

	private Context ctx;
	private NoteDatabaseLoader loader;

	public DeleteByIDTask(Context ctx, NoteDatabaseLoader loader) {
		super();

		this.ctx = ctx;
		this.loader = loader;
	}

	@Override
	protected Void doInBackground(Long... params) {
		loader.deleteNote(params[0]);
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(ctx);
		Intent dbChangedIntent = new Intent(Constants.ACTION_DATABASE_CHANGED);
		lbm.sendBroadcast(dbChangedIntent);
	}

}
