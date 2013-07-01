package jp.gr.java_conf.daisy.first_ai_programming.ch5;

import java.util.Collection;
import java.util.Iterator;

public class Util {
    public static <E> E getRandomElementFrom(Collection<E> collection) {
        int index = (int) (Math.random() * collection.size());
        Iterator<E> itr = collection.iterator();
        for (int i = 0; i < index; i++) {
            itr.next();
        }
        return itr.next();
    }

    public static <E> boolean removeIfContains(
            Collection<E> collection, E element) {
        if (collection.contains(element)) {
            collection.remove(element);
            return true;
        }
        return false;
    }
}
