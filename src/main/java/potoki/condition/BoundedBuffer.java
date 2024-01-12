package potoki.condition;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public final class BoundedBuffer<T> {
    private final T[] elements;
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;
    private int size;
    @SuppressWarnings("unchecked")
    public BoundedBuffer(final int capacity) {
        this.elements=(T[]) new Object[capacity];
        this.lock=new ReentrantLock();
        this.notFull =this.lock.newCondition();
        this.notEmpty=this.lock.newCondition();
    }
    public boolean isFull(){
        this.lock.lock();
        try{
            return this.size==this.elements.length;
        }finally {
            this.lock.unlock();
        }
    }
    public boolean isEmpty(){
        this.lock.lock();
        try{
            return this.size==0;
        }finally {
            this.lock.unlock();
        }
    }
    public void put(final T element){
        this.lock.lock();
        try {
            while (this.isFull()) {
                this.notFull.await();
            }
            this.elements[this.size] = element;
            this.size++;
            System.out.printf("%s was put in buffer. Result buffer: %s%n",element,this);
            this.notEmpty.signal();
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }finally {
            this.lock.unlock();
        }
    }
    public T take(){
        this.lock.lock();
        try {
            while (this.isEmpty()) {
                this.notEmpty.await();
            }
            final T result = this.elements[this.size - 1];
            this.elements[this.size - 1] = null;
            this.size--;
            System.out.printf("%s was taken from buffer. Result buffer: %s%n",result,this);
            this.notFull.signal();
            return result;
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
            throw new RuntimeException(interruptedException);
        }finally {
            this.lock.unlock();
        }
    }

    @Override
    public String toString() {
        this.lock.lock();
        try {
            return "{"
                    + Arrays.stream(this.elements,0,this.size).
                    map(Objects::toString).
                    collect(Collectors.joining(","))
                    + "}";
        }finally {
            this.lock.unlock();
        }
    }
}
