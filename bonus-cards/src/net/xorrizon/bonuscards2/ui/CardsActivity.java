package net.xorrizon.bonuscards2.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.R;
import net.xorrizon.bonuscards2.adapter.CardAdapter;

public class CardsActivity extends ListActivity implements CardAdapter.OnCheckedChangeListener {

	private MenuItem searchItem;
	private SearchView searchView;
	private CardAdapter adapter;
	private ActionMode currentActionMode = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards_activity);
		adapter = CardContainer.instance().createAdapter(this);
		getListView().setAdapter(adapter);
		adapter.setOnCheckedChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cards_activity, menu);
		searchItem = menu.findItem(R.id.menuActionSearch);
		searchView = (SearchView) searchItem.getActionView();
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
			currentActionMode.setTitle("" + adapter.getCheckedItemCount() + " selected");
		}

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
