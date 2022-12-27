import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class HelloWorldPage {
    public static void createHelloWorldPage() throws IOException {
        // Create a new document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.LETTER);
        document.addPage(page);

        // Create a content stream
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Select a font
        PDFont font = PDType1Font.TIMES_ROMAN;


        // Start writing
        contentStream.beginText();

        // Set font and size
        contentStream.setFont(font, 20);

        // Start writing at coordinates x = 25, y = 750
        contentStream.newLineAtOffset(25, 750);

        // Place text
        contentStream.showText("Hello World!");

        // End writing
        contentStream.endText();


        // Read an image
        PDImageXObject image = PDImageXObject.createFromFile("src/main/resources/HelloWorld.png", document);

        // Add the image in the middle of the page
        PDRectangle dimensions = page.getMediaBox();
        contentStream.drawImage(image, (dimensions.getWidth() - image.getWidth()) / 2, (dimensions.getHeight() - image.getHeight()) / 2);

        // Close and save the document
        contentStream.close();
        document.save("HelloWorldPage.pdf");
        document.close();
    }
}
