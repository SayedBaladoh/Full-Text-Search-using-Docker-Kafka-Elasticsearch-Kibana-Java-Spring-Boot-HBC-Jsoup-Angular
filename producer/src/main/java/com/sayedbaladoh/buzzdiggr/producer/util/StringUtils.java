package com.sayedbaladoh.buzzdiggr.producer.util;

public class StringUtils {

	// ToDo : Remove Stop Words, Grammar checking, Spelling correction
	/**
	 * Simple Text Cleaner
	 * 
	 * @param text
	 * 				- Text to clean
	 * @return
	 * 			- The cleaned text
	 * 
	 * @author SayedBaladoh
	 */
	public static String cleanText(String text) {
		
		if (text == null && text.equals(""))
			return "";
//		System.out.println("Dirty text: " + text);
		// Delete all usernames mentioned		 			(?:\\s|\\A)@+([A-Za-z0-9-_]+)
		// Delete all RT (retweets flags)					(RT)
		// Delete all hashtags mentioned					(?:\\s|\\A)#+([A-Za-z0-9-_]+)
		// Delete all URLs									(https?://(\\w+\\.)+\\S*)
		// Delete all no printable characters and Emojis	([\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]+)
		// Replace all break lines with spaces				(\n)
		// Replace all double spaces with single spaces		" {2,}"
		// Remove leading/trailing spaces					trim()
		
		text = text.replaceAll("((?:\\s|\\A)@[A-Za-z0-9-_]+)|(RT)|((?:\\s|\\A)#+([A-Za-z0-9-_]+))|(https?://(\\w+\\.)+\\S*)|([\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]+)", "").replaceAll("\n", " ").replaceAll(" {2,}", " ").trim();
//		System.out.println("Cleaned text: " + text);
		return text;
	}

	/**
	 * Test Case.
	 * 
	 * @param args
	 *            - not used
	 */
	public static void main(String[] args) {
		String text = "öäü L'alphabet est génial 😀! I luv صراع الكرة الذهبية 🏆\n\n🇦🇷  (الدوري  my &lt;3 iphone @abc12 &amp; you’re awsm #apple. DisplayIsAwesomehttps://www.apple, sooo happppppy 🙂 http://www.apple.com sdas 👽😀☂❤华み원❤";
		text="RT @H45HEM: \u0645\u062d\u0645\u062f \u0635\u0644\u0627\u062d \u064a\u0646\u0642\u0630 \u0633\u0645\u0643\u0629 \u0645\u0646 \u0627\u0644\u063a\u0631\u0642! \u0648\u0627\u0644\u0646\u0639\u0645 \u0641\u064a\u0643 \u064a\u0627 \u0641\u062e\u0631 \u0627\u0644\u0639\u0631\u0628. https://t.co/8rGdLuGqKE";
		System.out.println("Dirty text: " + text);
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Cleaned text: " + cleanText(text));
		System.out.println("------------------------------------------------------------------------------");
	}

}
