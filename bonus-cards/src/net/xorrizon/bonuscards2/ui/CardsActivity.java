package net.xorrizon.bonuscards2.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import net.xorrizon.bonuscards2.R;

public class CardsActivity extends ListActivity {

	private MenuItem searchItem;
	private SearchView searchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cards_activity, menu);
		searchItem = menu.findItem(R.id.menuActionSearch);
		searchView = (SearchView) searchItem.getActionView();
		//searchView.setOnQueryTextListener(this);
		return true;
	}
}
