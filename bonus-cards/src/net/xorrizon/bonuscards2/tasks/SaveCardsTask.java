package net.xorrizon.bonuscards2.tasks;

import android.os.AsyncTask;
import net.xorrizon.bonuscards2.Card;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.CardSerializer;

import java.util.List;

public class SaveCardsTask extends AsyncTask<Void, Void, Void> {
	private CardSerializer cardSerializer;

	public SaveCardsTask(CardSerializer cardSerializer) {
		this.cardSerializer = cardSerializer;
	}

	@Override
	protected void onPreExecute() {
		CardContainer.instance().getLock().lock();
	}

	@Override
	protected Void doInBackground(Void... params) {
		List<Card> cardList = CardContainer.instance().getCardList();
		cardSerializer.saveCardsToFile(cardList);
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		CardContainer.instance().getLock().unlock();
	}

	@Override
	protected void onCancelled(Void aVoid) {
		CardContainer.instance().getLock().unlock();
	}
}
