import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.IOException;

public class Form {
    public static void createFormPDF() throws IOException {
        // create a new document with a blank page in A4 format
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // initialize a new PDAcroForm and bind it to our document
        PDAcroForm acroForm = new PDAcroForm(document);

        // add the acro form to our document catalog
        document.getDocumentCatalog().setAcroForm(acroForm);

        // set default font to Helvetica, font size to auto, and colour to black
        acroForm.setDefaultAppearance("/Helv 0 Tf 0 g");

        // create new PDResources object and add Helvetica font to acro form
        PDResources resources = new PDResources();
        resources.put(COSName.getPDFName("Helv"), PDType1Font.HELVETICA);
        acroForm.setDefaultResources(resources);

        // create a new text field, set its name and add it to the acro form
        PDTextField name = new PDTextField(acroForm);
        name.setPartialName("NameField");
        acroForm.getFields().add(name);

        // set standard value
        name.setValue("Max Musterfrau");

        // resize and place name widget
        PDAnnotationWidget widget = name.getWidgets().get(0);
        widget.setRectangle(new PDRectangle(50,750,200,50));
        widget.setPage(page);

        // create a new checkbox field, set its name and add it to the acro form
        PDCheckBox cool = new PDCheckBox(acroForm);
        cool.setPartialName("CoolField");
        acroForm.getFields().add(cool);

        // resize cool widget
        PDAnnotationWidget buttonWidget = cool.getWidgets().get(0);
        buttonWidget.setRectangle(new PDRectangle(50, 650, 30, 30));

        // set name field's style
        PDAppearanceCharacteristicsDictionary fieldAppearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        fieldAppearance.setBorderColour(new PDColor(new float[]{0,1,0}, PDDeviceRGB.INSTANCE));
        fieldAppearance.setBackground(new PDColor(new float[]{1,1,0}, PDDeviceRGB.INSTANCE));
        widget.setAppearanceCharacteristics(fieldAppearance);

        // make sure widgets will be displayed
        widget.setPrinted(true);
        buttonWidget.setPrinted(true);

        // write labels
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50,810);
        contentStream.showText("Name:");
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Are you cool?");
        contentStream.endText();
        contentStream.close();

        // add widgets to the page
        page.getAnnotations().add(widget);
        page.getAnnotations().add(buttonWidget);

        document.save("RegistrationForm.pdf");
        document.close();
    }
}
