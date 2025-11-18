package com.kien.fix;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;      // Đã thêm import View
import android.view.Window;
import android.view.WindowManager;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

// LƯU Ý: Đã xóa thuộc tính iconName để tránh lỗi thiếu ảnh
@DesignerComponent(version = 1, 
    description = "Android 15 Fix", 
    category = ComponentCategory.EXTENSION, 
    nonVisible = true) 
@SimpleObject(external = true)
public class Android15Fix extends AndroidNonvisibleComponent {

    private Activity activity;

    public Android15Fix(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Enable Transparent Status Bar (Android 15)")
    public void EnableTransparent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);

                if (Build.VERSION.SDK_INT >= 30) {
                    // Android 11+ (bao gồm 15)
                    window.setDecorFitsSystemWindows(false);
                } else {
                    // Android cũ: Dùng các flags truyền thống (5890 = Layout Stable + Fullscreen + Hide Nav)
                    window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | 
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
            }
        });
    }
}
