package com.kien.fix;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1, description = "Android 15 Fix", category = ComponentCategory.EXTENSION, nonVisible = true, iconName = "images/extension.png")
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
            public void run() {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                if (Build.VERSION.SDK_INT >= 30) {
                    window.setDecorFitsSystemWindows(false);
                } else {
                    window.getDecorView().setSystemUiVisibility(5890); // Flags gộp cho gọn
                }
            }
        });
    }
}
