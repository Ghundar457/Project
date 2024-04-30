import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    // Tests for combination():
    @Test
    public void testCombination0() {
        String str1 = "dog";
        String str2 = "doghot";

        String result = StringReassembly.combination(str1, str2, 3);

        assertEquals(result, str2);
    }

    @Test
    public void testCombination1() {
        String str1 = "";
        String str2 = "";

        String result = StringReassembly.combination(str1, str2, 0);

        assertEquals(result, str2);

    }

    @Test
    public void testCombination2() {
        String str1 = "dog";
        String str2 = "dog";
        String result = StringReassembly.combination(str1, str2, 3);
        assertEquals(result, str2);
    }

    // Tests for printWithLineSeparator():
    public void testPrintWithLineSeparator() {
        SimpleReader in = new SimpleReader1L("data/cheer-8-2.txt");
        SimpleWriter out = new SimpleWriter1L("data/cheer-8-2.txt");
        StringReassembly.printWithLineSeparators("Bucks ~ Beat", out);

        String answer = in.nextLine();
        String answer2 = in.nextLine();
        assertEquals(answer, "Bucks ");
        assertEquals(answer2, " Beat");

        in.close();
        out.close();
    }

    // Tests for printWithLineSeparator2():
    @Test
    public void testPrintWithLineSeparator2() {
        SimpleReader in = new SimpleReader1L("data/cheer-8-2.txt");
        SimpleWriter out = new SimpleWriter1L("data/cheer-8-2.txt");
        StringReassembly.printWithLineSeparators("Bucks Beat", out);

        String answer = in.nextLine();
        assertEquals(answer, "Bucks Beat");
        in.close();
        out.close();
    }

    @Test
    public void testLinefrominput() {
        SimpleReader in = new SimpleReader1L("data/Test.txt");
        Set<String> setOne = StringReassembly.linesFromInput(in);
        Set<String> setTwo = new Set1L<>();
        setTwo.add("Ayman");
        setTwo.add("Jill");
        assertEquals(setOne, setTwo);
    }

    //change this
    @Test
    public void testLinefrominput2() {
        SimpleReader in = new SimpleReader1L("data/Test2.txt");
        Set<String> setOne = StringReassembly.linesFromInput(in);
        Set<String> setTwo = new Set1L<>();
        setTwo.add("Ayman");
        assertEquals(setOne, setTwo);
    }

    //change this
    @Test
    public void testLinefrominput3() {
        SimpleReader in = new SimpleReader1L("data/Test3.txt");
        Set<String> setOne = StringReassembly.linesFromInput(in);
        Set<String> setTwo = new Set1L<>();
        setTwo.add("ji");
        setTwo.add("Jill");
        assertEquals(setOne, setTwo);
    }
    // Tests for addToSetAvoidingSubstrings():

    @Test
    public void testAddToSetAvoidingSubstrings0() {
        Set<String> setOne = new Set1L<>();
        setOne.add("jil");
        StringReassembly.addToSetAvoidingSubstrings(setOne, "Ayman");
        Set<String> setTwo = new Set1L<>();
        setTwo.add("Ayman");
        setTwo.add("jil");
        assertEquals(setOne, setTwo);
    }

    @Test
    public void testAddToSetAvoidingSubstrings1() {
        Set<String> setOne = new Set1L<>();
        setOne.add("Ayman");
        StringReassembly.addToSetAvoidingSubstrings(setOne, "an");
        Set<String> setTwo = new Set1L<>();
        setTwo.add("Ayman");
        assertEquals(setOne, setTwo);

    }

    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> setOne = new Set1L<>();
        setOne.add("an");
        StringReassembly.addToSetAvoidingSubstrings(setOne, "Ayman");
        Set<String> setTwo = new Set1L<>();
        setTwo.add("Ayman");
        assertEquals(setOne, setTwo);
    }
}
