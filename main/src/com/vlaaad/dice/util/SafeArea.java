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

package com.vlaaad.dice.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Android display cutout and system bar insets translated to stage units.
 */
public class SafeArea {

    private static volatile int leftPx;
    private static volatile int topPx;
    private static volatile int rightPx;
    private static volatile int bottomPx;

    private SafeArea() {
    }

    public static void setInsets(int left, int top, int right, int bottom) {
        leftPx = Math.max(0, left);
        topPx = Math.max(0, top);
        rightPx = Math.max(0, right);
        bottomPx = Math.max(0, bottom);
    }

    public static float left(Stage stage) {
        return toStageUnits(leftPx, stage);
    }

    public static float top(Stage stage) {
        return toStageUnits(topPx, stage);
    }

    public static float right(Stage stage) {
        return toStageUnits(rightPx, stage);
    }

    public static float bottom(Stage stage) {
        return toStageUnits(bottomPx, stage);
    }

    private static float toStageUnits(int valuePx, Stage stage) {
        if (stage == null || stage.getViewport() == null) {
            return valuePx;
        }
        if (Gdx.graphics.getHeight() == 0) {
            return valuePx;
        }
        return valuePx * stage.getHeight() / Gdx.graphics.getHeight();
    }
}
