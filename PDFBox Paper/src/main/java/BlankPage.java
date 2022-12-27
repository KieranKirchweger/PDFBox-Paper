import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public class BlankPage {
    public static void createBlankPage() throws IOException {
        // Create a new empty document
        PDDocument myDocument = new PDDocument();

        // Create a new page without content
        PDPage blankPage = new PDPage();

        // Add the page to the document
        myDocument.addPage(blankPage);

        // Save the document and close it
        myDocument.save("MyFirstPDF.pdf");
        myDocument.close();
    }
}
