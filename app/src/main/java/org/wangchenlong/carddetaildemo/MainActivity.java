package org.wangchenlong.carddetaildemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.wangchenlong.carddetaildemo.cards.CardDetailActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转药品信息页面
     *
     * @param view 视图
     */
    public void gotoDragCard(View view) {
        CardDetailActivity.startCardDetail(this, "药品信息", 4, CardType.TYPE_DRAG);
    }

    /**
     * 跳转检查项目页面
     *
     * @param view 视图
     */
    public void gotoCheckCard(View view) {
        CardDetailActivity.startCardDetail(this, "检测项目", 4, CardType.TYPE_CHECK);
    }

    /**
     * 跳转手术项目页面
     *
     * @param view 视图
     */
    public void gotoOperationCard(View view) {
        CardDetailActivity.startCardDetail(this, "手术项目", 4, CardType.TYPE_OPERATION);
    }
}
