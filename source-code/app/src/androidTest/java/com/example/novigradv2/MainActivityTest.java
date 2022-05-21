package com.example.novigradv2;


import static org.junit.Assert.assertNotEquals;

import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import com.example.novigradv2.authentication.MainActivity;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule= new ActivityTestRule<>(MainActivity.class);
    public MainActivity mActivity=null;
    private EditText emailOrUserName;
    private EditText password;


    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
        mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    }



    @Test
    @UiThreadTest
    public void checkEmail() {
        emailOrUserName = mActivity.findViewById(R.id.emailOrUserNameEditTextLogin);
        emailOrUserName.setText("testuser@abc");
        String email = emailOrUserName.getText().toString();
        assertNotEquals("teat@gmail.com", email );
    }

    @Test
    @UiThreadTest
    public void checkPassword() {
        password = mActivity.findViewById(R.id.passwordEditTextLogin);
        password.setText("password1");
        String pwd = password.getText().toString();
        assertNotEquals("password",pwd);
    }

    @Test
    @UiThreadTest
    public void checkUserName() {
        emailOrUserName = mActivity.findViewById(R.id.passwordEditTextLogin);
        emailOrUserName.setText("username1");
        String username  = emailOrUserName.getText().toString();
        assertNotEquals("username",username);
    }




}
