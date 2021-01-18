package edu.tjrac.swant.baselib.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import edu.tjrac.swant.baselib.R;

public class SingleFragmentActivity extends BaseActivity {

    public static BaseFragment content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
//        if(content==null){
//            content = (T) T.instantiate(this, "fragment");
//        }
        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fl_content, content)
//                .commit();
                .add(R.id.fl_content, content)
//                .show(content)
                .commit();
    }

    public static void start(Context context, BaseFragment fragment) {
        content = fragment;
        context.startActivity(new Intent(context, SingleFragmentActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (content.backable()) {
            content.onBack();
        } else {
            super.onBackPressed();
        }
    }
}
