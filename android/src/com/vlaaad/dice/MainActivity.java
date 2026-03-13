/*
 * Dice heroes is a turn based rpg-strategy game where characters are dice.
 * Copyright (C) 2016 Vladislav Protsenko
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.vlaaad.dice;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.vlaaad.dice.services.AndroidMobileApi;
import com.vlaaad.dice.util.SafeArea;

public class MainActivity extends AndroidApplication {

    private Handler mainHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainHandler = new Handler(getMainLooper());

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useImmersiveMode = true;

        initialize(new DiceHeroes(new AndroidMobileApi(this)), config);
        configureWindow();
        applyImmersiveMode();
        observeInsets();
    }

    public Handler getMainHandler() {
        return mainHandler;
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyImmersiveMode();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            applyImmersiveMode();
        }
    }

    private void configureWindow() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(params);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
        }
    }

    private void applyImmersiveMode() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    private void observeInsets() {
        final View root = findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH && root != null) {
            root.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    int left = 0;
                    int top = 0;
                    int right = 0;
                    int bottom = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        left = Math.max(left, insets.getSystemWindowInsetLeft());
                        top = Math.max(top, insets.getSystemWindowInsetTop());
                        right = Math.max(right, insets.getSystemWindowInsetRight());
                        bottom = Math.max(bottom, insets.getSystemWindowInsetBottom());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && insets.getDisplayCutout() != null) {
                        left = Math.max(left, insets.getDisplayCutout().getSafeInsetLeft());
                        top = Math.max(top, insets.getDisplayCutout().getSafeInsetTop());
                        right = Math.max(right, insets.getDisplayCutout().getSafeInsetRight());
                        bottom = Math.max(bottom, insets.getDisplayCutout().getSafeInsetBottom());
                    }
                    SafeArea.setInsets(left, top, right, bottom);
                    if (com.badlogic.gdx.Gdx.app != null) {
                        com.badlogic.gdx.Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                if (com.badlogic.gdx.Gdx.app != null && com.badlogic.gdx.Gdx.app.getApplicationListener() != null) {
                                    com.badlogic.gdx.Gdx.app.getApplicationListener().resize(
                                        com.badlogic.gdx.Gdx.graphics.getWidth(),
                                        com.badlogic.gdx.Gdx.graphics.getHeight()
                                    );
                                }
                            }
                        });
                    }
                    return insets;
                }
            });
            root.requestApplyInsets();
        } else {
            SafeArea.setInsets(0, 0, 0, 0);
        }
    }
}
