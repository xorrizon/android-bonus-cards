package net.xorrizon.bonuscards2;

import android.content.Context;
import net.xorrizon.bonuscards2.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardContainer {
	private static CardContainer instance = null;

	private List<Card> cardList = new ArrayList<Card>();

	private CardContainer(){
		cardList.add(new Card("Billa", "Ich", null, null));
		cardList.add(new Card("Company X", "Ich", null, null));
		cardList.add(new Card("Company Y", "Mr. X", null, null));
	};

	public static synchronized CardContainer instance() {
		if(instance == null)
			instance = new CardContainer();
		return instance;
	}

	public int getSize() {
		return cardList.size();
	}

	public void addCard(Card card) {
		cardList.add(card);
	}

	public Card getCard(int position) {
		return cardList.get(position);
	}

	public void removeCard(Card cardToRemove) {
		cardList.remove(cardToRemove);
	}

	public void removeCards(List<Card> cardsToRemove) {
		for(Card card : cardsToRemove)
			removeCard(card);
	}

	public CardAdapter createAdapter(Context context) {
		return new CardAdapter(context, cardList);
	}

}
