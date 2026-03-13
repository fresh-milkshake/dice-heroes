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

package com.vlaaad.dice.services;

import com.vlaaad.common.util.IStateDispatcher;
import com.vlaaad.common.util.StateDispatcher;
import com.vlaaad.common.util.futures.Future;
import com.vlaaad.common.util.futures.IFuture;
import com.vlaaad.dice.ServicesState;
import com.vlaaad.dice.api.services.IGameServices;
import com.vlaaad.dice.api.services.achievements.IGameAchievements;
import com.vlaaad.dice.api.services.cloud.ICloudSave;
import com.vlaaad.dice.api.services.multiplayer.IMultiplayer;

public class AndroidGameServices implements IGameServices {

    private final StateDispatcher<ServicesState> dispatcher = new StateDispatcher<ServicesState>(ServicesState.DISCONNECTED);
    private final ICloudSave cloudSave = new AndroidNoOpCloudSave();
    private final IMultiplayer multiplayer = new AndroidNoOpMultiplayer();

    @Override public boolean isSupported() {
        return false;
    }

    @Override public void signIn() {}

    @Override public void signOut() {}

    @Override public boolean isSignedIn() {
        return false;
    }

    @Override public IStateDispatcher<ServicesState> dispatcher() {
        return dispatcher;
    }

    @Override public IGameAchievements gameAchievements() {
        return IGameAchievements.NONE;
    }

    @Override public ICloudSave cloudSave() {
        return cloudSave;
    }

    @Override public IMultiplayer multiplayer() {
        return multiplayer;
    }

    @Override public void showLeaderboard(String leaderboardId) {}

    @Override public IFuture<Boolean> incrementScore(String leaderboardId, int by) {
        return Future.completed(false);
    }
}
