import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketAB implements AB{
    protected Socket client;
    protected ObjectInputStream oin;
    protected ObjectOutputStream oout;
    @Override
    public boolean init(String host, int port) {
        boolean flag=true;
        try {
            client=new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
            flag=false;
        }

        try {
            oout=new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            flag=false;
        }
        try {
            oin=new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean close() {
        boolean flag=true;
        try {
            client.close();
        } catch (IOException e) {
//            e.printStackTrace();
            flag=false;
        }
        try {
            oin.close();
        } catch (IOException e) {
//            e.printStackTrace();
            flag=false;
        }
        try {
            oout.close();
        } catch (IOException e) {
//            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public void putGoods(String name, Goods goods) {
        goods.setName(name);
        try {
            oout.writeObject("put");
            String response=(String) oin.readObject();
            if(response.equals("op_OK"))
            {
                oout.writeObject(goods);
                oin.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Goods getGoods(String name,int id) {
        try {
            oout.writeObject("get");
            String response=(String) oin.readObject();
            if(response.equals("op_OK"))
            {
                oout.writeObject(id);
                response=(String) oin.readObject();
                if(!response.equals("OK"))
                    return null;
                oout.writeObject(name);
                Goods result=(Goods) oin.readObject();
                oout.writeObject("OK");
                return result;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
