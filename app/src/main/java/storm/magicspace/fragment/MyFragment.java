package storm.magicspace.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.commonlib.common.view.RoundedImageView;
import storm.magicspace.R;
import storm.magicspace.activity.FreshHelpActivity;
import storm.magicspace.activity.mine.MyCollectionActivity;
import storm.magicspace.activity.mine.MyWorksActivity;
import storm.magicspace.bean.UserInfo;
import storm.magicspace.bean.httpBean.UserInfoResponse;
import storm.magicspace.http.HTTPManager;
import storm.magicspace.util.LocalSPUtil;

public class MyFragment extends BaseFragment {

    private EditText nameEt;
    private RoundedImageView advator;
    private TextView money;
    private TextView level;
    private TextView editNickNameTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void initData() {
        super.initData();

        GetAccountInfoTask task = new GetAccountInfoTask(getActivity());
        task.execute();
    }


    @Override
    public void initView(View view) {
        super.initView(view);

        editNickNameTv = (TextView) findEventView(view, R.id.edit_my_account);
        findEventView(view, R.id.my_siv_wroks);
        findEventView(view, R.id.my_siv_collection);
        findEventView(view, R.id.my_siv_fresh_help);
        nameEt = (EditText) findView(view, R.id.mine_tv_name);
        advator = (RoundedImageView) findView(view, R.id.mine_ri_avatar);
        money = (TextView) findView(view, R.id.money);
        level = (TextView) findView(view, R.id.level);
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

}
