package org.roysin.filepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/8/17.
 */
public class FilePickerView extends FrameLayout {

    ListView mListview;
    FilePickerAdapter mAdapter;
    FileBrowser browser;
    Context mContext;
    public FilePickerView(Context context) {
        super(context);
        mContext =context;
        LayoutInflater inflater= LayoutInflater.from(context);
        inflater.inflate(R.layout.file_picker_view,this,true);
        mListview= (ListView) findViewById(R.id.file_picker_view_list);
        mAdapter= new FilePickerAdapter();
        browser=FileBrowser.getInstance(mContext);
        mAdapter.setAdapterData(browser.loadDir("/"));
        mListview.setAdapter(mAdapter);

    }

    public FilePickerView(Context context, AttributeSet attrs) {

        super(context);
        mContext =context;
        LayoutInflater inflater= LayoutInflater.from(context);
        inflater.inflate(R.layout.file_picker_view,this,true);
        mListview= (ListView) findViewById(R.id.file_picker_view_list);
        mAdapter= new FilePickerAdapter();
        browser=FileBrowser.getInstance(mContext);
        mAdapter.setAdapterData(browser.loadDir("/"));
        mListview.setAdapter(mAdapter);
    }

    public FilePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context);
        mContext =context;
        LayoutInflater inflater= LayoutInflater.from(context);
        inflater.inflate(R.layout.file_picker_view,this,true);
        mListview= (ListView) findViewById(R.id.file_picker_view_list);
        mAdapter= new FilePickerAdapter();
        browser=FileBrowser.getInstance(mContext);
        mAdapter.setAdapterData(browser.loadDir("/"));
        mListview.setAdapter(mAdapter);
    }


    public class FilePickerAdapter extends BaseAdapter{


        private List<FileItemData> mAdapterData;
        @Override
        public int getCount() {
            if(getAdapterData()!=null){
                return getAdapterData().size();
            }
            return 0;
        }

        @Override
        public FileItemData getItem(int position) {
            if(getAdapterData()!=null){
                return getAdapterData().get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        private List<FileItemData> getAdapterData(){
            return mAdapterData;
        }
        public void setAdapterData(List<FileItemData> data){
            this.mAdapterData =data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(mContext);
                View itemView = inflater.inflate(R.layout.file_picker_view_list_view, parent, false);
                holder =new ViewHolder(itemView);
                convertView=itemView;
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            FileItemData data = getItem(position);
            holder.tvPath.setText(data.getPath());
            if(data.getType()==FileItemData.TYPE_DIR){
                holder.tvSize.setText(String.valueOf(data.getSize()));
                holder.icon.setImageResource(R.drawable.icon_folderblue);
            }else if(data.getType()==FileItemData.TYPE_FILE){
                holder.tvSize.setText(String.valueOf(data.getSizeWithUnit()));
                holder.icon.setImageResource(R.drawable.icon_default);
            }

            return convertView;
        }


        public class ViewHolder{

            public TextView tvPath;
            public TextView tvSize;
            public ImageView icon;

            public ViewHolder(View itemView){

                tvPath = (TextView) itemView.findViewById(R.id.tv_path);
                tvSize = (TextView) itemView.findViewById(R.id.tv_size);
                icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            }
        }

    }


}
