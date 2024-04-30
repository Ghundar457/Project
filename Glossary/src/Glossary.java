import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Ayman Abumaike
 */
public final class Glossary {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Glossary() {

    }

    /**
     * Used to compare Strings in lexicographic order.
     */
    public static class StringLT implements Comparator<String> {
        /**
         * Compares parameters o1 and o2. Returns a positive if o1 is greater,
         * negative if o1 is smaller, or 0 indicating they're equal
         *
         * @param o1
         *            first string parameter
         * @param o2
         *            second string parameter
         * @returns positive,negative, or 0 value
         */
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);

        }
    }

    /**
     * Outputs "opening" tags in the generated HTML file.
     *
     * @param title
     *            the title/header of the HTML file
     * @param outputStream
     *            the output stream
     * @updates outputStream.content
     * @ensures outputStream.content=#outputStream.content*HTML opening tags
     */
    public static void generateHtmlHeader(String title,
            SimpleWriter outputStream) {
        outputStream.println("<html>");
        outputStream.println("<head>");
        outputStream.println("<title>" + title + "</title>");
        outputStream.println("</head>");
        outputStream.println("<style>");
        // size of outputted paragraph
        outputStream.println("p{ font-size: 1.1em;}");
        outputStream.println("<body>");
        outputStream.println("</style>");
        // title is used as header and it is bold, italicized, and red
        outputStream.println("<h1 style=" + "\"color:red;\">" + "<b><i>" + title
                + "</i></b></h1>");
    }

    /**
     * Outputs the closing tags in the generated HTML file.
     *
     * @param outputStream
     *            the output stream
     * @updates outputStream.contents
     * @requires outputStream.is_open
     * @ensures outputStream.content=#outputStream.content* HTML closing tags
     */
    public static void generateHtmlFooter(SimpleWriter outputStream) {
        outputStream.println("</body>");
        outputStream.println("</html>");
    }

    /**
     * Outputs the definition of a given term in the generated HTML file.
     *
     * @param definition
     *            the output stream
     * @param outputStream
     *            the output stream
     * @param termList
     *            list of terms
     *
     * @updates outputStream.content
     * @requires outputStream.is_open
     * @ensures
     */
    public static void generateHtmlDefinition(String definition,
            SimpleWriter outputStream, Queue<String> termList) {
        outputStream.print("<p>");

        String[] words = definition.split(" ");

        for (int i = 0; i < words.length; i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < words[i].length(); j++) {
                if (Character.isLetter(words[i].charAt(j))) {
                    word.append(words[i].charAt(j));
                }
            }
            boolean contains = false;
            for (String term : termList) {
                if (word.toString().equals(term)) {
                    contains = true;
                }
            }
            if (contains) {
                outputStream.print("<a href=" + word.toString() + ".html>"
                        + words[i] + "</a>" + " ");
            } else {
                outputStream.print(words[i] + " ");
            }
        }

        outputStream.println("</p>");
        outputStream.print(
                "<hr style=\"height:2px;border-width:0;color:gray;background"
                        + "-color:blue\">\r\n");
        outputStream.print("<p>Return to ");
        outputStream.println("<a href=Index.html" + ">Index</a></p>");
    }

    /**
     * Gets all the terms in the fileData input stream and adds in to Queue list
     * then sorts it.
     *
     * @param termList
     * @param fileData
     * @updates termList
     * @ensures termList=#termList is sorted
     */
    public static void getSortedTermList(Queue<String> termList,
            SimpleReader fileData) {
        Comparator<String> sorter = new StringLT();

        while (!fileData.atEOS()) {
            String term = fileData.nextLine();
            termList.enqueue(term);
            while (!term.isEmpty()) {
                term = fileData.nextLine();
            }
        }

        termList.sort(sorter);
    }

    /**
     * Processes information in a given text file by the user, converting it to
     * an input stream to be read, and an HTML file being created in their
     * inputed folder.
     *
     * @param userTextFile
     *            User's text file
     * @param userFolder
     *            user's inputed folder location
     * @requires userFolder is a valid folder and userTextFile is a valid text
     *           file with terms and definitions
     * @ensures html files are generated in the user's inputed folder
     */
    public static void processTextFile(String userTextFile, String userFolder) {
        SimpleWriter fileOutput = new SimpleWriter1L(
                userFolder + "/Index.html");
        SimpleReader fileData = new SimpleReader1L(userTextFile);

        Queue<String> termList = new Queue1L<>();
        getSortedTermList(termList, fileData);

        fileOutput.println("<html>");
        fileOutput.println("<head>");
        fileOutput.println("<title>Glossary</title>");
        fileOutput.println("</head>");
        fileOutput.println("<body>");
        fileOutput.println("<h1>Glossary</h1>");
        fileOutput.print(
                "<hr style=\"height:2px;border-width:0;color:gray;background"
                        + "-color:blue\">\r\n");
        fileOutput.println("<h2>Index</h2>");
        fileOutput.println("<ul>");

        int length = termList.length();

        while (length > 0) {
            SimpleReader fileData2 = new SimpleReader1L(userTextFile);

            String currentTerm = termList.dequeue().trim();
            String currentFile = userFolder + "/"
                    + currentTerm.replaceAll(" ", "") + ".html";
            SimpleWriter termOutput = new SimpleWriter1L(currentFile);

            if (!currentTerm.isEmpty()) {
                generateHtmlHeader(currentTerm, termOutput);

                String currentLine = fileData2.nextLine();
                while (!currentLine.equals(currentTerm)) {
                    currentLine = fileData2.nextLine();
                }

                StringBuilder definitionBuilder = new StringBuilder();
                definitionBuilder.append(fileData2.nextLine());
                currentLine = fileData2.nextLine();
                while (!currentLine.isEmpty()) {
                    definitionBuilder.append(" " + currentLine);
                    currentLine = fileData2.nextLine();
                }

                generateHtmlDefinition(definitionBuilder.toString().trim(),
                        termOutput, termList);
                generateHtmlFooter(termOutput);

                fileOutput.println(
                        "<li><a href=" + currentTerm.replaceAll(" ", "")
                                + ".html" + ">" + currentTerm + "</a></li>");

                termOutput.close();
                fileData2.close();
            }

            termList.enqueue(currentTerm);
            length--;
        }

        fileData.close();
        fileOutput.println("</ul>");
        generateHtmlFooter(fileOutput);
        fileOutput.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader inputReader = new SimpleReader1L();
        SimpleWriter outputWriter = new SimpleWriter1L();

        outputWriter.print("Enter a text file with terms and definitions: ");
        String userTextFile = inputReader.nextLine();
        outputWriter.print("Enter a folder name: ");
        String userFolder = inputReader.nextLine();
        processTextFile(userTextFile, userFolder);
        outputWriter.println("Success!");

        inputReader.close();
        outputWriter.close();
    }

    public static void outputFooter(SimpleWriter testFile) {
        // TODO Auto-generated method stub

    }

}