package com.example.novigradv2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import com.example.novigradv2.employeeActivities.CreateBranchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CreateBranchActivityTest1{
    @Rule
    public ActivityTestRule<CreateBranchActivity> mActivityTestRule= new ActivityTestRule<>(CreateBranchActivity.class);
    public CreateBranchActivity mActivity=null;
    private EditText city;
    private EditText address;


    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
        mActivityTestRule = new ActivityTestRule<>(CreateBranchActivity.class);


    }

    @Test
    @UiThreadTest
    public void cityIsNotValid() {
        city = mActivity.findViewById(R.id.cityEditText);
        address = mActivity.findViewById(R.id.addressEditText);
        city.setText("Ottawa");
        String text = city.getText().toString();
        assertNotEquals("London", text );
    }

    @Test
    @UiThreadTest
    public void cityIsValid() {
        city = mActivity.findViewById(R.id.cityEditText);
        address = mActivity.findViewById(R.id.addressEditText);
        city.setText("Ottawa");
        String text = city.getText().toString();
        assertEquals("Ottawa", text );
    }

    @Test
    @UiThreadTest
    public void addressIsNotValid() {
        city = mActivity.findViewById(R.id.cityEditText);
        address = mActivity.findViewById(R.id.addressEditText);
        address.setText("34, Champ st");
        String text = address.getText().toString();
        assertNotEquals("34, Champ", text );
    }

    @Test
    @UiThreadTest
    public void addressIsValid() {
        city = mActivity.findViewById(R.id.cityEditText);
        address = mActivity.findViewById(R.id.addressEditText);
        address.setText("34, Champ st");
        String text = address.getText().toString();
        assertEquals("34, Champ st", text );
    }

}
