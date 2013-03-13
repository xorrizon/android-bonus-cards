package net.xorrizon.bonuscards2.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import net.xorrizon.bonuscards2.Card;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.CardSerializer;
import net.xorrizon.bonuscards2.R;
import net.xorrizon.bonuscards2.tasks.LoadCardsTask;

import java.io.File;
import java.util.List;

public class ImportCardsActivity extends Activity implements LoadCardsTask.LoadCardsTaskCallback, View.OnClickListener {
	private Button button_import;
	private List<Card> cardsToImport;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.import_cards_activity);
		TextView text_cards = (TextView) findViewById(R.id.text_cards_text);
		button_import = (Button) findViewById(R.id.button_import_cards);

		if(CardContainer.instance().getSize() == 0) {
			//Make sure the cards are loaded before the user can import new ones
			button_import.setText("Please Wait...");
			button_import.setEnabled(false);
			new LoadCardsTask(new CardSerializer(), this).execute();
		}

		Intent intent = getIntent();

		Log.d("ImportCardsActivity", intent.getAction());
		Log.d("ImportCardsActivity", intent.getType());
		Log.d("ImportCardsActivity", intent.getDataString());

		Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
		Log.d("ImportCardsActivity", "uri: " + uri.getPath());
		File file = new File(uri.getPath());
		cardsToImport = new CardSerializer().loadCardsFromFile(file);
		String text = "";
		for(Card card : cardsToImport) {
			text = text + card.toString() + "\n";
		}
		text_cards.setText(text);

		button_import.setOnClickListener(this);

	}

	@Override
	public void onLoadCardsTaskFinished() {
		button_import.setText(getText(R.string.button_import_cards));
		button_import.setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		CardContainer.instance().addCards(cardsToImport);
		Intent intent = new Intent(ImportCardsActivity.this, CardsActivity.class);
		Toast.makeText(ImportCardsActivity.this, "" + cardsToImport.size() + " cards imported", Toast.LENGTH_SHORT).show();
		startActivity(intent);
		finish();
	}
}