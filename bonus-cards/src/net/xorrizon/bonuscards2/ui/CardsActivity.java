package net.xorrizon.bonuscards2.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.CardSerializer;
import net.xorrizon.bonuscards2.R;
import net.xorrizon.bonuscards2.adapter.CardAdapter;
import net.xorrizon.bonuscards2.tasks.LoadCardsTask;

public class CardsActivity extends ListActivity implements CardAdapter.OnCheckedChangeListener, CardAdapter.OnItemClickListener, LoadCardsTask.LoadCardsTaskCallback {
	private static final String TAG = "CardsActivity";
	private MenuItem searchItem;
	private SearchView searchView;
	private CardAdapter adapter;
	private ActionMode currentActionMode = null;

	private CardSerializer cardSerializer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards_activity);
		adapter = CardContainer.instance().createAdapter(this);
		getListView().setAdapter(adapter);
		adapter.setOnCheckedChangeListener(this);
		adapter.setOnItemClickListener(this);
		cardSerializer = new CardSerializer();

		if(CardContainer.instance().getSize() > 0) {
			//We still have our data, no need to load it from file
			CardContainer.instance().deleteObservers();
			CardContainer.instance().addObserver(cardSerializer);
		} else {
			new LoadCardsTask(cardSerializer, this).execute();
		}
	}

	@Override
	public void onLoadCardsTaskFinished() {
		Log.d(TAG, "onLoadCardsTaskFinished called");
		adapter.notifyDataSetChanged();
		CardContainer.instance().deleteObservers();
		CardContainer.instance().addObserver(cardSerializer);
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cards_activity, menu);
		searchItem = menu.findItem(R.id.menuActionSearch);
		searchView = (SearchView) searchItem.getActionView();

		MenuItem addCardItem = menu.findItem(R.id.menuAdd);
		addCardItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(CardsActivity.this, EditCardActivity.class);
				startActivity(intent);
				return true;
			}
		});

		//searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public void onCheckedChange() {
		//Toast.makeText(this, "Checked: " + adapter.getCheckedItemCount(), Toast.LENGTH_SHORT).show();
		//Log.d("CardActivity", "Checked: " + adapter.getCheckedItemCount());
		if(currentActionMode == null && adapter.getCheckedItemCount() > 0)
			startActionMode(actionModeCallback);

		if(currentActionMode == null)
			return;

		if(adapter.getCheckedItemCount() == 0) {
			currentActionMode.finish();
		} else {
			currentActionMode.getMenu().findItem(R.id.menuEdit).setVisible(adapter.getCheckedItemCount() == 1);
			currentActionMode.setTitle("" + adapter.getCheckedItemCount() + " selected");
		}

	}

	@Override
	public void onItemClicked(int position) {
		Intent intent = new Intent(this, EditCardActivity.class);
		intent.putExtra(EditCardActivity.EDIT_CARD_POSITION, position);
		startActivity(intent);
	}

	private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.cards_activity_actionmode, menu);
			currentActionMode = mode;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				case R.id.menuDelete:
					CardContainer.instance().removeCards(adapter.getCheckedItems());
					adapter.clearCheckedItems();
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
				default:
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			adapter.clearCheckedItems();
			adapter.notifyDataSetChanged();
			currentActionMode = null;
		}
	};
}
