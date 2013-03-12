package net.xorrizon.bonuscards2;

public class Card {
	public String name;
	public String owner;
	public String barcode_content;
	public String barcode_type;

	public Card() {}
	public Card(String name, String owner, String barcode_content, String barcode_type) {
		this.name = name;
		this.owner = owner;
		this.barcode_content = barcode_content;
		this.barcode_type = barcode_type;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Card))return false;
		Card card = (Card) other;
		return name.equals(card.name) && owner.equals(card.owner) &&
				barcode_content.equals(card.barcode_content) && barcode_type.equals(barcode_type);
	}
}
