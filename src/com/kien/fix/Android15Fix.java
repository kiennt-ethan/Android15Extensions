package com.kien.fix;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import java.lang.reflect.Method; // Thư viện giúp "lách luật" biên dịch

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

// Đã xóa iconName để tránh lỗi "Exit code 8" do thiếu ảnh
@DesignerComponent(version = 1, 
    description = "Android 15 Reflection Fix", 
    category = ComponentCategory.EXTENSION, 
    nonVisible = true)
@SimpleObject(external = true)
public class Android15Fix extends AndroidNonvisibleComponent {

    private Activity activity;

    public Android15Fix(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Enable Transparent Status Bar")
    public void EnableTransparent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Window window = activity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                    
                    if (Build.VERSION.SDK_INT >= 30) {
                        // KỸ THUẬT REFLECTION:
                        // Gọi hàm bằng tên string thay vì gọi trực tiếp.
                        // Trình biên dịch cũ sẽ không báo lỗi, nhưng máy Android 15 vẫn hiểu và chạy.
                        Method m = Window.class.getMethod("setDecorFitsSystemWindows", boolean.class);
                        m.invoke(window, false);
                    } else {
                        // Code cho máy cũ
                        window.getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | 
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    }
                } catch (Exception e) {
                    // Nếu máy không hỗ trợ thì bỏ qua, không crash app
                    e.printStackTrace();
                }
            }
        });
    }
}
