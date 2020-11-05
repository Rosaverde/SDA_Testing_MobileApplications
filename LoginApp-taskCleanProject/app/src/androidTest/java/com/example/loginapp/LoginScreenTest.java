package com.example.loginapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.loginapp.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withAlpha;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public class LoginScreenTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void warningIconDisplayed() {

        onView(withId(R.id.password))
                .perform(typeText("123456"));

        onView(withId(R.id.password_compromised_warning))
                .check(matches(isDisplayed()));

        onView(withContentDescription("Compromised password"))
                .check(matches(isDisplayed()));

        onView(withResourceName("password_compromised_warning"))
                .check(matches(isDisplayed()));

        onView(withAlpha(0.5f))
                .check(matches(isDisplayed()));
    }

    @Test
    public void signInButtonEnabled() {
        onView(withId(R.id.username))
                .perform(typeText("username"));
        onView(withId(R.id.password))
                .perform(typeText("password"));
        onView(withId(R.id.login))
                .check(matches(isEnabled()));
    }

    @Test
    public void differentAssertion() {
        onView(withText("Compromised password 2"))
                .check(doesNotExist());
    }

    @Test
    public void signInButtonDisabled() {
        onView(withId(R.id.username))
                .perform(typeText(""));
        onView(withId(R.id.password))
                .perform(typeText(""));
        onView(withId(R.id.login))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void loginError() {
        String loginErrorMessage = "Not a valid username";
        onView(withId(R.id.username))
                .perform(typeText("abcdef"))
                .perform(clearText())
                .check(matches(hasErrorText(loginErrorMessage)));
    }

    @Test
    public void passwordValidation() {
        String errorMessage = "Password must be >5 characters";

        onView(withId(R.id.username))
                .perform(typeText("username"));
        onView(withId(R.id.password))
                .perform(click())
                .check(matches(hasErrorText(errorMessage)))
                .perform(typeText("12345"))
                .check(matches(hasErrorText(errorMessage)))
                .perform(typeText("6"))
                .check(matches(not(hasErrorText(errorMessage))));
        onView(withId(R.id.login))
                .check(matches(isEnabled()));
    }

    @Test
    public void swipeOnPicture() {
        onView(withId(R.id.username))
                .perform(typeText("aaa"));
        onView(withId(R.id.password))
                .perform(typeText("password"));
        onView(withId(R.id.login))
                .perform(click());

        onView(withId(R.id.header))
                .perform(swipeUp());
        onView(withId(R.id.long_description))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.hurray))
                .check(matches(isCompletelyDisplayed()));
        onView(withText("Short description"))
                .check(matches(isCompletelyDisplayed()));
    }
}
