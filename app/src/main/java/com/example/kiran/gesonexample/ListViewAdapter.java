package com.example.kiran.gesonexample;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.io.BufferedInputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.zip.Inflater;

/**
 * Created by Kiran on 08-03-2016.
 */
public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Gallery> galleryArrayList;
    LayoutInflater layoutInflater;
    TextView tvId, tvGalleryName;
    ImageView ivProfilePick;

    public ListViewAdapter(Context context, ArrayList<Gallery> galleryObject) {
        this.context = context;
        galleryArrayList = galleryObject;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.e("listview_arr_size",""+galleryArrayList.size());
        return galleryArrayList.size();
    }

    @Override
    public Gallery getItem(int position) {
        return galleryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_items, parent, false);
        tvId = (TextView) convertView.findViewById(R.id.tv_id_gson);
        tvGalleryName = (TextView) convertView.findViewById(R.id.tv_gallery_title_gson);
        ivProfilePick = (ImageView) convertView.findViewById(R.id.iv_profile_pick);
        tvId.setText(String.valueOf(galleryArrayList.get(position).id));
        tvGalleryName.setText(galleryArrayList.get(position).gallery_title);
        Picasso.with(context).load(galleryArrayList.get(position).images).into(ivProfilePick);
//        ivProfilePick.setImageBitmap(DownloadFullFromUrl(galleryArrayList.get(position).images));
        return convertView;
    }
    public Bitmap DownloadFullFromUrl(String imageFullURL) {
        Bitmap bmp=null;
        try {
            URL url=new URL(imageFullURL);
            bmp=BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}

