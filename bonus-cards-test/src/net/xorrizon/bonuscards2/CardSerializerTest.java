package net.xorrizon.bonuscards2;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

public class CardSerializerTest extends InstrumentationTestCase {
	List<Card> cards = new ArrayList<Card>();
	String cards_json = "[{\"barcode_content\":\"42\",\"barcode_type\":\"TestType\",\"name\":\"Company X\",\"owner\":\"Mr X\"},{\"barcode_content\":\"21\",\"barcode_type\":\"TestType2\",\"name\":\"Company Y\",\"owner\":\"Mr Y\"}]";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cards.add(new Card("Company X","Mr X", "42", "TestType"));
		cards.add(new Card("Company Y","Mr Y", "21", "TestType2"));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		cards.clear();
	}

	public void test_toAndFromJson() {
		String json = CardSerializer.cardsToJson(cards);
		String json_expected = cards_json;
		assertEquals(json_expected, json);

		List<Card> loaded_cards = CardSerializer.jsonToCards(json_expected);
		assertEquals(cards.get(0), loaded_cards.get(0));
		assertEquals(cards.get(1), loaded_cards.get(1));

	}

}
