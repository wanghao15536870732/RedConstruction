package com.example.zhongahiyi.redconstruction.adapter;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.bean.NineGridModel;
import com.example.zhongahiyi.redconstruction.view.NineGridTestLayout;

import java.util.List;


public class NineGridAdapter extends RecyclerView.Adapter<NineGridAdapter.ViewHolder> {

    private Context mContext;
    private List<NineGridModel> mList;
    private LayoutInflater inflater;


    public NineGridAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<NineGridModel> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.item_nine_grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NineGridModel model = mList.get( position );
        holder.layout.setIsShowAll(model.isShowAll);
        holder.layout.setUrlList(model.urlList);
        if (model.getAvatar() != null)
            Glide.with( mContext ).load( model.getAvatar()).into( holder.mAvatar  );
        if(model.getName() != null)
            holder.mName.setText( model.getName() );
        if(model.getContent() != null)
            holder.mContent.setText( model.getContent() );
        if(model.getTime() != null)
            holder.mTime.setText( model.getTime() );
    }

    @Override
    public int getItemCount() {
        return getListSize(mList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        TextView mName, mContent,mTime; //姓名，具体的内容，时间
        ImageView mAvatar;  //头像
        private ViewHolder(View itemView) {
            super(itemView);
            layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            mAvatar = (ImageView) itemView.findViewById( R.id.friendCircle_imageView );
            mName = (TextView) itemView.findViewById( R.id.mName );
            mContent = (TextView) itemView.findViewById( R.id.mContent );
            mTime = (TextView) itemView.findViewById( R.id.mTime );
        }
    }

    private int getListSize(List<NineGridModel> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
