import java.util.List;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Server {


    public static void main(String[] args)
    {
        Map <String,SemaphoreABBuffer<Goods>> map=new HashMap<>();
        Semaphore semaphore=new Semaphore(0);
        while(true) {
            try (ServerSocket server = new ServerSocket(4001);) {
                Socket client = server.accept();
                new WorkingThread(map,client,semaphore).start();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
