package com.harush.zitoon.quoridor.core.model;

import java.util.List;
import java.util.stream.Collectors;

public interface CloneUtil {

    <T> T clone(T t, Object ... args);

    default <T> List<T> clone(List<T> list, Object... args) {
        return list.stream().map(elem -> clone(elem, args)).collect(Collectors.toList());
    }
}
