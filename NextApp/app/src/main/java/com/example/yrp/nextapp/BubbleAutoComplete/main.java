package com.example.yrp.nextapp.BubbleAutoComplete;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.yrp.nextapp.R;

import java.util.ArrayList;

/**
 * Created by YRP on 02/08/2015.
 */
public class main extends Activity {
    private String contactName = "andini";
    private MultiAutoCompleteTextView to_input = null;
    String[] arra = {"makanan","minuman","pakaian","meja"};
    ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble_layput);

        to_input = (MultiAutoCompleteTextView) findViewById(R.id.multiBubble);

        mAdapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown_item_1line, arra);
        to_input.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        to_input.setThreshold(1);
        to_input.setAdapter(mAdapter);
//
//        final SpannableStringBuilder sb = new SpannableStringBuilder();
//        TextView tv = createContactTextView(contactName);
//        BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv);
//        bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());
//
//        sb.append(contactName + ",");
//        sb.setSpan(new ImageSpan(bd), sb.length() - (contactName.length() + 1), sb.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        to_input.setText(sb);
    }

    public TextView createContactTextView(String text) {
        //creating textview dynamically
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(20);
        tv.setBackgroundResource(R.drawable.oval);
//        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_search_api_holo_light, 0);
        return tv;
    }

    public static Object convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        return new BitmapDrawable(viewBmp);


    }
}
