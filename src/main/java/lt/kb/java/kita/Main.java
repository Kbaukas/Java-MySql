package lt.kb.java.kita;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("1yes");
        List<Figura> figuros=new ArrayList<>();
        figuros.add(new Figura(123,"vardas1",true));
        figuros.add(new Figura(323,"vardas2",false));
        figuros.add(new Figura(225,"vardas3",true));
        Gson gson=new Gson();
        System.out.println(gson.toJson(figuros));
    }
}
 class Figura{
    private int a;
    private String name;
    private boolean x;

    public Figura(int a, String name, boolean x) {
        this.a = a;
        this.name = name;
        this.x = x;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isX() {
        return x;
    }

    public void setX(boolean x) {
        this.x = x;
    }
}