package com.biz.common.copier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;

/**
 *
 * @param <P>
 * @param <T>
 */
public abstract class AbstractCopier<P, T> implements Copier<P, T> {

    private final Class<?>[] aClass = new Class<?>[2];

    @Override
    public T copy(P p) {
        try {
            return copy(p, (T) getClass(CopyClassIndex.T).getDeclaredConstructor().newInstance());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T copy(P p, T t) {
        return t;
    }


    private Class<?> getClass(CopyClassIndex index) {
        if (aClass[index.index] == null) {
            TypeVariable<?> t = Copier.class.getTypeParameters()[index.index];
            aClass[index.index] = t.getClass();
        }
        return aClass[index.index];
    }

    private enum CopyClassIndex {
        P(0), T(1);
        private int index;
        CopyClassIndex(int i) {
            index = i;
        }
        int getCopyClassIndex() {
            return index;
        }
    }

}
