package net.xorrizon.bonuscards2.tasks;

import android.os.AsyncTask;
import net.xorrizon.bonuscards2.Card;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.CardSerializer;

import java.util.List;

public class LoadCardsTask extends AsyncTask<Void, Void, Void> {
	private CardSerializer cardSerializer;
	private LoadCardsTaskCallback loadCardsTaskCallback;

	public LoadCardsTask(CardSerializer cardSerializer, LoadCardsTaskCallback loadCardsTaskCallback) {
		this.cardSerializer = cardSerializer;
		this.loadCardsTaskCallback = loadCardsTaskCallback;
	}

	@Override
	protected Void doInBackground(Void... params) {
		List<Card> cards = cardSerializer.loadCardsFromFile();
		if(cards != null)
			CardContainer.instance().getCardList().addAll(cards);
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		if(loadCardsTaskCallback != null)
			loadCardsTaskCallback.onLoadCardsTaskFinished();
	}

	public interface LoadCardsTaskCallback {
		public void onLoadCardsTaskFinished();
	}
}
