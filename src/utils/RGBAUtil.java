/**
 *颜色值十六进制和argb转化
 */
public class RGBAUtil {
	public static void main(String[] args) {

		StringBuilder rgba= new StringBuilder();

		long uColor = 0xAAFF0000 ;    // #AARRGGBB format

		System.out.println(hex2RGBA(uColor));

		System.out.println(hex2RGBA("AAFF0000"));

		System.out.println(hex2RGBA("AFF0000"));

		System.out.println(subHexColor("AAFF0000"));
		System.out.println(subHexColor("AFF0000"));
		System.out.println(subHexColor("FF0000"));
		System.out.println(subHexColor("F0000"));


		String p = "0.00|0.00|0.00|0.00|0.00|0.00|left";
		System.out.println((p));
		String[] attrs = p.split("\\|");
		System.out.println(attrs.length);
		for(int i=0;i<attrs.length;i++){
			System.out.print(attrs[i] );
		}

	}



	/**
     * argb 格式转网页格式
     *
     * @param uColor 0xAAFF0000
     * @return rgba(255.0, 0.0, 0.0, 0.6666667)
     */
	private static String hex2RGBA(long uColor) {
		StringBuilder rgba = new StringBuilder();
		float fAlpha = (float) ((uColor >> 24) & 0xFF) / 0xFF;
		float fRed = (float) ((uColor >> 16) & 0xFF);
		float fGreen = (float) ((uColor >> 8) & 0xFF);
		float fBlue = (float) (uColor & 0xFF);
		rgba.append("rgba(")
			.append(fRed)
			.append(",")
			.append(fGreen)
			.append(",")
			.append(fBlue)
			.append(",")
			.append(fAlpha)
			.append(")");

		return rgba.toString();
	}

	/**
     * argb 格式转网页格式
     *
     * @param uColor 
     * @return AAFF0000 --> rgba(255.0, 0.0, 0.0, 0.6666667)
     * 		   AFF0000 -->  FAFF0000  --> (255.0,0.0,0.0,0.98039216)
     */
	private static String hex2RGBA(String uColor) {
		long colorHex = 0X0;
		if (uColor.length() == 6) {
			colorHex = Long.parseLong("FF"+uColor, 16);
		} else if (uColor.length() == 7) {
			colorHex = Long.parseLong("F"+uColor, 16);
		} else {
			colorHex = Long.parseLong(uColor, 16);
		}
		return hex2RGBA(colorHex);
	}

	/**
     * argb 格式转网页格式
     *
     * @param uColor
     * @return AAFF0000 --> FF0000
     *         AFF0000 -->  FF0000  
     *         FF0000 -->  FF0000  
     */
	private static String subHexColor(String uColor) {
		return  uColor.length() <6? uColor: uColor.substring(uColor.length()-6,uColor.length());
	}
}
