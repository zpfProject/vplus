package avenwu.net.vplus.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import avenwu.net.vplus.R;
import avenwu.net.vplus.pojo.MovieItem;
import avenwu.net.vplus.view.DetailActivity;

/**
 * Created by chaobin on 7/1/15.
 */
public class PostInCategoryAdapter extends RecyclerView.Adapter<PostInCategoryAdapter.Holder> implements View.OnClickListener {

    public static class Holder extends RecyclerView.ViewHolder {
        public TextView label;
        public SimpleDraweeView pic;

        public Holder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.tv_info);
            pic = (SimpleDraweeView) itemView.findViewById(R.id.iv_pic);
        }
    }

    List<MovieItem.Data> mData;
    int mWidth = 0;
    int mSpanCount;

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.home_item, null);
        if (mWidth == 0) {
            mWidth = viewGroup.getResources().getDisplayMetrics().widthPixels / mSpanCount;
        }
        view.setLayoutParams(new RecyclerView.LayoutParams(mWidth, (int) (1.29f * mWidth)));
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        MovieItem.Data data = mData.get(position);
        holder.label.setText(data.title);
        holder.itemView.getLayoutParams().width = mWidth;
        float percent = 1;
        switch (position % 3) {
            case 0:
                percent = 1.29f;
                break;
            case 1:
                percent = 1.20f;
                break;
            case 2:
                percent = 1.16f;
                break;
        }
        final int height = (int) (percent * mWidth);
        holder.itemView.getLayoutParams().height = height;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(data.image))
            .setResizeOptions(new ResizeOptions(mWidth, height))
            .build();
        holder.pic.setController(Fresco.newDraweeControllerBuilder()
            .setOldController(holder.pic.getController())
            .setImageRequest(request)
            .build());
        holder.itemView.setTag(R.id.tv_info, encodeTag(data));
        holder.itemView.setOnClickListener(this);
    }

    private String encodeTag(MovieItem.Data data) {
        return data.title + ":" + data.postid;
    }

    private String[] decodeTag(String tag) {
        return tag.split(":");
    }

    @Override
    public void onClick(View v) {
        String[] info = decodeTag((String) v.getTag(R.id.tv_info));
        DetailActivity.launch(v.getContext(), info[0], info[1]);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<MovieItem.Data> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void appendData(List<MovieItem.Data> data) {
        if (mData != null) {
            mData.addAll(data);
        } else {
            mData = data;
        }
        notifyDataSetChanged();
    }

    public void setSpanCount(int spanCount) {
        mSpanCount = spanCount;
    }
}
