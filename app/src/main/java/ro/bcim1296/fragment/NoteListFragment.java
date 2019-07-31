package ro.bcim1296.fragment;

import ro.bcim1296.ActivityMain;
import ro.bcim1296.R;
import ro.bcim1296.adapter.NoteCursorAdapter;
import ro.bcim1296.application.NoteApplication;
import ro.bcim1296.model.Note;
import ro.bcim1296.database.Constants;
import ro.bcim1296.database.NoteDatabaseLoader;
import ro.bcim1296.database.tasks.DeleteByIDTask;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NoteListFragment extends ListFragment implements
        NoteCreateFragment.INoteCreateFragment {

	// Log tag
	public static final String TAG = "NoteListFragment";

	// Az adapter osztaly egy arraylistben taroolja az elemeket es konnu feldolgozhatosagot biztosit
	private NoteCursorAdapter adapter;
	private LocalBroadcastManager lbm;

	// az ActivityMain implementalja a listener interface-et
	private INoteListFragment listener;
    private INoteEmailFragment emailListener;

    // DBloader
	private NoteDatabaseLoader dbLoader;
	private GetAllTask getAllTask;

    // a Fragment Activity-hez valo csatolasakor hivodik meg
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

        // az ActivityMain mivel implementalja a listener-unket ezert biztosan el tudjuk kerni az objectet
		try {
			listener = (INoteListFragment) activity;
            emailListener = (INoteEmailFragment) activity;
		} catch (ClassCastException ce) {
            ce.printStackTrace();
        }
	}

    // a Fragment letrehozasakor hivodik meg
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // a fragmentemnek lesz menuje
		setHasOptionsMenu(true);

		lbm = LocalBroadcastManager.getInstance(getActivity());
		dbLoader = NoteApplication.getNoteDbLoader();
	}

    // a Fragment inditasakor hivodik meg
	@Override
	public void onStart() {
		super.onStart();

        // bealitjuk a listaelem hosszan nyomvatarsahoz a RegisterMenut
		registerForContextMenu(getListView());
	}


	@Override
	public void onResume() {
		super.onResume();

		// Regisztraljuk az adatbazis modosulasara figyelmezteto Receiver-t
		IntentFilter filter = new IntentFilter(Constants.ACTION_DATABASE_CHANGED);
		lbm.registerReceiver(updateDbReceiver, filter);

		// Frissitjuk a lista tartalmat, ha visszater a user
		refreshList();
	}

	@Override
	public void onPause() {
		super.onPause();

		// Kiregisztraljuk az adatbazis modosulasara figyelmezteto Receiver-t
		lbm.unregisterReceiver(updateDbReceiver);

		//
		if (getAllTask != null) {
			getAllTask.cancel(false);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// Ha van Cursor rendelve az Adapterhez, lezarjuk
		if (adapter != null && adapter.getCursor() != null) {
			adapter.getCursor().close();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // ha a lista elemre kattintottunk
		if (v.equals(getListView())) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			// az elougro Menu fejleceben a fejlec beallitasa az adott elem nevere
            menu.setHeaderTitle(((Note) getListAdapter().getItem(info.position)).getTitle());

            // beallitjuk a menu elemeket, amit a notemenu.xml eroforrastol kerunk le
			String[] menuItems = getResources().getStringArray(R.array.notemenu);
			for (int i = 0; i < menuItems.length; i++) {
                // nincs csoportbaszervezes, azonosito, pozicio, szoveg
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

    // listmenu kivalasztot elemenek lekezelese
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int menuItemIndex = item.getItemId();
        // kivalasztott elem torlese
		if (menuItemIndex == 0) {
			Note selectedNote = adapter.getItem(info.position);

			DeleteByIDTask deleteByIdTask = new DeleteByIDTask(getActivity().getApplicationContext(), dbLoader);
			deleteByIdTask.execute(selectedNote.get_id());
		} else if (menuItemIndex == 1) {
            // EMAIL KULDES

            Note selectedNote = adapter.getItem(info.position);
            emailListener.onEmailSelected(selectedNote);
/*
            getActivity().startActivity(new Intent(getActivity().getBaseContext(),ActivityEmailSend.class));


             i.putExtra(NoteDetailsFragment.KEY_NOTE, selectedNote);*/
//            startActivity(i);

//            SendMail sendMail = new SendMail();
//            sendMail.initAndSend(selectedNote);
        }


		return true;
	}

    // ha raklikkeltunk egy elemre a fragmensunkon a listabol meghivodik
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Note selectedNote = (Note) getListAdapter().getItem(position);

		if (listener != null) {
            // visszaszolunk az ActivityMain-nek, hogy valtozott a kivalasztott elem
			listener.onNoteSelected(selectedNote);
		}
	}

    // menu obijektum beallitasa a listmenu.xml eroforrasbol
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.listmenu, menu);
	}

    // a notemenu-re elemere kattintas utan jelenik meg
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.itemCreatenote) {
			NoteCreateFragment createFragment = new NoteCreateFragment();
            // ki az aki ezt az objektumot meghiv
			createFragment.setTargetFragment(this, 0);

			FragmentManager fm = getFragmentManager();
			createFragment.show(fm, NoteCreateFragment.TAG);
		} /*else if (item.getItemId() == R.id.itemDeleteAllDB) {
			DeleteAllTask deleteTask = new DeleteAllTask(getActivity()
					.getApplicationContext(), dbLoader);

			deleteTask.execute();
		} */

		return super.onOptionsItemSelected(item);
	}

	private void refreshList() {
		if (getAllTask != null) {
			getAllTask.cancel(false);
		}

		getAllTask = new GetAllTask();
		getAllTask.execute();
	}

    // INoteCreateFragment - ertesit az uj elem beszurasarol
    public void onNoteCreated(Note newNote) {
        dbLoader.createNote(newNote, (ActivityMain) getActivity(), this);
        refresh();
    }

    // aktualis Fragment frissitese
    public void refresh() {
        refreshList();
    }

    public interface INoteListFragment {
		public void onNoteSelected(Note selecedNote);
	}

    public interface INoteEmailFragment {
        public void onEmailSelected(Note selecedNote);
    }

	// Broadcast listener
	private BroadcastReceiver updateDbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			refreshList();
		}
	};

	private class GetAllTask extends AsyncTask<Void, Void, Cursor> {

		private static final String TAG = "GetAllTask";

		@Override
		protected Cursor doInBackground(Void... params) {
			try {
				Cursor result = dbLoader.fetchAll();

				if (!isCancelled()) {
					return result;
				} else {
					if (result != null) {
						result.close();
					}

					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            if (adapter == null) {
                adapter = new NoteCursorAdapter(getActivity().getApplicationContext(), result);
                setListAdapter(adapter);
            } else {
                adapter.changeCursor(result);
            }

            getAllTask = null;
        }
	}
}
