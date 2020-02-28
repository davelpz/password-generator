package passgen;

public class Main {
	static Words words = new Words();
	static String[] specials = { "!", "@", "#", "$", "%", "^", "&", "*", "-", "_", "+", "=" };
	static String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

	private static String randomSpecial() {
		return specials[(int) (Math.random() * specials.length)];
	}

	private static String randomDigits(int length) {
		String ans = "";
		for (var i = 0; i < length; i++) {
			ans += numbers[(int) (Math.random() * numbers.length)];
		}

		return ans;
	}

	private static String createPasswordTemplate(int length) {
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

	public static String generatePassword(int length) {
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

	public static void main(String[] args) {
		// for (var i = 0; i < 20; i++) {
		// System.out.println(words.randomWord(1));
		// System.out.println(randomDigits(i));
		// }
		for (var i = 3; i < 64; i++) {
			System.out.format("%2d: %s\n", i, generatePassword(i));
		}
	}

}
