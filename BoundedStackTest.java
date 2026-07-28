import java.util.*;


/**
 * Test runner
 */
public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง - พิมพ์ PASS/FAIL และนับผล*/
    private static void check(String name, boolean condition) {
        if(condition) {
            passed++;
            System.out.println("[PASS]" + name);
        } else {
            failed++;
            System.out.println("[FAIL]" + name);
        }
    }

    /**
     * capicity ต้องมากกว่า 0
     * @param capacity ขนาดของ stack
     * size เท่ากับ 0
     * capacity เท่ากับ ค่าที่รับมา
     * isEmpty() ต้องเป็น true
     * isFull() ต้องเป็น false
     * @throws IllegalArgumentException ถ้า capacity <= 0
     */

    private static void testConstructor(int capacity) {
        try {
            BoundedStack stack = new BoundedStack(capacity);
            check("testConstructor: size == 0", stack.size() == 0);
            check("testConstructor: capacity == " + capacity, stack.capacity() == capacity);
            check("testConstructor: isEmpty() == true", stack.isEmpty());
            check("testConstructor: isFull() == false", !stack.isFull());
        } catch (IllegalArgumentException e) {
            if (capacity <= 0) {
                check("testConstructor: IllegalArgumentException for capacity <= 0", true);
            } else {
                check("testConstructor: Unexpected IllegalArgumentException for capacity > 0", false);
            }
        }
    }

    public static void main(String[] args) {
        testConstructor(5);
        testConstructor(0);
        testConstructor(-1);

        System.out.println("Total Passed: " + passed);
        System.out.println("Total Failed: " + failed);
    }

    /**
     * Operation: push(T element)
     * 
     * Purpose:
     * เพิ่ม element เข้าไปบนสุดของ stack
     * 
     * @param element ข้อมูลที่จะเพิ่มเข้าไป
     * 
     * Precondition:
     * stack ต้องไม่เต็ม
     * element ต้องไม่เป็น null
     * 
     * postcondition: 
     * element กลายเป็น top ของ stack
     * size เพิ่มขึ้น 1
     * ข้อมูลเดิมยังอยู่ครบ
     * capacity ไม่เปลี่ยน
     * 
     * @throws IllegalStateException ถ้า stack เต็ม
     * @throws NullPointerException ถ้า element เป็น null
     * 
     * side effect: 
     * เปลี่ยนสถานะของ stack
     */

    public static void testPush() {
        BoundedStack stack = new BoundedStack(3);
        try {
            stack.push("A");
            check("testPush: size == 1", stack.size() == 1);
            check("testPush: top == A", stack.peek().equals("A"));
            stack.push("B");
            check("testPush: size == 2", stack.size() == 2);
            check("testPush: top == B", stack.peek().equals("B"));
            stack.push("C");
            check("testPush: size == 3", stack.size() == 3);
            check("testPush: top == C", stack.peek().equals("C"));
            // Now the stack is full, pushing another element should throw an exception
            try {
                stack.push("D");
                check("testPush: push on full stack should throw exception", false);
            } catch (IllegalStateException e) {
                check("testPush: push on full stack throws IllegalStateException", true);
            }
        } catch (Exception e) {
            check("testPush: Unexpected exception " + e.getMessage(), false);
        }
    }

    /**
     * operation: 
     * T pop()
     * 
     * Purpose:
     * ลบ element บนสุดของ stack และ return element นั้น
     * 
     * Precondition:
     * stack ต้องไม่ว่าง
     * 
     * Postcondition:
     * คืนค่าข้อมูลที่อยู่บนสุดก่อนเรียกเมธอด
     * ข้อมูลบนสุดถูกลบออกจาก stack
     * size ลดลง 1
     * capacity ไม่เปลี่ยน
     * สมาชิกอื่นยังอยู่ครบ
     * 
     * @throws NoSuchElementException ถ้า stack ว่าง
     * 
     * Side effect:
     * เปลี่ยนสถานะของ stack
     */

    public static void testPop() {
        BoundedStack stack = new BoundedStack(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        try {
            String top = (String) stack.pop();
            check("testPop: popped element == C", top.equals("C"));
            check("testPop: size == 2", stack.size() == 2);
            check("testPop: top == B", stack.peek().equals("B"));
            stack.pop(); // pops B
            stack.pop(); // pops A
            // Now the stack is empty, popping should throw an exception
            try {
                stack.pop();
                check("testPop: pop on empty stack should throw exception", false);
            } catch (NoSuchElementException e) {
                check("testPop: pop on empty stack throws NoSuchElementException", true);
            }
        } catch (Exception e) {
            check("testPop: Unexpected exception " + e.getMessage(), false);
        }
    }

    

}
