package net.xorrizon.bonuscards2;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class CardTest extends InstrumentationTestCase {
	public void test_filenameGeneration() throws IOException {
		Card card = new Card("asdf", "Mr X", "42", "TestType");
		String filename = card.getBarcodeFilename();
		assertNotNull(filename);
		File file = card.getBarcodeFile();
		assertNotNull(file);
		assertFalse(file.exists());

		Log.d("JUNIT", "Barcode path: " + file.getAbsolutePath());
		file.createNewFile();
		assertTrue(file.exists());

		card.setBarcode_content("21");
		assertTrue("new barcode filename is the same as old one", !filename.equals(card.getBarcodeFilename()));
		assertFalse("old barcode file should have been deleted", file.exists());

	}
}
