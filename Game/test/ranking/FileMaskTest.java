package Game.test.ranking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import project.resources.model.ranking.FileMask;
import project.resources.model.ranking.IFileMask;

public class FileMaskTest {

    private Random rnd;
    private IFileMask test;

    @Before
    public void setUp() {
        test = FileMask.getFileInstance();
        rnd = new Random();
    }

    @Test
    public void testWriteInFile(){

        int first = 0,
            last = 0;

        try {
            first = countLine(test.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.writeInFile("[USER]: sASA [CHARACTER]: Fonzie [COINS]: "+(10+rnd.nextInt(500))+" [DURATION]: "+(10+rnd.nextInt(500)));

        try {
            last = countLine(test.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotEquals(first, last);
    }

    @Test
    public void testSortFile(){
        test.writeInFile("[USER]: sASA [CHARACTER]: Fonzie [COINS]: "+(10+rnd.nextInt(500))+" [DURATION]: "+(10+rnd.nextInt(500)));
        try {
            Assert.assertEquals(false, isOrdered(test.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.sortFile();
        try {
            Assert.assertEquals(true, isOrdered(test.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countLine(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int count = 0;
        while (reader.readLine() != null)
            count++;
        reader.close();
        return count;
    }

    private static boolean isOrdered(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine;
        List<Integer> values = new ArrayList<>();
        while ((currentLine = reader.readLine()) != null) {
            String[] words = currentLine.split(": ");
            values.add(Integer.parseInt(getNumericalPartsFromString(words[4]))
            - Integer.parseInt(getNumericalPartsFromString(words[3])));
        }
        reader.close();
        return isSorted(values);
    }

    private final static String getNumericalPartsFromString(final String str) {
        final Pattern NUMERICAL_PATTERN = Pattern.compile("\\d+");
		final Matcher matcher = NUMERICAL_PATTERN.matcher(str);
		final StringBuilder result = new StringBuilder();
		while (matcher.find()) {
			result.append(matcher.group());
		}
		return result.toString();
	}
    
    private static boolean isSorted(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i).compareTo(values.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }
    
}
