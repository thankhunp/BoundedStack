import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * BoundedStack - ATD แทนรายการชุดข้อความที่ผู้ใช้กรอกเข้ามา
 * 
 * ค่านามธรรม (A): ลำดับของชุดข้อความ เช่น [แอปเปิ้ล, กล้วย, ส้ม]
 * 
 * ตัวอย่างการใช้งาน:
 * BoundedStack b = new BoundedStack();
 * b.puch("Bohemian Rhapsody");
 * b.push("Imagine");
 * System.out.println(b.size()); // 2
 */
public class BoundedStack {
    private final List<String> elements;
    private final int capacity;

    // Abstraction Function:
    // AF(elements,capacity) = เก็บรายการชุดข้อความ
    // elements = รายการชุดข้อความ
    // capacity = ความจุสูงสุดที่ใช้เก็บข้อความ

    // Representation Invariant:
    // ต้องมีรายการชุดข้อความอยู่จริง (ไม่เป็น null)
    // มีจำนวนชุดข้อความได้ไม่เกิน capacity
    // ไม่มีข้อความใดเป็น null
    // ไม่มีข้อความใดเป็นสตริงว่าง (ไม่เป็น "")
    // ข้อความห้ามซ้ำกัน
    // ห้ามมีอักษรพิเศษในข้อความ

    // Safety from rep exposure:
    // Copy ข้อมูลขาเข้าและขาออก
    // ประกาศ elements และ capacity เป็น final
    // เพื่อไม่ให้ผู้ใช้เข้าถึงหรือแก้ไขได้โดยตรง

    private void checkRep() {
        assert elements != null;
        assert elements.size() <= capacity;
        Set<String> seen = new HashSet<>();
        for (String s : elements) {
            assert s != null;
            assert !s.isEmpty();
            assert seen.add(s);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                assert Character.isLetterOrDigit(c) || c == ' ';
            }
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
     * สร้าง list จากชุดข้อความที่ผู้ใช้ให้มา
     * 
     * @param initial รายการชุดข้อความเริ่มต้น, ไม่ซ้ำกัน
     * @throws IllegalArgumentException ถ้า initial ผิดเงื่อนไข
     */
    public BoundedStack(List<String> initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.capacity = initial.size();
        Set<String> seen = new HashSet<>();
        for (String s : initial) {
            if (s == null) {
                throw new IllegalArgumentException();
            }
            if (s == "") {
                throw new IllegalArgumentException();
            }
            if (!seen.add(s)) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!Character.isLetterOrDigit(c) && c != ' ') {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.elements = new ArrayList<>(initial);
        checkRep();
    }

     // ===== Mutators =====

    /**
     * เพิ่มข้อความตำแหน่งสุดท้ายใน elements
     * 
     * @param information ข้อความ, ต้องไม่เป็น null ไม่เป็นสตริงว่าง
     *                    ไม่เป็นอักษรพิเศษ
     * @return true ถ้าเพิ่มข้อความสำเร็จ, false ถ้าเพิ่มข้อความไม่สำเร็จ
     * @throws IllegalArgumentException ถ้า information ผิดเงื่อนไข
     */
    public boolean push(String information) {

        if (information == null || information == "") {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < information.length(); i++) {
            char c = information.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                throw new IllegalArgumentException();
            }
        }
        if (elements.size() == capacity || elements.size() > capacity || elements.contains(information)) {
            return false;
        }

        elements.add(information);
        checkRep();
        return true;
    }

    /**
     * ลบข้อความออกจากตำแหน่งสุดท้าย elements และ คืนข้อความตำแหน่งสุดท้าย
     * 
     * @return ข้อความตำแหน่งสุดท้าย
     * @throws IndexOutOfBoundsException ถ้า เมื่อ elements ว่างอยู่
     *                                   (ไม่มีสมาชิกเลย)
     */
    public String pop() {
        if (elements.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        String s = elements.get(elements.size() - 1);
        elements.remove(elements.size() - 1);
        checkRep();
        return s;
    }

     // ===== Observers =====

    /**
     * คืนจำนวนชุดข้อความใน elements
     * 
     * @return จำนวนชุดข้อความใน elements
     */
    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean isFull() {
        return elements.size() == capacity;
    }

    /**
     * คืนค่าความจุสูงสุดที่ใช้เก็บข้อความ
     * 
     * @return ความจุสูงสุดที่ใช้เก็บข้อความ
     */
    public int getCapacity() {
        return this.capacity;
    }

     /**
     * คืนข้อความตำแหน่งสุดท้าย
     * 
     * @return ข้อความตำแหน่งสุดท้าย
     * @throws IndexOutOfBoundsException ถ้า เมื่อ elements ว่างอยู่
     *                                   (ไม่มีสมาชิกเลย)
     */
    public String peek() {
        if (elements.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * คืนรายการข้อความทั้งหมดตามลำดับ
     * 
     * @return รายการขชุดข้อความตามลำดับ
     */
    public List<String> getElements() {
        return new ArrayList<>(elements);
    }

    /**
     * ตรวจสอบว่ามีข้อความนี้อยู่ใน elements หรือไม่
     * 
     * @param information ข้อความ, ต้องไม่เป็น null ไม่เป็นสตริงว่าง
     *                    ไม่เป็นอักษรพิเศษ
     * @return true ถ้าพบข้อความ , false ถ้าไม่พบข้อความ
     * @throws IllegalArgumentException ถ้า information ผิดเงื่อนไข
     */
    public boolean contains(String information) {
        if (information == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < information.length(); i++) {
            char c = information.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                throw new IllegalArgumentException();
            }
        }
        return elements.contains(information);
    }

     // ===== Producer =====

    /**
     * คืนรายการข้อความใหม่ที่มีชุดข้อความเดิมแต่สลับลำดับ
     *
     * @return รายการชุดข้อความที่สลับลำดับแล้ว
     */
    public BoundedStack shuffled() {
        List<String> copy = new ArrayList<String>(elements);
        Collections.shuffle(copy);
        return new BoundedStack(copy);
    }
}