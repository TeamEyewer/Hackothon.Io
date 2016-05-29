package ranger.eyewer.com.rangerapp.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ranger.eyewer.com.rangerapp.R;

public class UpdateContactsView extends AppCompatActivity {

    RelativeLayout backBtn, row_contact_1, row_contact_2, row_contact_3, row_contact_4, row_contact_5;
    TextView contact_1_name, contact_1_number;
    TextView contact_2_name, contact_2_number;
    TextView contact_3_name, contact_3_number;
    TextView contact_4_name, contact_4_number;
    TextView contact_5_name, contact_5_number;
    RelativeLayout contact_1_remove, contact_2_remove, contact_3_remove, contact_4_remove, contact_5_remove;
    SharedPreferences contactData;
    TextView main_title, main_desc, row_emergency_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts_view);

        ActionBar actionBar = UpdateContactsView.this.getSupportActionBar();
        actionBar.hide();

        contactData = UpdateContactsView.this.getSharedPreferences("contactData", Context.MODE_PRIVATE);

        backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        row_contact_1 = (RelativeLayout) findViewById(R.id.row_contact_1);
        row_contact_2 = (RelativeLayout) findViewById(R.id.row_contact_2);
        row_contact_3 = (RelativeLayout) findViewById(R.id.row_contact_3);
        row_contact_4 = (RelativeLayout) findViewById(R.id.row_contact_4);
        row_contact_5 = (RelativeLayout) findViewById(R.id.row_contact_5);
        contact_1_name = (TextView) findViewById(R.id.contact_1_name);
        contact_2_name = (TextView) findViewById(R.id.contact_2_name);
        contact_3_name = (TextView) findViewById(R.id.row_contact_3_name);
        contact_4_name = (TextView) findViewById(R.id.row_contact_4_name);
        contact_5_name = (TextView) findViewById(R.id.row_contact_5_name);
        contact_1_number = (TextView) findViewById(R.id.contact_1_number);
        contact_2_number = (TextView) findViewById(R.id.contact_2_number);
        contact_3_number = (TextView) findViewById(R.id.row_contact_3_number);
        contact_4_number = (TextView) findViewById(R.id.row_contact_4_number);
        contact_5_number = (TextView) findViewById(R.id.row_contact_5_number);
        contact_1_remove = (RelativeLayout) findViewById(R.id.contact_1_remove);
        contact_2_remove = (RelativeLayout) findViewById(R.id.contact_2_remove);
        contact_3_remove = (RelativeLayout) findViewById(R.id.contact_3_remove);
        contact_4_remove = (RelativeLayout) findViewById(R.id.contact_4_remove);
        contact_5_remove = (RelativeLayout) findViewById(R.id.contact_5_remove);
        row_emergency_title = (TextView) findViewById(R.id.row_emergency_title);
        main_title = (TextView) findViewById(R.id.main_title);
        main_desc = (TextView) findViewById(R.id.main_desc);

        Typeface face_light = Typeface.createFromAsset(UpdateContactsView.this.getAssets(), "fonts/robotolight.ttf");

        main_title.setTypeface(face_light);
        main_desc.setTypeface(face_light);
        row_emergency_title.setTypeface(face_light);


        //region Set Default Data
        contact_1_name.setText(contactData.getString("contact_1_name", "Contact 1"));
        contact_1_number.setText(contactData.getString("contact_1_number", "Not set"));
        contact_2_name.setText(contactData.getString("contact_2_name", "Contact 2"));
        contact_2_number.setText(contactData.getString("contact_2_number", "Not set"));
        contact_3_name.setText(contactData.getString("contact_3_name", "Contact 3"));
        contact_3_number.setText(contactData.getString("contact_3_number", "Not set"));
        contact_4_name.setText(contactData.getString("contact_4_name", "Contact 4"));
        contact_4_number.setText(contactData.getString("contact_4_number", "Not set"));
        contact_5_name.setText(contactData.getString("contact_5_name", "Contact 5"));
        contact_5_number.setText(contactData.getString("contact_5_number", "Not set"));
        //endregion


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //region Remove Contact

        contact_1_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_1_name.setText("Contact 1");
                contact_1_number.setText("Not set");
                contactData.edit().remove("contact_1_name").apply();
                contactData.edit().remove("contact_1_number").apply();
            }
        });

        contact_2_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_2_name.setText("Contact 2");
                contact_2_number.setText("Not set");
                contactData.edit().remove("contact_2_name").apply();
                contactData.edit().remove("contact_2_number").apply();
            }
        });

        contact_3_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_3_name.setText("Contact 3");
                contact_3_number.setText("Not set");
                contactData.edit().remove("contact_3_name").apply();
                contactData.edit().remove("contact_3_number").apply();
            }
        });

        contact_4_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_4_name.setText("Contact 4");
                contact_4_number.setText("Not set");
                contactData.edit().remove("contact_4_name").apply();
                contactData.edit().remove("contact_4_number").apply();
            }
        });

        contact_5_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_5_name.setText("Contact 5");
                contact_5_number.setText("Not set");
                contactData.edit().remove("contact_5_name").apply();
                contactData.edit().remove("contact_5_number").apply();
            }
        });

        //endregion


        row_contact_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        row_contact_1.setBackgroundColor(Color.parseColor("#22BFF5D5"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        row_contact_1.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case MotionEvent.ACTION_UP:
                        row_contact_1.setBackgroundColor(Color.TRANSPARENT);

                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(pickContactIntent, 1);

                        break;
                }
                return true;
            }
        });

        row_contact_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        row_contact_2.setBackgroundColor(Color.parseColor("#22BFF5D5"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        row_contact_2.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case MotionEvent.ACTION_UP:
                        row_contact_2.setBackgroundColor(Color.TRANSPARENT);

                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(pickContactIntent, 2);

                        break;
                }
                return true;
            }
        });

        row_contact_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        row_contact_3.setBackgroundColor(Color.parseColor("#22BFF5D5"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        row_contact_3.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case MotionEvent.ACTION_UP:
                        row_contact_3.setBackgroundColor(Color.TRANSPARENT);

                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(pickContactIntent, 3);

                        break;
                }
                return true;
            }
        });

        row_contact_4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        row_contact_4.setBackgroundColor(Color.parseColor("#22BFF5D5"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        row_contact_4.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case MotionEvent.ACTION_UP:
                        row_contact_4.setBackgroundColor(Color.TRANSPARENT);

                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(pickContactIntent, 4);

                        break;
                }
                return true;
            }
        });

        row_contact_5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        row_contact_5.setBackgroundColor(Color.parseColor("#22BFF5D5"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        row_contact_5.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case MotionEvent.ACTION_UP:
                        row_contact_5.setBackgroundColor(Color.TRANSPARENT);

                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(pickContactIntent, 5);

                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri contactUri = data.getData();
            String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver()
                    .query(contactUri, projection, null, null, null);
            cursor.moveToFirst();

            int numberColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            String name = cursor.getString(nameColumn);
            String number = cursor.getString(numberColumn);

            switch (requestCode) {
                case 1:
                    contact_1_name.setText(name);
                    contact_1_number.setText(number);
                    SaveData(1, name, number);
                    break;
                case 2:
                    contact_2_name.setText(name);
                    contact_2_number.setText(number);
                    SaveData(2, name, number);
                    break;
                case 3:
                    contact_3_name.setText(name);
                    contact_3_number.setText(number);
                    SaveData(3, name, number);
                    break;
                case 4:
                    contact_4_name.setText(name);
                    contact_4_number.setText(number);
                    SaveData(4, name, number);
                    break;
                case 5:
                    contact_5_name.setText(name);
                    contact_5_number.setText(number);
                    SaveData(5, name, number);
                    break;
            }

        }


    }

    private void SaveData(int order, String name, String number) {
        contactData.edit().putString("contact_" + order + "_name", name).apply();
        contactData.edit().putString("contact_" + order + "_number", number).apply();
    }

}