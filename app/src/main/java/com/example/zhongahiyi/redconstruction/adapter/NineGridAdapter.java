package com.example.zhongahiyi.redconstruction.adapter;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.bean.Comment;
import com.example.zhongahiyi.redconstruction.bean.NineGridModel;
import com.example.zhongahiyi.redconstruction.view.NineGridTestLayout;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.util.V;


public class NineGridAdapter extends RecyclerView.Adapter<NineGridAdapter.ViewHolder> {

    private Context mContext;
    private List<NineGridModel> mList;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;

    private OnCommentListener mCommentListener;

    private TextView like,comment;

    public NineGridAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }


    public interface OnCommentListener{
        void onComment(int position);
    }

    public void setList(List<NineGridModel> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.item_nine_grid, parent, false);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
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
        holder.updateComment( model );
        holder.mMoreView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMore( v );
            }
        } );
        if (comment != null){
            comment.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCommentListener != null) {
                        mCommentListener.onComment( position );
                        if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                            mMorePopupWindow.dismiss();
                        }
                    }
                }
            } );
        }
    }

    @Override
    public int getItemCount() {
        return getListSize(mList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        TextView mName, mContent,mTime; //姓名，具体的内容，时间
        ImageView mAvatar;  //头像
        LinearLayout mCommentLayout;
        View mMoreView;

        private ViewHolder(View itemView) {
            super(itemView);
            layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            mAvatar = (ImageView) itemView.findViewById( R.id.friendCircle_imageView );
            mName = (TextView) itemView.findViewById( R.id.mName );
            mContent = (TextView) itemView.findViewById( R.id.mContent );
            mTime = (TextView) itemView.findViewById( R.id.mTime );
            mCommentLayout = (LinearLayout) itemView.findViewById( R.id.comment_layout );
            mMoreView = itemView.findViewById( R.id.more_btn );
        }


        //更新评论布局
        private void updateComment(NineGridModel nineGridModel){
            if (nineGridModel.hasComment()){
                mTime.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.bg_top );;
                mCommentLayout.removeAllViews();
                mCommentLayout.setVisibility( View.VISIBLE );

                for (Comment c : nineGridModel.getComments()) {
                    TextView  textView = new TextView( mContext );
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT ) ));
                    textView.setBackgroundColor( mContent.getResources().getColor( R.color.colorCommentLayoutBg ));
                    textView.setTextSize( 16 );
                    textView.setPadding( 5,2,0,3 );
                    textView.setLineSpacing(3,(float) 1.5);
                    textView.setText( c.getComment());
                    mCommentLayout.addView( textView );
                }
            }else {
                mTime.setCompoundDrawablesWithIntrinsicBounds( 0,0,0,0 );
                mCommentLayout.setVisibility( View.GONE );
            }
        }
    }

    public void setCommentListener(OnCommentListener l) {
        this.mCommentListener = l;
    }

    private int getListSize(List<NineGridModel> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    //弹出赞跟评论框
    private void showMore(View moreBtnview){
        if (mMorePopupWindow == null){
            LayoutInflater li = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View content = li.inflate( R.layout.layout_more,null,false ); //自定义布局

            mMorePopupWindow = new PopupWindow( content,ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);  //自适应大小
            mMorePopupWindow.setBackgroundDrawable( new BitmapDrawable( ) );
            mMorePopupWindow.setOutsideTouchable( true);//这里设置显示PopuWindow之后在外面点击是否有效。
            mMorePopupWindow.setTouchable( true );  // 设置是否可被点击

            content.measure( View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getHeight();

            View parent = mMorePopupWindow.getContentView();  //获取当前布局
            like = (TextView) parent.findViewById( R.id.like );
            comment = (TextView) parent.findViewById( R.id.comment );
        }

        if (mMorePopupWindow.isShowing()){
            mMorePopupWindow.dismiss();
        }else {
            int heightMoreBtnView = moreBtnview.getHeight();

            mMorePopupWindow.showAsDropDown( moreBtnview,-mShowMorePopupWindowWidth,
                    -(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
        }
    }
}
