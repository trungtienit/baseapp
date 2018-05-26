package com.example.trantien.appreview.base.drawer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trantien.appreview.R;
import com.example.trantien.appreview.base.AppConstants;
import com.example.trantien.appreview.base.drawer.dto.BodyDTO;
import com.example.trantien.appreview.base.drawer.dto.HeaderDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public List<Object> mList;
    private int currentPos;

    public MenuAdapter(Context context, List<Object> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    public OnItemClickListener onItemClickedListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == AppConstants.MENU_HEADER) {
            v = LayoutInflater.from(mContext).inflate(R.layout.hd_menu, parent, false);
            return new HeaderHolder(v);
        }

        v = LayoutInflater.from(mContext).inflate(R.layout.bd_menu, parent, false);
        return new BodyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==AppConstants.MENU_HEADER){
            final HeaderHolder header = (HeaderHolder) holder;
            header.bind((HeaderDTO) mList.get(position));
        }
        else {
            BodyHolder body = (BodyHolder) holder;
            body.bind((BodyDTO) mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public interface OnItemClickListener {

        void onHeaderClicked(boolean isSelected);

        void onBodyClick(int position);

    }

    @Override
    public int getItemViewType(int position) {
        Object object = mList.get(position);
        if (object instanceof HeaderDTO) {
            return AppConstants.MENU_HEADER;
        } else
            return AppConstants.MENU_BODY;
    }

    public class HeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.menu_avatar)
        CircleImageView civLogo;

        @BindView(R.id.menu_name)
        TextView tvNameHeader;

        public HeaderHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickedListener.onHeaderClicked(((HeaderDTO) mList.get(getAdapterPosition())).isSelected);
        }

        public void bind(HeaderDTO headerDTO) {

            if(headerDTO.url=="NULL")
                civLogo.setImageResource(R.drawable.ic_launcher_background);
            else {
                String url = headerDTO.url;
                Glide.with(mContext)
                        .load(url)
                        .into(civLogo);
            }



            tvNameHeader.setText(headerDTO.title);
            tvNameHeader.setSelected(true);
        }
    }

    public class BodyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //region Bind View
        @BindView(R.id.container)
        ConstraintLayout mContainer;

        @BindView(R.id.fl_icon)
        FrameLayout mContainerIcon;

        @BindView(R.id.menu_tv_icon)
        TextView tvIcon;

        @BindView(R.id.menu_iv_icon)
        ImageView imvIcon;

        @BindView(R.id.menu_title)
        TextView tvTitle;
        //endregion


        public BodyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(BodyDTO bodyDTO) {
            tvTitle.setText(bodyDTO.title);
            imvIcon.setImageResource(bodyDTO.icon);
            boolean isSelected = bodyDTO.isSelected;

            mContainer.setSelected(isSelected);
            if (isSelected) {
                currentPos = getAdapterPosition();
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }

        }

        @Override
        public void onClick(View v) {
            onItemClickedListener.onBodyClick(getAdapterPosition());
        }
    }
}
