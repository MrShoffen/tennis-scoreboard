package org.mrshoffen.entity.domain;

public enum PlayerNumber {
    ONE,
    TWO;

    PlayerNumber opponent() {
        return this == ONE ? TWO : ONE;
    }
}
