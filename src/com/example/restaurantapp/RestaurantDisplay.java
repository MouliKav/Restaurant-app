package com.example.restaurantapp;


import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

public class RestaurantDisplay extends Activity {
	
	private TextView mTitleText;
	private TextView mAddressText;
	private TextView mPhoneText;
	
	private Long mRowId;
	private RestaurantDbAdapter mDbHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDbHelper = new RestaurantDbAdapter(this);
		mDbHelper.open();
		
		this.setContentView(R.layout.restaurant_display);
		this.setTitle(R.string.Display_Title);
		
		mTitleText = (TextView) findViewById(R.id.business_Name);
		mAddressText = (TextView) findViewById(R.id.business_Address);
		mPhoneText = (TextView) findViewById(R.id.business_Phone);
		
		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState
						.getSerializable(RestaurantDbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras
					.getLong(RestaurantDbAdapter.KEY_ROWID) : null;
		}	
		populateFields();
	}
	
	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			
			mTitleText.setText(note.getString(note
					.getColumnIndexOrThrow(RestaurantDbAdapter.KEY_TITLE)));
			mAddressText.setText(note.getString(note
					.getColumnIndexOrThrow(RestaurantDbAdapter.KEY_Address)));
			mPhoneText.setText(note.getString(note
					.getColumnIndexOrThrow(RestaurantDbAdapter.KEY_Contact)));
		}
	}
}
