package com.shycoder.android.bakingapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);
    private static String INTENT = "com.android.shycoder.bakingapp";

    @Test
    public void StepsItendingTest() {
        String string = intentsTestRule.getActivity().getResources().getString(R.string.steps);
        intended(AllOf.allOf(hasExtraWithKey(string), toPackage(INTENT)));
    }

    @Test
    public void IngrediensItendingTest() {
        String string = intentsTestRule.getActivity().getResources().getString(R.string.ingredient);
        intended(AllOf.allOf(hasExtraWithKey(string), toPackage(INTENT)));
    }

}
