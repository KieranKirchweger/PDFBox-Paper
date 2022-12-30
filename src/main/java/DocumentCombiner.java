import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class DocumentCombiner {
    public static void combineDocumentsAdd(PDDocument document1, PDDocument document2) {
        PDPageTree pages = document1.getPages();
        // iterate through the pages of document1 and add them to document2
        for (PDPage page : pages) {
            document2.addPage(page);
        }
    }

    public static void combineDocumentsImport(PDDocument document1, PDDocument document2) throws IOException {
        PDPageTree pages = document1.getPages();
        // iterate through the pages of document1 and import them in document2
        for (PDPage page : pages) {
            document2.importPage(page);
        }
    }

    public static void tryOutAdd() throws IOException {
        // load the files we want to add
        PDDocument document1 = PDDocument.load(new File("HelloWorldPage.pdf"));
        PDDocument document2 = PDDocument.load(new File("MyFirstPDF.pdf"));

        // add pages of document1 to document2
        combineDocumentsAdd(document1, document2);

        // get the last page we added to document2
        PDPage page = document2.getPage(document2.getNumberOfPages()-1);

        // create content stream in append mode as to not delete previous page content
        PDPageContentStream contentStream = new PDPageContentStream(document2, page, PDPageContentStream.AppendMode.APPEND, true, true);

        // write some text we only want in document2 but not in document1
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 200);
        contentStream.showText("We only want this in document 2.");
        contentStream.endText();
        contentStream.close();

        // save the documents
        document1.save("AddedDocument1.pdf");
        document2.save("AddedDocument2.pdf");
        document1.close();
        document2.close();
    }

    public static void tryOutImport() throws IOException {
        PDDocument document1 = PDDocument.load(new File("HelloWorldPage.pdf"));
        PDDocument document2 = PDDocument.load(new File("MyFirstPDF.pdf"));

        combineDocumentsImport(document1, document2);

        PDPage page = document2.getPage(document2.getNumberOfPages()-1);

        PDPageContentStream contentStream = new PDPageContentStream(document2, page, PDPageContentStream.AppendMode.APPEND, false, false);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 200);
        contentStream.showText("This will only be in document 2.");
        contentStream.endText();
        contentStream.close();

        document1.save("ImportDocument1.pdf");
        document2.save("ImportDocument2.pdf");
        document1.close();
        document2.close();
    }

}
