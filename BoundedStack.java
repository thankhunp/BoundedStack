import java.util.*;

/**
 * BoundedStack เป็น Arraylist ที่เอาไว้เก็บข้อมูลแบบ Last in first out
 */
public class BoundedStack {
    private final List<String> elements ; //private final String[] elements ;
    private final int capacity;

    //AF(elements,capacity) =
    //RI
    // -
    // -

    /**
     * 
     * @param capacity
     */
    public BoundedStack(int capacity){
        this.elements = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * 
     * @param s
     */
    public void push (String s){

    }
    }