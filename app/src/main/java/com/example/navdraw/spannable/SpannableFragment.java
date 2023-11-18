package com.example.navdraw.spannable;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;
import com.example.navdraw.sharedpreferences.SharedPrefFragment;

public class SpannableFragment extends Fragment {

    public static final String TAG = SpannableFragment.class.getName();

    TextView tvSpannable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spannable_layout, container, false);

        tvSpannable = v.findViewById(R.id.textView);

        // The text that need to be styled using spans
        String text = "Név: Viktor   Kor: 25";

        // This will convert the text-string to spannable string
        // and we will used this spannableString to put spans on
        // them and make the sub-string changes
        SpannableString spannableString = new SpannableString(text);

        // Creating the spans to style the string
        ForegroundColorSpan foregroundColorSpanRed = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan foregroundColorSpanGreen = new ForegroundColorSpan(Color.GREEN);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

        // Setting the spans on spannable string
       /* spannableString.setSpan(foregroundColorSpanRed, 7, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpanGreen, 15, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(boldSpan, 51, 55, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(italicSpan, 57, 63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 57, 63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 68, 77, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(strikethroughSpan, 82, 96, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 5,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 5,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 14,18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 19,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 19,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String text1 = "<font color='#ff0000'> My text </font><ul><li>bullet one</li><li>bullet two</li></ul>";

        String name = "Viktor";
        int age = 25;
        Spanned quantities = Html.fromHtml(
                "<h1>Név: </h1>" + "<font color='#428BCA'>" + name + "</font> " +
                        "<h2>Életkor: </h2>" + "<b><p style=color:red>" + age + "</p></b>", Html.FROM_HTML_MODE_LEGACY);


        tvSpannable.setText(quantities);

        // Setting the spannable string on TextView
//        tvSpannable.setText(spannableString);

        return v;
    }
}
