import controller.InvertedIndexFileReader;
import controller.MainController;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerTest {
    InvertedIndexFileReader fileReader = new InvertedIndexFileReader();
    MainController mainController = new MainController();

    @Test(expected = FileNotFoundException.class)
    public void readFileTestFailToFindFolder() throws FileNotFoundException {
        fileReader.readFilesInFolder(new File("wrongAddress"), new HashMap<>());
    }

    @Test
    public void saveSimpleFilesTest() throws FileNotFoundException {
        HashMap<String, ArrayList<String>> invertedIndexMap = new HashMap<>();
        fileReader.readFilesInFolder(new File("src/main/resources/testData"), invertedIndexMap);
        Assert.assertEquals("1", invertedIndexMap.get("hello").get(0));
    }

    @Test(expected = FileNotFoundException.class)
    public void readAndStoreFailTest() throws FileNotFoundException {
        mainController.readAndStoreWordsInDirectory("wrong address");
    }

    @Test
    public void simpleSearchNotFound() throws FileNotFoundException {
        mainController.readAndStoreWordsInDirectory("src/main/resources/testData");
        Assert.assertEquals("Nothing found!", mainController.search("something"));
    }

    @Test
    public void simpleQuery() throws FileNotFoundException {
        mainController.readAndStoreWordsInDirectory("src/main/resources/testData");
        Assert.assertEquals("[1]", mainController.search("John"));
    }

    @Test
    public void advancedQueryWithMultipleWords() throws FileNotFoundException {
        mainController.readAndStoreWordsInDirectory("src/main/resources/testData");
        Assert.assertEquals("[1]", mainController.search("hello John"));
    }

    @Test
    public void advancedQueryWithMinus() throws FileNotFoundException {
        mainController.readAndStoreWordsInDirectory("src/main/resources/testData");
        Assert.assertEquals("[2]", mainController.search("hello -name"));
    }

    @Test
    public void advancedQueryWithPlus() throws FileNotFoundException {
        mainController.readAndStoreWordsInDirectory("src/main/resources/testData");
        Assert.assertEquals("[1]", mainController.search("hello +name +boo"));
    }
}
