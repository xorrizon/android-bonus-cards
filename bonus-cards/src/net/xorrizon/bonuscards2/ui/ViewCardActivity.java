package net.xorrizon.bonuscards2.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import net.xorrizon.bonuscards2.Card;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.R;

import java.util.EnumMap;
import java.util.Map;

public class ViewCardActivity extends Activity {
	public static final String VIEW_CARD_POSITION = "VIEW_CARD_POSITION";

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	private Card currentCard;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_card_activity);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		int card_position = intent.getIntExtra(VIEW_CARD_POSITION, -1);
		if(card_position < 0)
			return;
		currentCard = CardContainer.instance().getCard(card_position);

		TextView text_name = (TextView) findViewById(R.id.text_Name);
		TextView text_owner = (TextView) findViewById(R.id.text_owner);
		TextView text_content = (TextView) findViewById(R.id.text_barcodeContent);
		text_name.setText(currentCard.getName());
		text_owner.setText(currentCard.getOwner());
		text_content.setText(currentCard.getBarcode_content());

		ImageView image_barcode = (ImageView) findViewById(R.id.image_barcode);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = (int)(size.x * 0.9);
		int height = size.y / 2;

		Bitmap image;
		try {

			BarcodeFormat format = BarcodeFormat.valueOf(currentCard.getBarcode_type());

			image = encodeAsBitmap(currentCard.getBarcode_content(), format, width, height);
			image_barcode.setImageBitmap(image);

		} catch (Exception e) {
			return;
		}


	}

	/**************************************************************
	 * getting from com.google.zxing.client.android.encode.QRCodeEncoder
	 * http://stackoverflow.com/questions/10353392/generate-barcode-image-in-android-application
	 *
	 * See the sites below
	 * http://code.google.com/p/zxing/
	 * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
	 * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
	 */
	Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}
		Map<EncodeHintType, Object> hints = null;
		String encoding = guessAppropriateEncoding(contentsToEncode);
		if (encoding != null) {
			hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result;
		try {
			result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	private static String guessAppropriateEncoding(CharSequence contents) {
		// Very crude at the moment
		for (int i = 0; i < contents.length(); i++) {
			if (contents.charAt(i) > 0xFF) {
				return "UTF-8";
			}
		}
		return null;
	}

}