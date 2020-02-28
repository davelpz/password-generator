package passgen;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "passgen", mixinStandardHelpOptions = true, version = "passgen 1.0", description = "Generate passwords of given length.")
public class PasswordGenerator implements Callable<Integer> {
    @Parameters(index = "0", description = "The length of the password to generate.")
    private int length = 8;

    static Words words = new Words();
    static String[] specials = { "!", "@", "#", "$", "%", "^", "&", "*", "-", "_", "+", "=" };
    static String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

    private String randomSpecial() {
        return specials[(int) (Math.random() * specials.length)];
    }

    private String randomDigits(int length) {
        String ans = "";
        for (var i = 0; i < length; i++) {
            ans += numbers[(int) (Math.random() * numbers.length)];
        }

        return ans;
    }

    private String createPasswordTemplate(int length) {
        if (length < 6) {
            return words.randomWord(length);
        } else if (length == 6) {
            return createPasswordTemplate(3) + "-" + createPasswordTemplate(2);
        } else if (length == 7) {
            return createPasswordTemplate(3) + "-" + createPasswordTemplate(3);
        } else if (length == 8) {
            return createPasswordTemplate(4) + "-" + createPasswordTemplate(3);
        } else if (length == 9) {
            return createPasswordTemplate(4) + "-" + createPasswordTemplate(4);
        } else if (length == 10) {
            return createPasswordTemplate(5) + "-" + createPasswordTemplate(4);
        } else if (length == 11) {
            return createPasswordTemplate(5) + "-" + createPasswordTemplate(5);
        }

        return createPasswordTemplate(5) + "-" + createPasswordTemplate(length - 6);
    }

    public String generatePassword() {
        String base = createPasswordTemplate(length - 1);
        String[] pieces = base.split("-");
        // System.out.println(Arrays.deepToString(pieces));

        String result = "";
        boolean toggle = true;
        for (String t : pieces) {
            if (toggle) {
                result += t;
            } else {
                result += randomDigits(t.length());
            }
            result += "-";
            toggle = !toggle;
        }

        return result.substring(0, 1).toUpperCase() + result.substring(1, length - 1) + randomSpecial();
    }
    
    public Integer call() throws Exception {
        System.out.println(generatePassword());
        return 0;
    }
}
