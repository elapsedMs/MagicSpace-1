package storm.magicspace.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import storm.magicspace.R;
import storm.magicspace.bean.Album;
import storm.magicspace.http.URLConstant;
import storm.magicspace.view.AlbumPicView;

import static storm.commonlib.common.BaseApplication.getApplication;

/**
 * Created by gdq on 16/6/29.
 */
public class WorksAdapter extends BaseAdapter {
    List<Album> list;
    Context context;

    public WorksAdapter(List<Album> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.d("zzz", " getCount 个数：" + list.size());
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.view_work, null);
            holder = new ViewHolder();
            holder.divider = (LinearLayout) convertView.findViewById(R.id.view_divider);
            if (list.size() == 1)
                holder.divider.setVisibility(View.GONE);

            holder.albumPicView = (AlbumPicView) convertView.findViewById(R.id.apv_left);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name);
            holder.descTv = (TextView) convertView.findViewById(R.id.desc);
            holder.countTv = (TextView) convertView.findViewById(R.id.download_count);
            holder.btnTv = (TextView) convertView.findViewById(R.id.btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position) != null) {
            final Album album = list.get(position);
            Picasso.with(context).load(album.getThumbImageUrl() == null ? "" : album.getThumbImageUrl()).into(holder.albumPicView.getBgIv());
            holder.albumPicView.setCollectTimes(album.getCommentCount());
            holder.albumPicView.setSupportTimes(album.getAppreciateCount());
            holder.nameTv.setText(album.getNickName() == null ? "" : album.getNickName());
            holder.descTv.setText(album.getDescription() == null ? "" : album.getDescription());
            holder.countTv.setText("");
            holder.btnTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "跳转开发中", 1).show();
//                    showShare("http://app.stemmind.com/vr/html/gamedetail.php?c=" + album.getContentId());
                    setShare("http://app.stemmind.com/vr/html/gamedetail.php?c=" + album.getContentId());
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        public AlbumPicView albumPicView;
        public TextView nameTv;
        public TextView descTv;
        public TextView countTv;
        public TextView btnTv;
        public LinearLayout divider;
    }


    private void showShare(String mContentId) {
        ShareSDK.initSDK(getApplication());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("魔fun全景挖彩蛋");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(URLConstant.SHARED_URL + mContentId);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(context.getString(R.string.shared_content));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://app.stemmind.com/vr/a/tour.html");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("快来评论一番吧");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getApplication().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(URLConstant.SHARED_URL + mContentId);

// 启动分享GUI
        oks.show(getApplication());
    }

    public void setShare(String mContentId){

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction((Activity) context).setDisplayList(displaylist)
                .withText( getApplication().getString(R.string.shared_content) )
                .withTitle("魔fun全景挖彩蛋")
                .withTargetUrl(URLConstant.SHARED_URL + mContentId)
                .open();
    }
}
