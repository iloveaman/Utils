
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Title: NumberUtilTest.java
 * @Description: TODO(...)
 * @author Aman aman_yin@163.com
 * @date 2016年1月21日 下午2:34:45
 * @version V1.0
 */
public class NumberUtilTest {
	long arabic = 19847337;
	String chinese = "壹仟玖佰捌拾肆万柒仟叁佰叁拾柒";
	String chineseMoney = "壹仟玖佰捌拾肆万柒仟叁佰叁拾柒元";

	// String chinese2 = "壹玖捌肆柒叁叁柒";
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArabic2Chinese() {
		System.out.println(NumberUtil.arabic2Chinese(arabic));
	}

	@Test
	public void testConvertString() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvertMoney() {
		System.out.println(NumberUtil.convertMoney(arabic));
	}

	@Test
	public void testChinese2Arabic() {
		System.out.println(NumberUtil.chinese2arabic(chinese));
	}

	@Test
	public void testchineseMoney2arabic() {
		System.out.println(NumberUtil.chineseMoney2arabic(chineseMoney));
	}
}
