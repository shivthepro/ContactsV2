package com.shangeeth.contactsclonev2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shangeeth.contactsclonev2.db.ContactsDataTable;
import com.shangeeth.contactsclonev2.db.ContactsTable;
import com.shangeeth.contactsclonev2.jdo.PrimaryContactJDO;
import com.shangeeth.contactsclonev2.jdo.SecondaryContactsJDO;

import java.util.ArrayList;

public class EditContactActivity2 extends AppCompatActivity {

    LinearLayout mLinearLayoutPhone;
    LinearLayout mLinearLayoutEmail;
    LinearLayout mLinearLayoutWebsite;
    LinearLayout mLinearLayoutIM;
    LinearLayout mLinearLayoutAddress;

    EditText mNameEDT;
    EditText mNoteEDT;
    EditText mOrgEDT;

    ImageView mPhoneAddIV;
    ImageView mEmailAddIV;
    ImageView mWebsiteAddIV;
    ImageView mImAddIV;
    ImageView mAddressAddIV;

    ArrayList<EditText> mPhoneEditTexts;
    ArrayList<EditTextAndIdJDO> mPhoneEditTextJDO;
    ArrayList<View> mPhoneViews;

    ArrayList<EditText> mEmailEditTexts;
    ArrayList<View> mEmailViews;
    ArrayList<EditTextAndIdJDO> mEmailEditTextAndIdJDOs;


    ArrayList<EditText> mWebsiteEditTexts;
    ArrayList<View> mWebsiteViews;
    ArrayList<EditTextAndIdJDO> mWebsiteEditTextAndIdJDOs;

    ArrayList<EditText> mImEditTexts;
    ArrayList<View> mImViews;
    ArrayList<EditTextAndIdJDO> mImEditTextAndIdJDOs;

    ArrayList<EditText> mAddressEditTexts;
    ArrayList<View> mAddressViews;
    ArrayList<EditTextAndIdJDO> mAddressEditTextAndIdJDOs;

    private LayoutInflater mInflater;
    private ArrayList<SecondaryContactsJDO> mContactsJDOs;
    private String mCurrentId;

    private FloatingActionButton mFab;
    private String mContactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact2);


        Intent intent = getIntent();
        mContactsJDOs = (ArrayList<SecondaryContactsJDO>) intent.getSerializableExtra(getString(R.string.contact_data_jdos));
        mCurrentId = intent.getStringExtra(getString(R.string.id_extra));
        mContactName = intent.getStringExtra(getString(R.string.name));

        mInflater = getLayoutInflater();

        mLinearLayoutPhone = (LinearLayout) findViewById(R.id.phone_container);
        mLinearLayoutEmail = (LinearLayout) findViewById(R.id.email_container);
        mLinearLayoutWebsite = (LinearLayout) findViewById(R.id.wesite_container);
        mLinearLayoutIM = (LinearLayout) findViewById(R.id.im_container);
        mLinearLayoutAddress = (LinearLayout) findViewById(R.id.address_container);

        mPhoneAddIV = (ImageView) findViewById(R.id.add_number);
        mEmailAddIV = (ImageView) findViewById(R.id.add_email);
        mWebsiteAddIV = (ImageView) findViewById(R.id.add_website);
        mImAddIV = (ImageView) findViewById(R.id.add_im);
        mAddressAddIV = (ImageView) findViewById(R.id.add_address);

        mNameEDT = (EditText) findViewById(R.id.name_edt_txt);
        mNoteEDT = (EditText) findViewById(R.id.note_edt);
        mOrgEDT = (EditText) findViewById(R.id.organization_edt);


        mFab = (FloatingActionButton) findViewById(R.id.save_fab);

        mPhoneEditTexts = new ArrayList<>();
        mPhoneViews = new ArrayList<>();

        mEmailEditTexts = new ArrayList<>();
        mEmailViews = new ArrayList<>();

        mWebsiteEditTexts = new ArrayList<>();
        mWebsiteViews = new ArrayList<>();

        mImEditTexts = new ArrayList<>();
        mImViews = new ArrayList<>();

        mAddressEditTexts = new ArrayList<>();
        mAddressViews = new ArrayList<>();


        setOnClickListeners();



    }


    public void loadAlldatas(){

        mNameEDT.setText(mContactName);

        for (SecondaryContactsJDO lSecondaryContactsJDO : mContactsJDOs) {

            String type = lSecondaryContactsJDO.getType();

            if (ContactsDataTable.Type.checkType(type)) {

                if (type.equalsIgnoreCase(ContactsDataTable.Type.PHONE)) {

                    addView(mLinearLayoutPhone, mPhoneEditTexts, mPhoneViews, InputType.TYPE_CLASS_PHONE, lSecondaryContactsJDO.getData());

                } else if (type.equalsIgnoreCase(ContactsDataTable.Type.EMAIL)) {

                    addView(mLinearLayoutEmail, mEmailEditTexts, mEmailViews, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, lSecondaryContactsJDO.getData());

                } else if (type.equalsIgnoreCase(ContactsDataTable.Type.WEBSITE)) {

                    addView(mLinearLayoutWebsite, mWebsiteEditTexts, mWebsiteViews, InputType.TYPE_CLASS_TEXT, lSecondaryContactsJDO.getData());

                } else if (type.equalsIgnoreCase(ContactsDataTable.Type.IM)) {

                    addView(mLinearLayoutIM, mImEditTexts, mImViews, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, lSecondaryContactsJDO.getData());

                } else if (type.equalsIgnoreCase(ContactsDataTable.Type.ADDRESS)) {

                    addView(mLinearLayoutAddress, mAddressEditTexts, mAddressViews, InputType.TYPE_CLASS_TEXT, lSecondaryContactsJDO.getData());

                }
            } else if (type.equalsIgnoreCase("Note")) {
                mNoteEDT.setText(lSecondaryContactsJDO.getData());
            } else if (type.equalsIgnoreCase("Organization")) {
                mOrgEDT.setText(lSecondaryContactsJDO.getData());
            }
        }
    }

    public void setOnClickListeners() {

        mPhoneAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addView(mLinearLayoutPhone, mPhoneEditTexts, mPhoneViews, InputType.TYPE_CLASS_PHONE, "");

            }
        });
        mEmailAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addView(mLinearLayoutEmail, mEmailEditTexts, mEmailViews, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, "");

            }
        });
        mWebsiteAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addView(mLinearLayoutWebsite, mWebsiteEditTexts, mWebsiteViews, InputType.TYPE_CLASS_TEXT, "");


            }
        });
        mImAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addView(mLinearLayoutIM, mImEditTexts, mImViews, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, "");


            }
        });
        mAddressAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addView(mLinearLayoutAddress, mAddressEditTexts, mAddressViews, InputType.TYPE_CLASS_TEXT, "");

            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContacts();
            }
        });

    }

    private void saveContacts() {

        // Update the First table
        PrimaryContactJDO lPrimaryContactJDO = new PrimaryContactJDO();
        lPrimaryContactJDO.setId(mCurrentId);
        lPrimaryContactJDO.setDisplayName(mNameEDT.getText().toString().trim());
        lPrimaryContactJDO.setNote(mNoteEDT.getText().toString());
        lPrimaryContactJDO.setOraganization(mOrgEDT.getText().toString());

        ContactsTable table = new ContactsTable(this);
        table.updateRow(lPrimaryContactJDO);

        //Get all the second table data


        Intent intent = new Intent();
        intent.putExtra(getString(R.string.id_extra), mCurrentId);
        setResult(1, intent);
        finish();

    }

    /**
     * Removes the last View in the Layout and from the ArrayList
     *
     * @param pViews
     * @param pLayout
     */
    public void removeLastViewInPhone(ArrayList<View> pViews, LinearLayout pLayout) {

        //TODO: If needed delete the edit text this can be a your source of issue if you are using the arraylist of editTexts
        pLayout.removeView(pViews.get(pViews.size() - 1));
        pViews.remove(pViews.get(pViews.size() - 1));

    }

    /**
     * Sets the last View in the group with a delete Button
     * @param pViews the arrayList of view groups
     */
    public void setLastViewWithDeleteButton(ArrayList<View> pViews) {

        for (View lView : pViews) {
            ((ImageView) lView.findViewById(R.id.delete_iv)).setVisibility(View.INVISIBLE);
        }
        if (pViews.size() >= 1) {
            ((ImageView) pViews.get(pViews.size() - 1).findViewById(R.id.delete_iv)).setVisibility(View.VISIBLE);
        }

    }


    /**
     * Create a view and add the View to pLinearLayout -- Not USED for now
     *
     * @param pLinearLayout the layout to add the view
     * @param pEditTexts    arrayList of edittexts
     * @param pViews        arraylist of views
     * @param pInputType    the type of
     */
    public void addView(final LinearLayout pLinearLayout, ArrayList<EditText> pEditTexts, final ArrayList<View> pViews, int pInputType, String pData) {

        View lView = mInflater.inflate(R.layout.dynamic_item, pLinearLayout, false);
        EditText editText = (EditText) lView.findViewById(R.id.data_edt);
        editText.setText(pData);
        editText.setInputType(pInputType);
        pEditTexts.add(editText);
        pViews.add(lView);

        ((ImageView) lView.findViewById(R.id.delete_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastViewInPhone(pViews, pLinearLayout);
                setLastViewWithDeleteButton(pViews);
            }
        });

        setLastViewWithDeleteButton(pViews);

        pLinearLayout.addView(lView);
    }

    public class EditTextAndIdJDO{
        EditText mEditText;
        String mId;

        public EditTextAndIdJDO(EditText mEditText, String mId) {
            this.mEditText = mEditText;
            this.mId = mId;
        }

        public EditText getmEditText() {
            return mEditText;
        }

        public String getmId() {
            return mId;
        }
    }
}
