import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test for Glossary.java.
 *
 * @author ayman
 *
 */
public class GlossaryTest {

    // header test
    @Test
    public void outputHeaderTEST() {
        SimpleWriter testFile = new SimpleWriter1L("data/outputHeaderTXT.txt");
        Glossary.generateHtmlHeader("Glossary", testFile);
        SimpleReader testReader = new SimpleReader1L(
                "data/outputHeaderTXT.txt");
        SimpleReader correctText = new SimpleReader1L(
                "data/outputHeaderTXT.txt");
        while (!testReader.atEOS()) {
            assertEquals(correctText.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctText.close();
    }

    // footer test
    @Test
    public void outputFooterTEST() {
        SimpleWriter testFile = new SimpleWriter1L("data/outputFooterTXT.txt");
        Glossary.generateHtmlFooter(testFile);
        SimpleReader testReader = new SimpleReader1L(
                "data/outputFooterTXT.txt");
        SimpleReader correctText = new SimpleReader1L(
                "data/outputFooterTXT.txt");
        while (!testReader.atEOS()) {
            assertEquals(correctText.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctText.close();
    }

}