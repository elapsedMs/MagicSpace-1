package storm.magicspace.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.commonlib.common.view.RoundedImageView;
import storm.magicspace.R;
import storm.magicspace.activity.FreshHelpActivity;
import storm.magicspace.activity.mine.MyCollectionActivity;
import storm.magicspace.activity.mine.MyWorksActivity;
import storm.magicspace.adapter.ViewPagerAdatper;
import storm.magicspace.bean.CirclePic;
import storm.magicspace.bean.UserInfo;
import storm.magicspace.bean.httpBean.CirclePicResponse;
import storm.magicspace.bean.httpBean.UserInfoResponse;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.util.LocalSPUtil;

public class MyFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private EditText nameEt;
    private RoundedImageView advator;
    private TextView money;
    private TextView level;
    private TextView editNickNameTv;
    private ViewPager showPage;

    private List<CirclePic> circlePicList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void initData() {
        super.initData();

        new CirclePicTask().execute();

        GetAccountInfoTask task = new GetAccountInfoTask(getActivity());
        task.execute();
    }


    @Override
    public void initView(View view) {
        super.initView(view);

        editNickNameTv = findEventView(view, R.id.edit_my_account);
        findEventView(view, R.id.my_siv_wroks);
        findEventView(view, R.id.my_siv_collection);
        findEventView(view, R.id.my_siv_fresh_help);
        nameEt = findView(view, R.id.mine_tv_name);
        advator = findView(view, R.id.mine_ri_avatar);
        money = findView(view, R.id.money);
        level = findView(view, R.id.level);
        showPage = (ViewPager) findView(view, R.id.show_view_page);
        showPage.setOnPageChangeListener(this);
    }

    @Override
    public void onLocalClicked(int resId) {
        super.onLocalClicked(resId);

        switch (resId) {
            case R.id.my_siv_wroks:
                goToNext(MyWorksActivity.class);
                break;

            case R.id.my_siv_collection:
                goToNext(MyCollectionActivity.class);
                break;

            case R.id.edit_my_account:
                nameEt.setEnabled(true);
                break;

            case R.id.my_siv_fresh_help:
                goToNext(FreshHelpActivity.class);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class GetAccountInfoTask extends BaseASyncTask<Void, UserInfoResponse> {

        public GetAccountInfoTask(Context context) {
            super(context, true);
        }

        @Override
        public UserInfoResponse doRequest(Void param) {
            return HTTPManager.getAccountInfo();
        }

        @Override
        protected void onPostExecute(UserInfoResponse userInfoResponse) {
            super.onPostExecute(userInfoResponse);

            UserInfo data = userInfoResponse.data;
            if (data == null) {
                Toast.makeText(BaseApplication.getApplication(), "参数返回错误！", Toast.LENGTH_SHORT).show();
                return;
            }

            money.setText(data.getTotalCredit());
            level.setText(data.getTotalCredit());

            Picasso.with(getActivity()).load(data.getPortraitImage()).into(advator);
            nameEt.setText(LocalSPUtil.getAccountInfo().getUser_name());
        }
    }

    private class CirclePicTask extends BaseASyncTask<Void, CirclePicResponse> {
        @Override
        public CirclePicResponse doRequest(Void param) {
            return HTTPManager.getAlbumCirclePic();
        }

        @Override
        public void onSuccess(CirclePicResponse albumResponse) {
            super.onSuccess(albumResponse);
            circlePicList = albumResponse.data;
            imageViews.clear();
//            title.setText(circlePicList.get(0).getTitle());
//            desc.setText("没字段");
            for (int i = 0; i < circlePicList.size(); i++) {
                RoundedImageView imageView = new RoundedImageView(getActivity());
                imageView.setCornerRadius(100f);
                Picasso.with(getActivity()).load(circlePicList.get(i).getUrl()).into(imageView);
                imageViews.add(imageView);
            }

            showPage.setAdapter(new ViewPagerAdatper(imageViews));

        }
    }

}
