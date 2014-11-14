package com.example.restaurantapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RestaurantEdit extends Activity {

	private EditText ResTitle;
	private EditText ResAddress;
	private EditText ResContact;

	private Long mRowId;
	private RestaurantDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new RestaurantDbAdapter(this);

		mDbHelper.open();
		setContentView(R.layout.restaurant_edit);
		setTitle(R.string.edit_note);

		ResTitle = (EditText) findViewById(R.id.Res_Name);
		ResAddress = (EditText) findViewById(R.id.Res_address);
		ResContact = (EditText) findViewById(R.id.ContactNo);

		Button confirmButton = (Button) findViewById(R.id.confirm);

		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState
						.getSerializable(RestaurantDbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras
					.getLong(RestaurantDbAdapter.KEY_ROWID) : null;
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			
			ResTitle.setText(note.getString(note
					.getColumnIndexOrThrow(RestaurantDbAdapter.KEY_TITLE)));
			ResAddress.setText(note.getString(note
					.getColumnIndexOrThrow(RestaurantDbAdapter.KEY_Address)));
			ResContact.setText(note.getString(note
					.getColumnIndexOrThrow(RestaurantDbAdapter.KEY_Contact)));
		
	}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(RestaurantDbAdapter.KEY_ROWID, mRowId);
	}

	protected void onPause() {
		super.onPause();
		saveState();
	}

	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		String title = ResTitle.getText().toString();
		String address = ResAddress.getText().toString();
		String contact = ResContact.getText().toString();


		if (mRowId == null) {
			long id = mDbHelper.createNote(title, address, contact);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, title, address, contact);
		}
	}
}




