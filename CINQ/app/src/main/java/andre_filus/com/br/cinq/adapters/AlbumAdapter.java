package andre_filus.com.br.cinq.adapters;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.databinding.ItemAlbumBinding;
import andre_filus.com.br.cinq.models.Album;
import android.provider.MediaStore.Video.Thumbnails;

import com.squareup.picasso.Picasso;

/**
 * Created by André Filus on 08/09/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    ArrayList<Album> mAlbumList = new ArrayList<>();
    AlbumClickListener mListener;

    public AlbumAdapter(ArrayList<Album> albumList, AlbumClickListener mListener) {
        this.mAlbumList = albumList;
        this.mListener = mListener;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album albumItem = mAlbumList.get(position);
        holder.mViewHolderBinding.txtTitle.setText(albumItem.title);
        Picasso.get().load(albumItem.thumbnailUrl).into(holder.mViewHolderBinding.imgThumbnail);
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(albumItem.thumbnailUrl);
//        Bitmap bmThumbnail = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
//        if(mediaMetadataRetriever != null){
//            mediaMetadataRetriever.release();
//        }
//        holder.mViewHolderBinding.imgThumbnail.setImageBitmap(bmThumbnail);

//        holder.mViewHolderBinding.imageView.setText(albumItem.thumbnailUrl);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        ItemAlbumBinding mViewHolderBinding;
        public AlbumViewHolder(View itemView) {
            super(itemView);
            mViewHolderBinding = DataBindingUtil.bind(itemView);
        }
    }

    public interface AlbumClickListener {
    }

}
