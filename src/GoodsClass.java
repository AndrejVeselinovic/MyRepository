
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class GoodsClass implements Goods, Serializable {
    protected String name=null;
    protected List<String> body=new LinkedList<>();
    protected int readIndex;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String[] getBody() {
        String result[]=new String[body.size()];
        for(int i=0;i<body.size();i++)
            result[i]=body.get(i);
        return result;
    }

    @Override
    public void setBody(String[] body) {
        this.body.clear();
        for(String s:body)
            this.body.add(s);
    }

    @Override
    public String readLine() {
        if(readIndex==body.size())
            return null;
        return body.get(readIndex++);
    }

    @Override
    public void printLine(String body) {
        this.body.add(body);
    }

    @Override
    public int getNumLines() {
        return body.size();
    }

    @Override
    public void save(String name) {
        BufferedWriter bufferedWriter=null;
        try {
            bufferedWriter=new BufferedWriter(new FileWriter(new File(name+".txt")));
        for(String s:body) {
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(String name) {
        BufferedReader bufferedReader=null;
        try{
            bufferedReader=new BufferedReader(new FileReader(new File(name)));
            body.clear();
            while(true)
            {
                String newLine=bufferedReader.readLine();
                if(newLine==null)
                    return;
                body.add(newLine);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
