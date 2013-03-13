package net.xorrizon.bonuscards2;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Card {
	public static final String TAG = "Card";
	private String name;
	private String owner;
	private String barcode_content;
	private String barcode_type;

	private transient String barcodeFilename;
	private transient String barcodeFilename_old;

	public Card() {}
	public Card(String name, String owner, String barcode_content, String barcode_type) {
		this.name = name;
		this.owner = owner;
		this.barcode_content = barcode_content;
		this.barcode_type = barcode_type;
		generateFilename();
	}

	@Override
	public String toString() {
		return name + ", " + owner + ", " + barcode_content;
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

	private void generateFilename() {
		if(barcode_content == null)
			return;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, e.getMessage());
			return;
		}
		barcodeFilename_old = barcodeFilename;
		byte[] hash = md.digest(barcode_content.getBytes());
		barcodeFilename = Base64.encodeToString(hash, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING) + ".png";
		if(barcodeFilename_old != null && !barcodeFilename_old.equals(barcodeFilename)) {
			File file = new File(BonusCardsApplication.getAppContext().getExternalFilesDir(null), barcodeFilename_old);
			if(file.exists()) {
				file.delete();
			}
		}
	}

//====== Accessors ============

	public File getBarcodeFile() {
		return new File(BonusCardsApplication.getAppContext().getExternalFilesDir(null), barcodeFilename);
	}

	public String getBarcodeFilename() {
		return barcodeFilename;
	}
	public String getBarcode_content() {
		return barcode_content;
	}

	public void setBarcode_content(String barcode_content) {
		if(this.barcode_content != null && this.barcode_content.equals(barcode_content))
			return;
		this.barcode_content = barcode_content;
		generateFilename();
	}

	public String getBarcode_type() {
		return barcode_type;
	}

	public void setBarcode_type(String barcode_type) {
		this.barcode_type = barcode_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
