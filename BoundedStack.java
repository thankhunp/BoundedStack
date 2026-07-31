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
        for (String element : elements) {
            assert element != null : "element ต้องไม่เป็น null";
            assert !element.isEmpty() : "element ต้องไม่เป็น empty string";
        }
    }

    // ===== Creator =====

    /**
     * สร้างสแตกว่าง
     * @param capacity
     */
    public BoundedStack(int capacity){
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        checkRep();
    }

    /**
     * 
     * @param s
     */
    public void push (String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("value must be non-empty");
        }
        if (isFull()) {
            throw new IllegalArgumentException("stack is full");
        }
        elements.add(s);
        checkRep();
    }

    public String pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty");
        }
        String top = elements.remove(elements.size() - 1);
        checkRep();
        return top;
    }

    public String peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty");
        }
        return elements.get(elements.size() - 1);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean isFull() {
        return elements.size() == capacity;
    }

    public int capacity() {
        return capacity;
    }

}