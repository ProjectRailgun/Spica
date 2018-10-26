package co.bangumi.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;

/**
 * Created by roya on 2017/6/8.
 */

public class SeekBar extends AppCompatSeekBar {
    public SeekBar(Context context) {
        super(context, null);
    }

    public SeekBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        post(new Runnable() {
            @Override
            public void run() {
                Rect rc = new Rect();
                getHitRect(rc);
                rc.top += getResources().getDisplayMetrics().density * 24;
                ((View) getParent()).setTouchDelegate(new TouchDelegate(rc, SeekBar.this));
            }
        });
    }
}
