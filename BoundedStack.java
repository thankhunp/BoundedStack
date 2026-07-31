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
 * BoundedStack b = new BoundedStack(5);
 * b.push("Bohemian Rhapsody");
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
     *
     * @param capacity ความจุสูงสุดของสแตก
     */
    public BoundedStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be non-negative");
        }
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        checkRep();
    }

    /**
     * สร้าง stack จากรายการข้อความเริ่มต้น
     *
     * @param initial รายการชุดข้อความเริ่มต้น ไม่ซ้ำกัน
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
            if (s.equals("")) {
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
     * เพิ่มข้อความลงท้าย stack
     *
     * @param information ข้อความต้องไม่เป็น null ไม่เป็นสตริงว่าง
     *                    และไม่ใช้อักขระพิเศษ
     * @return true ถ้าเพิ่มข้อความสำเร็จ, false ถ้าไม่สำเร็จ
     * @throws IllegalArgumentException ถ้า information ผิดเงื่อนไข
     */
    public boolean push(String information) {

        if (information == null || information.equals("")) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < information.length(); i++) {
            char c = information.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                throw new IllegalArgumentException();
            }
        }
        if (elements.size() >= capacity || elements.contains(information)) {
            return false;
        }
        elements.add(information);
        checkRep();
        return true;
    }

    /**
     * ลบข้อความตัวบนสุดของ stack และคืนค่า
     *
     * @return ข้อความตัวบนสุด
     * @throws IndexOutOfBoundsException ถ้า stack ว่างอยู่
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
     * คืนจำนวนข้อความใน stack
     *
     * @return จำนวนข้อความใน stack
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
     * คืนค่าความจุสูงสุดของ stack
     *
     * @return ความจุสูงสุดของ stack
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * คืนข้อความตัวบนสุดของ stack
     *
     * @return ข้อความตัวบนสุด
     * @throws IllegalArgumentException ถ้า stack ว่างอยู่
     */
    public String peek() {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Stack is empty");
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * คืนรายการข้อความทั้งหมดตามลำดับ
     *
     * @return รายการข้อความตามลำดับ
     */
    public List<String> getElements() {
        return new ArrayList<>(elements);
    }

    /**
     * ตรวจสอบว่ามีข้อความนี้อยู่ใน stack หรือไม่
     *
     * @param information ข้อความต้องไม่เป็น null ไม่เป็นสตริงว่าง
     *                    และไม่ใช้อักขระพิเศษ
     * @return true ถ้าพบข้อความ, false ถ้าไม่พบ
     * @throws IllegalArgumentException ถ้า information ผิดเงื่อนไข
     */
    public boolean contains(String information) {
        if (information == null || information.equals("")) {
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
     * คืน stack ใหม่ที่มีข้อความเดิมแต่สลับลำดับ
     *
     * @return stack ที่สลับลำดับแล้ว
     */
    public BoundedStack shuffled() {
        List<String> copy = new ArrayList<String>(elements);
        Collections.shuffle(copy);
        return new BoundedStack(copy);
    }
}