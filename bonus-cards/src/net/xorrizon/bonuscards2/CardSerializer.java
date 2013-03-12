package net.xorrizon.bonuscards2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class CardSerializer implements Observer {

	@Override
	public void update(Observable observable, Object data) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public static String cardsToJson(List<Card> cards) {
		return new Gson().toJson(cards);
	}

	public static List<Card> jsonToCards(String json) {
		Type collectionType = new TypeToken<ArrayList<Card>>(){}.getType();
		return new Gson().fromJson(json, collectionType);
	}

}
