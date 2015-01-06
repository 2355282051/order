package com.boka.common.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//---------------------------------------------------------------------------
//Filename:        StringUtils.java
//Date:            2012-02-23
//Author:          
//Function:        加密
//History:
//
//---------------------------------------------------------------------------
public class StringUtils {
	// private final static char SEPARATOR = '\n';

	/**
	 * This is a string replacement method.
	 */
	public static String replaceString(String source, String oldStr,
			String newStr) {

		// 转换为StringBuffer
		StringBuffer sb = new StringBuffer();
		int sind = 0;
		int cind = 0;
		// 搜索
		while ((cind = source.indexOf(oldStr, sind)) != -1) {
			sb.append(source.substring(sind, cind));
			sb.append(newStr);
			sind = cind + oldStr.length();
		}
		sb.append(source.substring(sind));
		return sb.toString();
	}

/**
     * This method is used to insert HTML block dynamically
     *
     * @param source the HTML code to be processes
     * @param bReplaceNl if true '\n' will be replaced by <br>
     * @param bReplaceTag if true '<' will be replaced by &lt; and 
     *                          '>' will be replaced by &gt;
     * @param bReplaceQuote if true '\"' will be replaced by &quot; 
     */
	public static String formatHtml(String source, boolean bReplaceNl,
			boolean bReplaceTag, boolean bReplaceQuote) {

		StringBuffer sb = new StringBuffer();
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char c = source.charAt(i);
			switch (c) {
			case '\"':
				if (bReplaceQuote)
					sb.append("&quot;");
				else
					sb.append(c);
				break;

			case '<':
				if (bReplaceTag)
					sb.append("&lt;");
				else
					sb.append(c);
				break;

			case '>':
				if (bReplaceTag)
					sb.append("&gt;");
				else
					sb.append(c);
				break;

			case '\n':
				if (bReplaceNl) {
					if (bReplaceTag)
						sb.append("&lt;br&gt;");
					else
						sb.append("<br>");
				} else {
					sb.append(c);
				}
				break;

			case '\r':
				break;

			case '&':
				sb.append("&amp;");
				break;

			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * Pad string object
	 */
	public static String pad(String src, char padChar, boolean rightPad,
			int totalLength) {

		int srcLength = src.length();
		if (srcLength >= totalLength) {
			return src;
		}

		int padLength = totalLength - srcLength;
		StringBuffer sb = new StringBuffer(padLength);
		for (int i = 0; i < padLength; ++i) {
			sb.append(padChar);
		}

		if (rightPad) {
			return src + sb.toString();
		} else {
			return sb.toString() + src;
		}
	}

	/**
	 * Get hex string from byte array
	 */
	public static String toHexString(byte[] res) {
		StringBuffer sb = new StringBuffer(res.length << 1);
		for (int i = 0; i < res.length; i++) {
			String digit = Integer.toHexString(0xFF & res[i]);
			if (digit.length() == 1) {
				digit = '0' + digit;
			}
			sb.append(digit);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * Get byte array from hex string
	 */
	public static byte[] toByteArray(String hexString) {
		int arrLength = hexString.length() >> 1;
		byte buff[] = new byte[arrLength];
		for (int i = 0; i < arrLength; i++) {
			int index = i << 1;
			String digit = hexString.substring(index, index + 2);
			buff[i] = (byte) Integer.parseInt(digit, 16);
		}
		return buff;
	}

	public static boolean isNotStringEmpty(String s){
		return !isStringEmpty(s);
	}

	public static boolean isStringEmpty(String s) {
		if (null == s || s.length() == 0 || s.equals("")) {
			return true;
		}
		return false;
	}
	public static boolean isNotEmpty(String s) {
		return !isStringEmpty(s);
	}

	public static boolean isStringArrayEmpty(String[] ss) {
		if (null == ss || ss.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isOneStringEmpty(String... s) {
		boolean result = isStringArrayEmpty(s);
		if (result) {
			return result;
		}
		for (String each : s) {
			if (isStringEmpty(each)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNumeric(String str){
		boolean isNumeric=false;
		if(!isStringEmpty(str)){
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if(isNum.matches() ){
				isNumeric=true;
			}
		}
		return isNumeric;
	}
	public static String arrayToString(String strs[]){
		StringBuffer sbuf=new StringBuffer("[");
		if(!isStringArrayEmpty(strs)){
			for(int i=0;i<strs.length;i++){
				if(i==strs.length-1){
					sbuf.append(strs[i]);
				}else{
					sbuf.append(strs[i]+", ");
				}
			}
		}
		sbuf.append("]");
		return sbuf.toString();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List ls){
		boolean isEmpty=true;
		if(ls!=null&&ls.size()>0){
			isEmpty=false;
		}
		return isEmpty;
	}

	public static String xssEncode(String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);

			// Avoid null characters
			value = value.replaceAll(" ", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	/**
	 * 过滤特殊字符
	 *
	 * @param s
	 * @return
	 * @author siquan.lv
	 * @date 2013-5-27
	 */
	public static String specialCharFilter(String s) {
//		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|; |or|-|+|,";

		return s.replaceAll(".*([';]+|(--)+).*", " ");
	}

	public static String encodeStr(String str) throws UnsupportedEncodingException {
		return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	}
}
