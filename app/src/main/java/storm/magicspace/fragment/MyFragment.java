package storm.magicspace.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseFragment;
import storm.magicspace.R;
import storm.magicspace.activity.mine.MyCollectionActivity;
import storm.magicspace.activity.mine.MyWorksActivity;
import storm.magicspace.bean.httpBean.AccountInfoResponse;
import storm.magicspace.http.HTTPManager;

public class MyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void initData() {
        super.initData();

        GetAccountInfoTask task = new GetAccountInfoTask();
        task.execute();
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        findEventView(view, R.id.my_siv_wroks);
        findEventView(view, R.id.my_siv_collection);
        findEventView(view, R.id.my_siv_eggs);
        findEventView(view, R.id.my_siv_fresh_help);
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
        }
    }

    private class GetAccountInfoTask extends BaseASyncTask<Void, AccountInfoResponse> {
        @Override
        public AccountInfoResponse doRequest(Void param) {
            return HTTPManager.getAccountInfo();
        }

        @Override
        protected void onPostExecute(AccountInfoResponse accountInfoResponse) {
            super.onPostExecute(accountInfoResponse);
        }
    }
}
