package com.harush.zitoon.quoridor.core.model;

interface Coordinate2PlayerActionConverter {

    PlayerAction toPlayerAction(Coordinate coordinate);

    PlayerAction toPlayerAction(Coordinate coordinate, Player player);
}