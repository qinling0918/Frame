package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.protocol698.data.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

/**
 * Created by qinling on 2018/5/12 17:34
 * Description:
 * SEQUENCE OF Data（见6.3.3.1　）
 * 数组的元素在对象属性或方法的描述中定义
 */
public class Array<E extends Data> extends Data implements List<E> {
    private final ArrayList<E> list;

    @Override
    public int dataType() {
        return 1;
    }

    @Override
    public String toHexString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            // todo 是不是需要根据 数据类型，将特殊数据长度计算出来，
            // 或者在每一种非固定长度的数据类型都对应添加其字节数
            stringBuilder
                    .append(get(i).dataTypeStr())
                    .append(get(i).toHexString());
        }
        return stringBuilder.toString().toUpperCase(Locale.CHINA);
    }

    public Array() {
        list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return list.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        return list.addAll(i, collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int i) {
        return list.get(i);
    }

    @Override
    public E set(int i, E e) {
        return list.set(i, e);
    }

    @Override
    public void add(int i, E e) {
        list.add(i, e);
    }

    @Override
    public E remove(int i) {
        return list.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return list.listIterator(i);
    }

    @Override
    public List<E> subList(int i, int i1) {
        return list.subList(i, i1);
    }
}
