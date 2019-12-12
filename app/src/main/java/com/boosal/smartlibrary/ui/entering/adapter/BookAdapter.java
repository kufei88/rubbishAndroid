package com.boosal.smartlibrary.ui.entering.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.mvp.DataBase.contract.DataBaseContract;
import com.boosal.smartlibrary.mvp.DataBase.model.DataBaseModelImpl;
import com.boosal.smartlibrary.net.entity.local.book.Book;
import com.boosal.smartlibrary.utils.ImageViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context mContext = null;
    private List<Book> mDataList = new ArrayList<>();
    private DataBaseContract.Model model;
    private BookListener listener;

    public interface BookListener{
        void selectedBook(Book book);
    }

    public BookAdapter(BookListener listener) {
        this.listener = listener;
        model = new DataBaseModelImpl();
    }

    public void setDataList(List<Book> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<Book> dataList) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(dataList)) {
            notifyItemRangeInserted(lastIndex, dataList.size());
        }
    }

    public void notifyData(List<Book> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void cleanDataChanged() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycleview_importedbook, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Book book = mDataList.get(position);
        //单选某本书
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每一次清空其他状态
                for (Book _book : mDataList) {
                    _book.setChecked(false);
                }
                book.setChecked(!book.isChecked());
                if(null != listener){
                    listener.selectedBook(book);
                }
                notifyDataSetChanged();
            }
        });
        if (book.isChecked()) {
            holder.itemView.setBackgroundResource(R.drawable.bg_line_selected);
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.themeWhite));
        }
        //序号
        holder.id.setText(String.valueOf(book.getId()));
        //图片
        ImageViewUtils.loadImage(mContext, book.getImgpath(), holder.importedbookIvImg, R.drawable.shuju9787111496007);
        //书名
        holder.name.setText(book.getName());
        //isbn
        holder.isbn.setText(book.getIsbn());
        //出版社
        holder.press.setText(book.getPress());
        //出版时间
        holder.publishTime.setText(book.getPublish_time());
        //类别
        holder.category.setText(book.getCategory_name());
        //绑定次数
        int count = model.getCountOfLabelsByisbn(book.getIsbn());
        holder.bindTime.setText(String.valueOf(count));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.importedbook_iv_img)
        ImageView importedbookIvImg;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.isbn)
        TextView isbn;
        @BindView(R.id.press)
        TextView press;
        @BindView(R.id.publish_time)
        TextView publishTime;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.bind_time)
        TextView bindTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
