import java.util.concurrent.Semaphore;

public class SemaphoreABBuffer<T> {

    private int capacity;
    private int n;
    private T[] buffer;
    private int[] cnt;
    private Semaphore[] mutexC;
    private int[] readIndex;
    private int writeIndex;
    private Semaphore[] full;
    private Semaphore empty;
    private Semaphore mutexP;
    public SemaphoreABBuffer(int capacity,int n){
        this.capacity=capacity;
        this.n=n;

        buffer= (T[]) new Object[capacity];
        cnt=new int[capacity];
        mutexC=new Semaphore[capacity];
        readIndex=new int[n];
        full=new Semaphore[n];
        empty=new Semaphore(capacity);
        mutexP=new Semaphore(1,true);
        for(int i=0;i<full.length;i++)
        {
            full[i]=new Semaphore(0);
        }
        for(int i=0;i< mutexC.length;i++)
        {
            mutexC[i]=new Semaphore(1);
        }

    }
    public void put(T item)
    {
        empty.acquireUninterruptibly();
        mutexP.acquireUninterruptibly();
        buffer[writeIndex]=item;
        writeIndex=(writeIndex+1)%capacity;
        mutexP.release();
        for(int i=0;i< full.length;i++)
            full[i].release();
    }

    public T get(int id)
    {
        full[id].acquireUninterruptibly();
        T item=buffer[readIndex[id]];

        mutexC[readIndex[id]].acquireUninterruptibly();
        if(++cnt[readIndex[id]]==n)
        {
            cnt[readIndex[id]]=0;

            empty.release();
        }
        mutexC[readIndex[id]].release();
        readIndex[id]++;

        return item;
    }

}
