import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test runner
 */

public class BoundedStackTest {
    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testPeek();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testCreators() {
        System.out.println("-- Creators --");
        // --สร้างสแตกใหม่แล้วต้องว่าง--
        BoundedStack stack = new BoundedStack(5);
        check("Create new stack is empty", stack.isEmpty());

        // --สร้างสแตกใหม่แล้วขนาดต้องเป็นศูนย์--
        check("New stack size should be 0", stack.size() == 0);

        // --สร้างสแตกใหม่แล้วต้องไม่เต็ม--
        check("Create new stack is not full", !stack.isFull());

        // --สร้างสแตกด้วยความจุหนึ่ง--
        BoundedStack stack1 = new BoundedStack(1);
        check("Create stack with capacity 1", stack1.getCapacity() == 1);

        // --สร้างสแตกด้วยความจุศูนย์ให้สร้างได้และยังว่าง--
        BoundedStack zeroCapacityStack = new BoundedStack(0);
        check("Constructor accepts 0 capacity",
                zeroCapacityStack.isEmpty() && zeroCapacityStack.size() == 0 && zeroCapacityStack.getCapacity() == 0);

        // --สร้างสแตกด้วยความจุศูนย์ต้องโยน Exception--
        boolean threwMinus5 = false;
        try {
            new BoundedStack(-5);
        } catch (IllegalArgumentException e) {
            threwMinus5 = true;
        }
        check("Constructor rejects negative capacity", threwMinus5);
    }

    private static void testPush() {
        System.out.println("\n-- Push --");

        // --เพิ่มข้อมูล 1 ตัวแล้วขนาดต้องเพิ่มเป็น 1--
        BoundedStack singlePushStack = new BoundedStack(5);
        singlePushStack.push("LungP");
        check("Push increases stack size", singlePushStack.size() == 1);

        // --เพิ่มข้อมูลแล้ว Stack ต้องไม่ว่าง--
        BoundedStack nonEmptyStack = new BoundedStack(5);
        nonEmptyStack.push("Hrk");
        check("Push makes stack non empty", !nonEmptyStack.isEmpty());

        // --เพิ่มข้อมูลแล้วข้อมูลบนสุดต้องเป็นข้อมูลล่าสุด--
        BoundedStack topElementStack = new BoundedStack(5);
        topElementStack.push("LungP");
        topElementStack.push("Hrk");
        check("Push updates top element", topElementStack.peek().equals("Hrk"));

        // --เพิ่มข้อมูลหลายตัวต้องเรียงแบบ LIFO--
        BoundedStack lifoBoundedStack = new BoundedStack(5);
        lifoBoundedStack.push("A");
        lifoBoundedStack.push("B");
        lifoBoundedStack.push("C");
        boolean lifotest = lifoBoundedStack.pop().equals("C") && lifoBoundedStack.pop().equals("B")
                && lifoBoundedStack.pop().equals("A");
        check("Push maintains LIFO order", lifotest);

        // --เพิ่มข้อมูลจนเต็ม--
        BoundedStack fullStack = new BoundedStack(3);
        fullStack.push("A");
        fullStack.push("B");
        fullStack.push("C");
        check("Push fills stack to capacity", fullStack.isFull());

        // --เพิ่มข้อมูลตอนเต็มต้องคืน false--
        BoundedStack overflowStack = new BoundedStack(2);
        overflowStack.push("A");
        overflowStack.push("B");
        boolean pushReturnedFalse = !overflowStack.push("C");
        check("Push on full stack returns false", pushReturnedFalse);
    }

    private static void testPop() {
        System.out.println("\n-- Pop --");
        BoundedStack s = new BoundedStack(Arrays.asList("A", "B", "C"));
        check("remove -> returns C", s.pop().equals("C"));
        check("remove -> size decreases", s.size() == 2);
        check("remove -> element is gone", !s.contains("C"));
        check("remove keeps the others in order",
                s.getElements().equals(Arrays.asList("A", "B")));

        // boundary: ลบจนหมด
        s.pop();
        s.pop();
        check("remove all -> empty", s.size() == 0);
        boolean threwNull = false;
        try {
            s.pop();
        } catch (IndexOutOfBoundsException e) {
            threwNull = true;
        }
        check("remove on empty list -> throws IndexOutOfBoundsException", threwNull);
    }

    private static void testPeek() {
        System.out.println("\n-- Peek --");

        // --Peek ต้องคืนข้อมูลตัวบนสุด--
        BoundedStack stack = new BoundedStack(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        check("Peek returns the top element", stack.peek().equals("C"));

        // --Peek ต้องไม่ลบข้อมูลออกจาก Stack--
        int beforeSize = stack.size();
        Object beforeTop = stack.peek();
        check("Peek does not change stack size", stack.size() == beforeSize);
        check("Peek does not remove the top element", stack.peek().equals(beforeTop));

        // --Peek ตอน Stack ว่างต้องโยน Exception--
        BoundedStack emptyStack = new BoundedStack(3);
        boolean threwEmpty = false;
        try {
            emptyStack.peek();
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("Peek from empty stack throws IllegalArgumentException", threwEmpty);
    }

    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");
        BoundedStack s = new BoundedStack(Arrays.asList("A", "B"));
        check("contains finds an existing element", s.contains("A"));
        check("contains rejects a missing element", !s.contains("Z"));
        boolean threwNullContain = false;
        try {
            s.contains(null);
        } catch (IllegalArgumentException e) {
            threwNullContain = true;
        }
        check("contains(null) -> throws IllegalArgumentException", threwNullContain);
        boolean threwSpecial = false;
        try {
            s.contains("ABC@&&");
        } catch (IllegalArgumentException e) {
            threwSpecial = true;
        }
        check("contains(Special characters) -> throws IllegalArgumentException", threwSpecial);
        BoundedStack n = new BoundedStack(0);
        boolean threwNull = false;
        try {
            n.peek();
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("peek() = null -> throws IllegalArgumentException", threwNull);
    }

    private static void testProducer() {
        System.out.println("\n-- Producer --");
        BoundedStack original = new BoundedStack(Arrays.asList("A", "B", "C", "D"));
        BoundedStack shuffled = original.shuffled();
        check("shuffled has the same size", shuffled.size() == original.size());
        List<String> a = new ArrayList<String>(original.getElements());
        List<String> b = new ArrayList<String>(shuffled.getElements());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same elements", a.equals(b));
        check("shuffled does not mutate the original",
                original.getElements().equals(Arrays.asList("A", "B", "C", "D")));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        shuffled.push("E");
        check("mutating the result does not affect the original",
                original.size() == 4);

        // boundary: shuffle ลิสต์ว่างต้องไม่พัง
        BoundedStack emptyShuffled = new BoundedStack(Arrays.asList()).shuffled();
        check("shuffling an empty list is safe", emptyShuffled.size() == 0);
    }

    // --- ทดสอบว่าไม่เกิด representation exposure ---
    private static void testExposure() {
        System.out.println("\n-- Exposure --");

        // ขาออก: แก้ list ที่ได้จาก getElement() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack(1);
        s.push("A");
        List<String> got = s.getElements();
        got.clear();
        check("clearing result of getElements() does not affect list",
                s.size() == 1);

        got = s.getElements();
        got.add("injected");
        check("adding to result of getElements() does not affect list",
                s.size() == 1 && !s.contains("injected"));

        // สองครั้งต้องเป็นคนละ object
        check("getElements() returns a fresh list each call",
                s.getElements() != s.getElements());

        // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(input);
        input.clear();
        check("clearing constructor argument does not affect list",
                p.size() == 2);
        input.add("injected");
        check("adding to constructor argument does not affect list",
                !p.contains("injected"));
    }
}