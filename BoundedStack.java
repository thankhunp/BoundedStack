import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * BoundedStack เป็น Arraylist ที่เอาไว้เก็บข้อมูลแบบ Last in first out
 */
public class BoundedStack {
    // ===== representation =====
    private final List<String> elements ; //private final String[] elements ;
    private final int capacity;

    //AF(elements,capacity) =
    //RI
    // -
    // -

    private void checkRep() {
        assert elements != null : "elements ต้องไม่เป็น null";
        assert capacity > 0 : "capacity ต้องมากกว่า 0";
        assert elements.size() <= capacity : "จำนวนข้อมูลเกินความจุ";
        Set<String> seen = new HashSet<>();
        for (String element : elements) {
            assert element != null;
            assert element != "";
            assert seen.add(element) : "ข้อมูลซ้ำ: " + element;
        }
    }

    // ===== Creator =====

    /**
     * สร้างสแตกว่าง
     * @param capacity
     */
    public BoundedStack(int capacity){
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        checkRep();
    }

    /**
     * 
     * @param s
     */
    public void push (String s){

    }

    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    public int capacity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'capacity'");
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    public boolean isFull() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFull'");
    }

    public String peek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peek'");
    }

    public String pop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pop'");
    }

    public BoundedStack copy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }
    }