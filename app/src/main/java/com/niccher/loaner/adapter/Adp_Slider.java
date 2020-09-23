package com.niccher.loaner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
    };

    public String[] heading_slide ={
            "Welcome",
            "Delivery",
            "Delivery 2",
            "Assortment"
    };

    public String[] description_slide ={
            "Best Loan app",
            "Low interest",
            "Long duration of time",
            "High loan score"
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

        TextView slideHeading = view.findViewById(R.id.tvHeading);
        TextView slideDescription = view.findViewById(R.id.tvDescription);

        if ( (heading_slide[position])=="Welcome"){
            change.setBackgroundColor(getColor(context,R.color.bak1));
        }
        if ( (heading_slide[position])=="Delivery"){
            change.setBackgroundColor(getColor(context,R.color.colorAccent));
        }
        if ( (heading_slide[position])=="Delivery 2"){
            change.setBackgroundColor(getColor(context,R.color.bak));
        }
        if ( (heading_slide[position])=="Assortment"){
            //change.setBackgroundResource(R.mipmap.assortment);
            change.setBackgroundColor(getColor(context,R.color.colorPrimaryDark));
        }

        //slide_imageView.setImageResource(image_slide[position]);
        slideHeading.setText(heading_slide[position]);
        slideDescription.setText(description_slide[position]);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}


