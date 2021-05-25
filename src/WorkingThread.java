import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class WorkingThread extends Thread{
    private Socket client;
    private Map<String,SemaphoreABBuffer<Goods>> map;
    private List<UnresolvedRequest> unresolvedRequestList;
    private Semaphore semaphore;
    public WorkingThread(Map<String,SemaphoreABBuffer<Goods>> map, Socket client,Semaphore semaphore)
    {
        this.semaphore=semaphore;
        this.map=map;
        this.client=client;
    }
    @Override
    public void run() {
        try(ObjectInputStream oin=new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oout=new ObjectOutputStream(client.getOutputStream());) {

            String op=(String) oin.readObject();
            switch(op)
            {
                case "put":{
                    oout.writeObject("op_OK");
                    Goods goods=(Goods) oin.readObject();
                    oout.writeObject("OK");
                    SemaphoreABBuffer<Goods> buffer=new SemaphoreABBuffer<>(5,3);
                    map.put(goods.getName(),buffer);
                    buffer.put(goods);
                    System.out.println(goods.getName());
                    semaphore.release();
                    break;
                }
                case "get":{
                    oout.writeObject("op_OK");
                    int id=(Integer) oin.readObject();
                    oout.writeObject("OK");
                    String name=(String) oin.readObject();
                    while(!map.containsKey(name))
                    {
                        semaphore.acquireUninterruptibly();
                    }
                    Goods goods=map.get(name).get(id);
                    oout.writeObject(goods);
                    oin.readObject();
                    break;
                }
                default:
                    oout.writeObject("not ok");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
