package passgen;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

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
        StringBuilder ans = new StringBuilder();
        for (var i = 0; i < length; i++) {
            ans.append(numbers[(int) (Math.random() * numbers.length)]);
        }

        return ans.toString();
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

        StringBuilder result = new StringBuilder();
        boolean toggle = true;
        for (String t : pieces) {
            if (toggle) {
                result.append(t);
            } else {
                result.append(randomDigits(t.length()));
            }
            result.append("-");
            toggle = !toggle;
        }

        return result.substring(0, 1).toUpperCase() + result.substring(1, length - 1) + randomSpecial();
    }
    
    public Integer call() {
        System.out.println(generatePassword());
        return 0;
    }
}
