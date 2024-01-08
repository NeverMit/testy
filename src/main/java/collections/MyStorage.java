package collections;

import java.util.Collections;
import java.util.Iterator;

public class MyStorage<E> implements IStorage<E>{

    private E[] values;

    public MyStorage() {
        values=(E[]) new Object[0];
    }

    @Override
    public boolean add(E e) {
       try {
           E[] temp=values;
           values=(E[]) new Object[temp.length+1];
           System.arraycopy(temp,0,values,0,temp.length);
           values[values.length-1]=e;
           return true;
       }catch (ClassCastException classCastException){
           classCastException.printStackTrace();
       }
       return false;
    }

    @Override
    public void delete(int index) {
        try{
            E[] temp=values;
            values=(E[]) new Object[temp.length-1];
            System.arraycopy(temp,0,values,0,index);
            int amountElementAfterIndex=temp.length-index-1;
            System.arraycopy(temp,index+1,values,index,amountElementAfterIndex);
        }catch (ClassCastException classCastException){
            classCastException.printStackTrace();
        }
    }

    @Override
    public E get(int index) {
        return values[index];
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public void update(int index, E e) {
        values[index]=e;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>(values);
    }
}
