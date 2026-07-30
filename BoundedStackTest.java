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
        //testPush();
        //testPop();
        //testPeek();
        //testObservers();
        //testBoundary();
        //testException();

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
}
