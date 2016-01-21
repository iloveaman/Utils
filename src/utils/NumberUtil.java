import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @Title: NumberUtil.java
 * @Description: 阿拉伯数字和中文数字相互转换，目前最高位数只支持到亿（9位）,暂不支持小数
 * @author Aman aman_yin@163.com
 * @date 2016年1月21日 下午2:31:51
 * @version V1.0
 */
public class NumberUtil {
	// -------------------------------------------
	static String[] chineseNum = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌",
			"玖" };// 中文数字
	static String unit[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰",
			"仟", "亿" , "拾", "佰", "仟", "万", "拾", "佰",
			"仟", "亿" };// 每位上面的单位
	static String moneyUnit = "元";

	// static SparseArray<String> sparseArray;
	// static {
	// sparseArray = new SparseArray<String>(10);
	// for (int i = 0; i < chineseNum.length; i++) {
	// sparseArray.append(i, chineseNum[i]);
	// }
	// }
	static HashMap<Integer, String> sparseArray;
	static {
		sparseArray = new HashMap<Integer, String>(10);
		for (int i = 0; i < chineseNum.length; i++) {
			sparseArray.put(i, chineseNum[i]);
		}
	}

	/**
	 * 数字转换 : 把阿拉伯数字转换成中文数字
	 */
	public static String arabic2Chinese(long count) {
		String s = String.valueOf(count);
		StringBuffer sb = convertArabic2Chinese(s);
		String sss = String.valueOf(sb);
		int i = 0;
		for (int j = sss.length(); j > 0; j--) {
			sb = sb.insert(j, unit[i++]);
		}

		return String.valueOf(sb);
	}

	/**
	 * 货币转换 把阿拉伯数字转换成中文数字
	 */
	public static String convertMoney(long number) {
		String num = arabic2Chinese(number);
		return num + moneyUnit;
	}

	/**
	 * 阿拉伯数字逐位转中文数字，不含权重
	 */
	private static StringBuffer convertArabic2Chinese(String arabic) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arabic.length(); i++) {
			String index = String.valueOf(arabic.charAt(i));
			sb = sb.append(chineseNum[Integer.parseInt(index)]);
		}
		return sb;
	}

	/**
	 * 数字转换 : 把中文数字转换成阿拉伯数字
	 * 
	 * "壹仟玖佰捌拾肆万柒仟叁佰叁拾柒" --> 19847337
	 */
	public static String chinese2arabic(String number) {
		StringBuilder arabic = new StringBuilder();
		String numberTrim = new String(number);
		numberTrim = numberTrim.replace("零", "0").replace("壹", "1")
				.replace("贰", "2").replace("叁", "3").replace("肆", "4")
				.replace("伍", "5").replace("陆", "6").replace("柒", "7")
				.replace("捌", "8").replace("玖", "9");
		System.out.println(numberTrim);
		char char_;
		for (int i = 0; i < numberTrim.length(); i++) {
			char_ = numberTrim.charAt(i);
			if (Character.isDigit(char_)) {
				arabic.append(char_);
			}
		}
		return arabic.toString();
	}

	public static String chineseMoney2arabic(String number) {
		return chinese2arabic(number) + "元";
	}

	/**
	 * @unused
	 * @deprecated 中文数字逐位转阿拉伯数字，不含权重 "壹玖捌肆柒叁叁柒" --> 19847337
	 */
	private static StringBuffer convertChinese2Arabic(String chinese) {
		StringBuffer sb = new StringBuffer();
		int key;
		for (int i = 0; i < chinese.length(); i++) {
			key = getKeyByValue(String.valueOf(chinese.charAt(i)));
			sb.append(key);
		}

		return sb;
	}

	/**
	 * @deprecated
	 * @param value
	 * @return
	 */
	private static int getKeyByValue(String value) {
		int key = 0;
		// Map可能有多个key对应一个value
		for (Entry<Integer, String> e : sparseArray.entrySet()) {
			if (value.equals(e.getValue())) {
				key = e.getKey();
			}
		}
		return key;
	}
}
