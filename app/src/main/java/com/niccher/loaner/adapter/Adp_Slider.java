package com.niccher.loaner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.niccher.loaner.R;

import static androidx.core.content.ContextCompat.getColor;


public class Adp_Slider extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public Adp_Slider(Context context) {
        this.context = context;
    }

    public int[] image_slide ={
            R.mipmap.bag,
            R.mipmap.grow1,
            R.mipmap.notes1,
            R.mipmap.takecoin2
    };

    public String[] heading_slide ={
            "Europol Azima SACCO.",
            "Grow Limits.",
            "M-Pesa.",
            "Instantly Credited."
    };

    public String[] description_slide ={
            "Welcome to Europol Azima SACCO.",
            "Grow your loan limit by regularly taking and paying loan.",
            "Your loan is sent to your M-Psesa account.",
            "No delays in processing your loan request."
    };

    @Override
    public int getCount() {

        return heading_slide.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);
        container.addView(view);

        RelativeLayout change = view.findViewById(R.id.changeme);

        ImageView slide_imageView = view.findViewById(R.id.tvImage);
        TextView slideHeading = view.findViewById(R.id.tvHeading);
        TextView slideDescription = view.findViewById(R.id.tvDescription);

        if ( (heading_slide[position])=="Welcome"){
            change.setBackgroundColor(getColor(context,R.color.bak1));
        }
        if ( (heading_slide[position])=="Delivery"){
            change.setBackgroundColor(getColor(context,R.color.bak1));
        }
        if ( (heading_slide[position])=="Delivery 2"){
            change.setBackgroundColor(getColor(context,R.color.bak1));
        }
        if ( (heading_slide[position])=="Assortment"){
            //change.setBackgroundResource(R.mipmap.assortment);
            change.setBackgroundColor(getColor(context,R.color.bak1));
        }

        slide_imageView.setImageResource(image_slide[position]);
        slideHeading.setText(heading_slide[position]);
        slideDescription.setText(description_slide[position]);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}


