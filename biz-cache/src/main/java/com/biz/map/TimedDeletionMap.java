package com.biz.map;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 定时删除 Map
 *
 * @author francis
 * @create: 2023-04-23 17:14
 **/
public class TimedDeletionMap<K, V> {

    private Map<K, V> map;


    public TimedDeletionMap(Map<K, V> map) {
        this.map = map;
    }


    public static TimedDeletionMapBuilder builder() {
        return new TimedDeletionMapBuilder();
    }

    public static class TimedDeletionMapBuilder<K, V> {

        private Supplier<Map<K, V>> supplier;

        private TimedDeletionMapBuilder() {

        }

        public TimedDeletionMap build() {
            return new TimedDeletionMap(supplier.get());
        }
    }


}
