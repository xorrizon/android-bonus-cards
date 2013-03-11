package net.xorrizon.bonuscards2.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import net.xorrizon.bonuscards2.Card;
import net.xorrizon.bonuscards2.CardContainer;
import net.xorrizon.bonuscards2.R;

import java.util.List;

public class EditCardActivity extends Activity implements View.OnClickListener {
	private View actionBarView;
	public static final String EDIT_CARD_POSITION = "net.xorrizon.bonuscards2.EDIT_CARD_POSITION";

	private EditText edit_cardName;
	private EditText edit_cardOwner;
	private EditText edit_cardType;
	private EditText edit_cardNumber;

	boolean isNewCard;
	Card currentCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_card_activity);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		actionBarView = inflater.inflate(R.layout.actionbar_custom_view_done_discard, null);
		getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_SHOW_TITLE);
		getActionBar().setCustomView(actionBarView, new ActionBar.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		actionBarView.findViewById(R.id.actionbar_done).setOnClickListener(this);
		actionBarView.findViewById(R.id.actionbar_discard).setOnClickListener(this);

		findViewById(R.id.btn_scan_code).setOnClickListener(this);
		edit_cardName = (EditText) findViewById(R.id.text_card_name);
		edit_cardOwner = (EditText) findViewById(R.id.text_card_owner);
		edit_cardType = (EditText) findViewById(R.id.text_card_type);
		edit_cardNumber = (EditText) findViewById(R.id.text_card_number);

		Intent intent = getIntent();
		int card_position = intent.getIntExtra(EDIT_CARD_POSITION, -1);
		if(card_position < 0) {
			currentCard = new Card();
			isNewCard = true;
		} else {
			currentCard = CardContainer.instance().getCard(card_position);
			isNewCard = false;
			loadCurrentCardToForm();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.actionbar_done:
				saveFormToCurrentCard();
				finish();
				break;
			case R.id.actionbar_discard:
				finish();
				break;
			case R.id.btn_scan_code:
				scanCode();
				break;
			default:
				return;
		}
	}

	private void scanCode() {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		boolean isIntentSafe = activities.size() > 0;
		if(isIntentSafe)
			startActivityForResult(intent, 0);
		else {
			Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client");
			Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
			startActivity(marketIntent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode != 0 || resultCode != RESULT_OK)
			return;
		String contents = data.getStringExtra("SCAN_RESULT");
		String format = data.getStringExtra("SCAN_RESULT_FORMAT");

		Toast.makeText(this, contents, Toast.LENGTH_SHORT).show();

		edit_cardType.setText(format);
		edit_cardNumber.setText(contents);

	}

	private void loadCurrentCardToForm() {
		edit_cardName.setText(currentCard.name);
		edit_cardOwner.setText(currentCard.owner);
		edit_cardNumber.setText(currentCard.barcode_content);
		edit_cardType.setText(currentCard.barcode_type);
	}

	private void saveFormToCurrentCard() {
		currentCard.name = edit_cardName.getText().toString();
		currentCard.owner = edit_cardOwner.getText().toString();
		currentCard.barcode_type = edit_cardType.getText().toString();
		currentCard.barcode_content = edit_cardNumber.getText().toString();
		if(isNewCard)
			CardContainer.instance().addCard(currentCard);
		isNewCard = false;
	}
}
