package superhelo.icrutils.utils;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public final class ListUtils {

    private ListUtils() {
    }

    public static <T> List<T> mergeList(List<? extends T> to, List<? extends T> from) {
        List<T> list = Lists.newArrayList(to.iterator());
        list.addAll(from);
        return list;
    }

    public static <T> List<T> addObjectToListHead(T object, List<T> list) {
        List<T> l = new ArrayList<>(1 + list.size());
        l.add(object);
        l.addAll(list);
        return l;
    }

}
