package com.dhcc.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lituo on 2017/4/16 0016. 00:08 .
 * Mail：tony1994_vip@163.com
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHodler> {

    List<Bean> mList;

    public void setList(List<Bean> list) {
        mList = list;
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_address, parent, false);
        MyViewHodler hodler = new MyViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, final int position) {
        holder.mTextView.setText(mList.get(position).getAge() + "");
        holder.mButton.setText(mList.get(position).getName());
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onButtonClick(v, position);
            }
        });
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onTextClick(v, position);
            }
        });

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onItemClickListener.onCheckChange(buttonView, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        Button mButton;
        TextView mTextView;
        CheckBox mCheckBox;

        public MyViewHodler(View itemView) {
            super(itemView);
            mButton = (Button) itemView.findViewById(R.id.btn);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onButtonClick(View view, int position);

        void onTextClick(View view, int position);

        void onCheckChange(View view, int position);
    }
}
