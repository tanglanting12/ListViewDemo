package com.example.a20254863.listview;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.animation.Animator;
public class MyAdapter extends  ListViewAdapter<DataBean> {
    private Context mContext;
    private List<DataBean> mDatas;
    private LayoutInflater mInflater;
    public boolean flage = false;
    public Map<Integer, String> selected;
    private List<String> dataIds;
    private  float mInitBegin = 10000;
    private boolean  mIfReadyFinallyPlace = false;
    private int in = 1;
    private int out = 2;
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public MyAdapter(Context mContext, List<DataBean> mDatas) {
        super(mContext, mDatas, R.layout.list_item_data);
        selected = new HashMap<>();
        dataIds = new ArrayList<>();
    }
    public  void convert(ViewHolder holder,  final DataBean bean,int position) {
        AnimatorSet animSet = new AnimatorSet();
        if(mInitBegin > 0) {
            int[] location = new int[2];
            ((CheckBox) holder.getView(R.id.checkbox_operate_data)).getLocationOnScreen(location);
            mInitBegin = location[0];
        }
        if (bean != null) {
            ((TextView) holder.getView(R.id.text_title)).setText(bean.getTitle());
            ((TextView) holder.getView(R.id.text_desc)).setText(bean.getDesc());
            // 根据isSelected来设置checkbox的显示状况
            if (flage) {
              //  ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setChecked(bean.getIsCheck());
                ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setVisibility(View.VISIBLE);
                if(!mIfReadyFinallyPlace)
                    appearCheckBox( ((CheckBox) holder.getView(R.id.checkbox_operate_data)),in,animSet);
                else {
                    //  animSet.cancel();
                    ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setX(mInitBegin+100);
                    ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setAlpha(1);
                }
            } else {
                appearCheckBox( ((CheckBox) holder.getView(R.id.checkbox_operate_data)),out,animSet);
            }

            if (selected.containsKey(position))

                ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setChecked(true);
            else
                ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setChecked(false);

            ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setChecked(bean.isCheck);

            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            ((CheckBox) holder.getView(R.id.checkbox_operate_data)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isCheck) {
                        bean.isCheck = false;
                    } else {
                        bean.isCheck = true;
                    }
                }
            });
        }
    }
    public void appearCheckBox(final  CheckBox  mCheckBox,int direct ,AnimatorSet animSet ){
        ObjectAnimator anim1 = new ObjectAnimator() ;
        ObjectAnimator anim2  = new ObjectAnimator();
        if(direct == in ) {
            anim1 = ObjectAnimator.ofFloat(mCheckBox, "x",
                    mInitBegin + 200, mInitBegin + 100);
            anim2 = ObjectAnimator.ofFloat(mCheckBox, "alpha", 0f, 1f);
            animSet.setDuration(300);
            anim1.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    Log.e("tanglanting", "onAnimationEnd");
                    mIfReadyFinallyPlace = true;
                }
            });
        }
        if(direct == out ) {
            anim1 = ObjectAnimator.ofFloat(mCheckBox, "x",
                    mInitBegin + 100, mInitBegin + 200);
            anim2 = ObjectAnimator.ofFloat(mCheckBox, "alpha", 1f, 0f);
            animSet.setDuration(100);
            anim1.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    Log.e("tanglanting", "onAnimationEnd");
                    mIfReadyFinallyPlace = false;

                    mCheckBox.setVisibility(View.GONE);
                }
            });
        }
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

}
