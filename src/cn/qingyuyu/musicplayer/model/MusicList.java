package cn.qingyuyu.musicplayer.model;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.*;

public class MusicList implements ObservableList ,Serializable {

    private ArrayList<MusicModel> list=new ArrayList<MusicModel>();


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
        return false;
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public boolean add(Object o) {
        if(o instanceof MusicModel)
        {
            MusicModel mm=(MusicModel) o;
            list.add(mm);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return     list.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return list.addAll(index,c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return list.retainAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return list.toArray(a);
    }


    @Override
    public Object get(int index) {
        return list.get(index).getMusicName();
    }

    @Override
    public Object set(int index, Object element) {
        if(element instanceof MusicModel) {
            MusicModel mm=(MusicModel) element;
            return list.set(index, mm);
        }
        else
            return null;
    }

    @Override
    public void add(int index, Object element) {
        if(element instanceof MusicModel) {
            MusicModel mm=(MusicModel) element;
            list.add(index,mm);
        }
    }

    @Override
    public Object remove(int index) {
        return list.remove(index);
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
    public ListIterator listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    public String getMusicPath(int index)
    {
        return list.get(index).getMusicPath();
    }


    @Override
    public void addListener(ListChangeListener listener) {

    }

    @Override
    public void removeListener(ListChangeListener listener) {

    }

    @Override
    public boolean addAll(Object[] elements) {
        return false;
    }

    @Override
    public boolean setAll(Object[] elements) {
        return false;
    }

    @Override
    public boolean setAll(Collection col) {
        return false;
    }

    @Override
    public boolean removeAll(Object[] elements) {
      return   list.removeAll(Arrays.asList(elements));
    }

    @Override
    public boolean retainAll(Object[] elements) {
        return false;
    }

    @Override
    public void remove(int from, int to) {
        for(;from<=to;from++)
        list.remove(from);
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
