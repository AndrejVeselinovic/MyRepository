import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class UnresolvedRequest extends Thread{
    private Socket client;
    private String goodsName;
    Map <String,SemaphoreABBuffer<Goods>> map;
    private int id;
    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String name) {
        this.goodsName = name;
    }

    public UnresolvedRequest(Socket client, String name,Map <String,SemaphoreABBuffer<Goods>> map,int id){
        this.client=client;
        this.goodsName=name;
        this.map=map;
        this.id=id;
    }
    @Override
    public void run()
    {
        try(ObjectInputStream oin=new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oout=new ObjectOutputStream(client.getOutputStream());) {

            Goods goods = map.get(goodsName).get(id);
            oout.writeObject(goods);
            oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
