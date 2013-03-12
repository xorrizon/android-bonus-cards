package net.xorrizon.bonuscards2;

import android.content.Context;
import net.xorrizon.bonuscards2.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.ReentrantLock;

public class CardContainer extends Observable {
	private static CardContainer instance = null;

	private List<Card> cardList = new ArrayList<Card>();
	private ReentrantLock cardList_lock = new ReentrantLock(); //Only for write access and for serializing

	private CardContainer(){
//		cardList.add(new Card("Billa", "Ich", null, null));
//		cardList.add(new Card("Company X", "Ich", null, null));
//		cardList.add(new Card("Company Y", "Mr. X", null, null));
	};

	public static synchronized CardContainer instance() {
		if(instance == null)
			instance = new CardContainer();
		return instance;
	}

	public List<Card> getCardList() {
		return cardList;
	}

	public int getSize() {
		return cardList.size();
	}

	public void addCard(Card card) {
		cardList_lock.lock();
		cardList.add(card);
		cardList_lock.unlock();
		setChanged();
		notifyObservers();
	}

	public Card getCard(int position) {
		return cardList.get(position);
	}

	public void removeCards(List<Card> cardsToRemove) {
		cardList_lock.lock();
		for(Card card : cardsToRemove)
			cardList.remove(card);
		cardList_lock.unlock();
		setChanged();
		notifyObservers();
	}

	public CardAdapter createAdapter(Context context) {
		return new CardAdapter(context, cardList);
	}

	public ReentrantLock getLock() {
		return cardList_lock;
	}

}
