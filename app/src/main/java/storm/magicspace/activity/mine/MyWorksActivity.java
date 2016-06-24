package storm.magicspace.activity.mine;

import android.os.Bundle;
import android.view.View;

import storm.commonlib.common.base.BaseASyncTask;
import storm.commonlib.common.base.BaseActivity;
import storm.magicspace.R;
import storm.magicspace.bean.httpBean.MyWorksResponse;
import storm.magicspace.http.HTTPManager;

public class MyWorksActivity extends BaseActivity {

    public MyWorksActivity() {
        super(R.layout.activity_my_works);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("我的作品");
        setTitleLeftBtVisibility(View.VISIBLE);

        GetMyWorksTask task = new GetMyWorksTask();
        task.execute();
    }

    private class GetMyWorksTask extends BaseASyncTask<String, MyWorksResponse> {
        @Override
        public MyWorksResponse doRequest(String param) {
            return HTTPManager.getMyWorks("", "");
        }

        @Override
        protected void onPostExecute(MyWorksResponse myWorksResponse) {
            super.onPostExecute(myWorksResponse);
        }
    }
}
