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

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea PlaylistTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testPeek();
        testObservers();
        testBoundary();

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
         //--สร้างสแตกใหม่แล้วต้องว่าง--
        BoundedStack stack = new BoundedStack(5);
        check("Create new stack is empty", stack.isEmpty());
        
        //--สร้างสแตกใหม่แล้วขนาดต้องเป็นศูนย์--
        check("New stack size should be 0", stack.size() == 0 );

        //--สร้างสแตกใหม่แล้วต้องไม่เต็ม--
        check("Create new stack is not full", !stack.isFull());

        //--สร้างสแตกด้วยความจุหนึ่ง--
        BoundedStack stack1 = new BoundedStack(1);
        check("Create stack with capacity 1", stack1.capacity() == 1);

        //--สร้างสแตกด้วยความจุศูนย์ต้องโยน Exception--
        boolean threw0 = false;
        try {
            new BoundedStack(0);
        } 
        catch (IllegalArgumentException e) 
            {
                threw0 = true;
            }
        check("Constructor rejects 0 capacity",threw0);

        //--สร้างสแตกด้วยความจุศูนย์ต้องโยน Exception--
        boolean threwMinus5 = false;
        try {
            new BoundedStack(-5);
        } catch (IllegalArgumentException e) {
        threwMinus5 = true;
        }
        check("Constructor rejects negative capacity",threwMinus5);
    }

    private static void testPush() {
        System.out.println("\n-- Push --");
            
        // --เพิ่มข้อมูล 1 ตัวแล้วขนาดต้องเพิ่มเป็น 1--
        BoundedStack singlePushStack = new BoundedStack(5);
        singlePushStack.push("LungP");
        check("Push increases stack size", singlePushStack.size() == 1 );

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

        // --เพิ่มข้อมูลตอนเต็มต้องโยน Exception--
        BoundedStack overflowStack = new BoundedStack(2);
        overflowStack.push("A");
        overflowStack.push("B");
        boolean threw = false;
        try {
            overflowStack.push("C");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("Push on full stack throws exception", threw);

        // --เพิ่มข้อมูลตอนเต็มแล้วขนาดต้องไม่เปลี่ยน--
        BoundedStack fixedSizeStack = new BoundedStack(2);
        fixedSizeStack.push("A");
        fixedSizeStack.push("B");
        int before = fixedSizeStack.size();
        boolean threw1 = false;
        try {
            fixedSizeStack.push("C");
        } catch (IllegalArgumentException e) {
            threw1 = true;
        }
        check("Push on full stack throws exception",threw1);
        check("Push on full stack keeps size unchanged", fixedSizeStack.size() == before);
    }
    
    private static void testPop() {
        System.out.println("\n-- Pop --");

        //--pop ต้องคืนค่าตัวบนสุด--
        BoundedStack topPopStack = new BoundedStack(5);
        topPopStack.push("LungP");
        topPopStack.push("Hrk");
        check("Pop returns top element", topPopStack.pop().equals("Hrk"));

        //--pop แล้วขนาดต้องลดลง--
        BoundedStack sizeDecreaseStack = new BoundedStack(5);
        sizeDecreaseStack.push("A");
        sizeDecreaseStack.push("B");
        sizeDecreaseStack.pop();
        check("Pop decreases stack size", sizeDecreaseStack.size() == 1);
        
        //--Pop หลายครั้งต้องคืนข้อมูลย้อนลำดับแบบ LIFO--
        BoundedStack stack = new BoundedStack(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");

        boolean lifoOrder = stack.pop().equals("C") && stack.pop().equals("B") && stack.pop().equals("A");

        check("Multiple pops return elements in LIFO order", lifoOrder);

        //--Pop จนหมดแล้ว Stack ต้องว่าง--
        check("Pop all elements makes stack empty", stack.isEmpty());

        //--Pop ตอน Stack ว่างต้องโยน Exception--
        boolean threwEmpty = false;
        try {
            stack.pop();
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }

        check("Pop from empty stack throws IllegalArgumentException", threwEmpty);

        //--Pop ที่ไม่สำเร็จต้องไม่เปลี่ยนขนาด--
        check("Failed pop leaves size unchanged at 0", stack.size() == 0);
    }

    private static void testPeek() {
        System.out.println("\n-- Peek --");

        //--Peek ต้องคืนข้อมูลตัวบนสุด--
        BoundedStack stack = new BoundedStack(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");

        check("Peek returns the top element", stack.peek().equals("C"));

        //--Peek ต้องไม่ลบข้อมูลออกจาก Stack--
        int beforeSize = stack.size();
        Object beforeTop = stack.peek();

        check("Peek does not change stack size", stack.size() == beforeSize);

        check("Peek does not remove the top element", stack.peek().equals(beforeTop));

        //--Peek ตอน Stack ว่างต้องโยน Exception--
        BoundedStack emptyStack = new BoundedStack(3);

        boolean threwEmpty = false;
        try {
            emptyStack.peek();
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }

        check("Peek from empty stack throws IllegalArgumentException", threwEmpty);
    }


    private static void testObservers() {
        System.out.println("\n-- Observers --");

        //--Size ต้องแสดงจำนวนข้อมูลอย่างถูกต้อง--
        BoundedStack stack = new BoundedStack(3);
        stack.push("A");
        stack.push("B");

        check("Size returns the correct number of elements", stack.size() == 2);

        //--isEmpty ต้องเป็น true เมื่อ Stack ว่าง--
        BoundedStack emptyStack = new BoundedStack(3);

        check("isEmpty returns true for an empty stack", emptyStack.isEmpty());

        //--isEmpty ต้องเป็น false เมื่อมีข้อมูล--
        check("isEmpty returns false for a non-empty stack", !stack.isEmpty());

        //--isFull ต้องเป็น false ก่อนเต็ม--
        check("isFull returns false before reaching capacity", !stack.isFull());

        //--isFull ต้องเป็น true เมื่อเต็ม--
        stack.push("C");
        check("isFull returns true when stack reaches capacity", stack.isFull());
    }

    private static void testBoundary() {    
        System.out.println("\n-- Boundary --");

        //--Stack ความจุหนึ่งต้องใช้งานได้--
        BoundedStack oneStack = new BoundedStack(1);
        oneStack.push("A");

        boolean capacityOneWorks = oneStack.isFull() && oneStack.size() == 1 && oneStack.pop().equals("A") && oneStack.isEmpty();
        check("Stack with capacity 1 works correctly", capacityOneWorks);

        //--Push-Pop-Push ต้องใช้งานได้--
        BoundedStack reuseStack = new BoundedStack(2);
        reuseStack.push("A");
        reuseStack.pop();
        reuseStack.push("B");

        check("Push-pop-push works correctly", reuseStack.size() == 1 && reuseStack.peek().equals("B"));

        //--Stack เต็มแล้ว Pop หนึ่งครั้งต้อง Push ใหม่ได้--
        BoundedStack fullStack = new BoundedStack(2);
        fullStack.push("A");
        fullStack.push("B");
        fullStack.pop();
        fullStack.push("C");
        check("Full-pop-push works correctly", fullStack.isFull() && fullStack.size() == 2 && fullStack.peek().equals("C"));
    }
}