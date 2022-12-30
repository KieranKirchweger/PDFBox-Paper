import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BlankPage.createBlankPage();
        HelloWorldPage.createHelloWorldPage();
        DocumentCombiner.tryOutAdd();
        DocumentCombiner.tryOutImport();
        Form.createFormPDF();
    }
}