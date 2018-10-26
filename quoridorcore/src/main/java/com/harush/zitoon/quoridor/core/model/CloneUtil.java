package com.harush.zitoon.quoridor.core.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface CloneUtil {

    <T> T clone(T t, Class clazz, Object ... args);

    default <T> List<T> clone(List<T> list, Class clazz, Object ... args) {
        return list.stream().filter(Objects::nonNull).map(elem -> clone(elem, clazz, args)).collect(Collectors.toList());
    }
}
