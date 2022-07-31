import org.junit.Assert;
import org.junit.Test;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.porterStemmer;

public class StemmerTest {
    @Test
    public void stemmerTest() {
        String string = "+pillows";
        SnowballStemmer stemmer = new porterStemmer();
        stemmer.setCurrent(string);
        stemmer.stem();
        string = stemmer.getCurrent();
        Assert.assertEquals("+pillow", string);
    }

    @Test
    public void multipleWordStemmerTest() {
        String string = "get here -pillows +pillows";
        SnowballStemmer stemmer = new porterStemmer();
        stemmer.setCurrent(string);
        stemmer.stem();
        string = stemmer.getCurrent();
        Assert.assertEquals("get here -pillows +pillow", string);
    }
}
