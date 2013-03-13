package net.xorrizon.bonuscards2;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.xorrizon.bonuscards2.tasks.SaveCardsTask;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CardSerializer implements Observer {
	private static final String TAG = "CardSerializer";
	private static final String filename = "saved_cards.bc";

	@Override
	public void update(Observable observable, Object data) {
		Log.d(TAG, "CardContainer sent notify");
		SaveCardsTask saveCardsTask = new SaveCardsTask(this);
		saveCardsTask.execute();
	}

	public static String cardsToJson(List<Card> cards) {
		return new Gson().toJson(cards);
	}

	public static List<Card> jsonToCards(String json) {
		Type collectionType = new TypeToken<ArrayList<Card>>(){}.getType();
		return new Gson().fromJson(json, collectionType);
	}

	public void saveCardsToFile(List<Card> cards) {
		File file = new File(BonusCardsApplication.getAppContext().getExternalFilesDir(null), filename);
		String json = cardsToJson(cards);
		FileWriter fw;
		try {
			fw = new FileWriter(file, false);
			fw.write(json);
			fw.flush();
			fw.close();
			Log.i(TAG, "Finished saving cards to file");
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public List<Card> loadCardsFromFile(File file) {
		if(file == null || !file.exists()){
			Log.i(TAG, "Cannot load cards, file does not exist");
			return null;
		}
		String json;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			json = sb.toString();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
		Log.i(TAG, "Finished loading cards from file");
		return jsonToCards(json);
	}

	public List<Card> loadCardsFromFile() {
		File file = new File(BonusCardsApplication.getAppContext().getExternalFilesDir(null), filename);
		return loadCardsFromFile(file);
	}

}
