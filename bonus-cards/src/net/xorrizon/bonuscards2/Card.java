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

}
