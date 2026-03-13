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
import com.vlaaad.common.util.Option;
import com.vlaaad.common.util.StateDispatcher;
import com.vlaaad.common.util.futures.Future;
import com.vlaaad.common.util.futures.IFuture;
import com.vlaaad.dice.api.services.multiplayer.GameSession;
import com.vlaaad.dice.api.services.multiplayer.IMultiplayer;

public class AndroidNoOpMultiplayer implements IMultiplayer {

    private final StateDispatcher<Integer> invites = new StateDispatcher<Integer>(0);
    private final StateDispatcher<Option<GameSession>> currentSession =
        new StateDispatcher<Option<GameSession>>(Option.<GameSession>none());

    @Override public IStateDispatcher<Integer> invites() {
        return invites;
    }

    @Override public IStateDispatcher<Option<GameSession>> currentSession() {
        return currentSession;
    }

    @Override public IFuture<Void> inviteFriends(int playersToInvite, int variant) {
        return Future.completed();
    }

    @Override public IFuture<Void> displayInvitations() {
        return Future.completed();
    }

    @Override public IFuture<Void> quickMatch(int playersToInvite, int variant) {
        return Future.completed();
    }
}
