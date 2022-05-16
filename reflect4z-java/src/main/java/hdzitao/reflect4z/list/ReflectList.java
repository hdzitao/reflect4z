package hdzitao.reflect4z.list;

import hdzitao.reflect4z.Reflect;
import hdzitao.reflect4z.ReflectElement;

import java.util.*;

/**
 * 封装java原版反射对象数组
 * 主要List的操作代理给Arrays.asList(javaArray)
 *
 * @param <T> java原版反射对象
 * @param <R> java原版反射对象对应的ReflectElement
 */
public abstract class ReflectList<T, R extends ReflectElement<T>> implements Reflect<T[]>, List<R> {
    /**
     * 原版反射对象数组
     */
    protected final List<T> javaList;

    public ReflectList(T[] javaArray) {
        this.javaList = Arrays.asList(javaArray);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] java() {
        return (T[]) this.javaList.toArray();
    }

    /**
     * 原版对象转ReflectElement
     *
     * @param t 原版对象
     * @return 对应ReflectElement
     */
    protected abstract R warpElement(T t);

    /**
     * 自己实现的Iterator，代理原数组的Iterator
     */
    private class ReflectIterator implements Iterator<R> {
        protected final Iterator<T> javaIterator;

        public ReflectIterator(Iterator<T> javaIterator) {
            this.javaIterator = javaIterator;
        }

        @Override
        public boolean hasNext() {
            return this.javaIterator.hasNext();
        }

        @Override
        public R next() {
            return warpElement(this.javaIterator.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    /**
     * 自己实现的ListIterator，代理原数组的ListIterator
     */
    private class ListReflectIterator extends ReflectIterator implements ListIterator<R> {
        public ListReflectIterator(ListIterator<T> javaIterator) {
            super(javaIterator);
        }

        @Override
        public boolean hasPrevious() {
            return ((ListIterator<T>) this.javaIterator).hasPrevious();
        }

        @Override
        public R previous() {
            return warpElement(((ListIterator<T>) this.javaIterator).previous());
        }

        @Override
        public int nextIndex() {
            return ((ListIterator<T>) this.javaIterator).nextIndex();
        }

        @Override
        public int previousIndex() {
            return ((ListIterator<T>) this.javaIterator).previousIndex();
        }

        @Override
        public void set(R r) {
            throw new UnsupportedOperationException("set");
        }

        @Override
        public void add(R r) {
            throw new UnsupportedOperationException("add");
        }
    }


    @Override
    public int size() {
        return this.javaList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.javaList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.javaList.contains(o);
    }

    @Override
    public Iterator<R> iterator() {
        return new ReflectIterator(this.javaList.iterator());
    }

    @Override
    public Object[] toArray() {
        return this.javaList.toArray();
    }

    @Override
    public <TT> TT[] toArray(TT[] a) {
        return this.javaList.toArray(a);
    }

    @Override
    public boolean add(R r) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.javaList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends R> c) {
        throw new UnsupportedOperationException("addAll");
    }

    @Override
    public boolean addAll(int index, Collection<? extends R> c) {
        throw new UnsupportedOperationException("addAll");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    public R get(int index) {
        return warpElement(this.javaList.get(index));
    }

    @Override
    public R set(int index, R element) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public void add(int index, R element) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    public R remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public int indexOf(Object o) {
        return this.javaList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.javaList.lastIndexOf(o);
    }

    @Override
    public ListIterator<R> listIterator() {
        return new ListReflectIterator(this.javaList.listIterator());
    }

    @Override
    public ListIterator<R> listIterator(int index) {
        return new ListReflectIterator(this.javaList.listIterator(index));
    }

    @Override
    public List<R> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }
}
