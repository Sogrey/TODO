package org.sogrey.sogreyframe.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用正则匹配
 * Created by Sogrey on 2016/11/16.
 */

public class RegularMatchingUtils {

    /**
     * 正则匹配
     *
     * @param pattern 正则表达式
     * @param s       要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean toMatcher(String pattern,String s) {
        Pattern p=Pattern
                .compile(pattern);
        Matcher m=p.matcher(s);
        System.out.println(m.matches()+"---正则匹配成功");
        return m.matches();
    }

    /**
     * 整数或者小数：^[0-9]+\.{0,1}[0-9]{0,2}$
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isIntegerOrDecimal(String s) {
        String pattern="^[0-9]+\\.{0,1}[0-9]{0,2}$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入数字："^[0-9]*$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isNumberOnly(String s) {
        String pattern="^[0-9]*$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入n位的数字："^\d{n}$"
     *
     * @param s 要匹配的字符串
     * @param n 要输入的数字位数
     *
     * @return 返回匹配结果
     */
    public static boolean isNDigitNumberOnly(String s,int n) {
        String pattern="^\\d{"+n+"}$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入至少n位的数字："^\d{n,}$"
     *
     * @param s 要匹配的字符串
     * @param n 至少要输入的数字位数
     *
     * @return 返回匹配结果
     */
    public static boolean isNDigitNumberLeast(String s,int n) {
        String pattern="^\\d{"+n+",}$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入m~n位的数字："^\d{m,n}$"
     *
     * @param s 要匹配的字符串
     * @param m 至少要输入的数字位数
     * @param n 最多要输入的数字位数
     *
     * @return 返回匹配结果
     */
    public static boolean isNDigitNumberLeast(String s,int m,int n) {
        if (m<=n) {
            String pattern="^\\d{"+m+","+n+"}$";
            return toMatcher(pattern,s);
        } else {
            LogUtil.e("m must be less than n...");
            return false;
        }
    }

    /**
     * 只能输入零和非零开头的数字："^(0|[1-9][0-9]*)$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isNumberBeginningWithZero(String s) {
        String pattern="^(0|[1-9][0-9]*)$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入非零开头的数字："^([1-9][0-9]*)$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isNumberBeginningWithoutZero(String s) {
        String pattern="^([1-9][0-9]*)$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入有n位小数的正实数："^[0-9]+(.[0-9]{n})?$"
     *
     * @param s 要匹配的字符串
     * @param n 小数位数
     *
     * @return 返回匹配结果
     */
    public static boolean isNDecimalPlaces(String s,int n) {
        String pattern="^[0-9]+(.[0-9]{"+n+"})?$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入有m~n位小数的正实数："^[0-9]+(.[0-9]{m,n})?$"
     *
     * @param s 要匹配的字符串
     * @param m 最小小数位数
     * @param n 最多小数位数
     *
     * @return 返回匹配结果
     */
    public static boolean isNDecimalPlaces(String s,int m,int n) {
        String pattern="^[0-9]+(.[0-9]{"+m+","+n+"})?$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入非零的正整数："^\+?[1-9][0-9]*$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isNonZeroPositiveInteger(String s) {
        String pattern="^\\+?[1-9][0-9]*$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入非零的负整数："^\-[1-9][0-9]*$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isNonZeroNegativeInteger(String s) {
        String pattern="^\\-[1-9][0-9]*$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入长度为n的字符："^.{n}$"
     *
     * @param s 要匹配的字符串
     * @param n 字符串长度
     *
     * @return 返回匹配结果
     */
    public static boolean isNonZeroNegativeInteger(String s,int n) {
        String pattern="^.{"+n+"}$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入由26个英文字母组成的字符串："^[A-Za-z]+$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean is26EnglishLetters(String s) {
        String pattern="^[A-Za-z]+$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入由26个大写英文字母组成的字符串："^[A-Z]+$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean is26UppercasesLetters(String s) {
        String pattern="^[A-Z]+$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入由26个小写英文字母组成的字符串："^[a-z]+$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean is26LowercaseLetters(String s) {
        String pattern="^[a-z]+$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入由数字和26个英文字母组成的字符串："^[A-Za-z0-9]+$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean is26EnglishLettersAndNumber(String s) {
        String pattern="^[A-Za-z0-9]+$";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入由数字、26个英文字母或者下划线组成的字符串："^\w+$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean is26EnglishLettersAndNumberAndUnderline(String s) {
        String pattern="^\\w+$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证用户密码："^[a-zA-Z]\w{5,17}$"正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
     *
     * @param s 要匹配的字符串
     * @param m 最小长度
     * @param n 最大长度
     *
     * @return 返回匹配结果
     */
    public static boolean isPwd(String s,int m,int n) {
        String pattern="^[a-zA-Z]\\w{"+(m-1)+","+(n-1)+"}$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证是否含有^%&',;=?$\"等字符："[^%&',;=?$\x22]+"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isContainsSpecialString(String s) {
        String pattern="[^%&',;=?$\\x22]+";
        return toMatcher(pattern,s);
    }

    /**
     * 验证是否含有非法字符 < > & / ' | 等字符："[^<>&/|'\]+"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isIllegalCharacter(String s) {
        String pattern="[^<>&/|'\\]+";
        return toMatcher(pattern,s);
    }

    /**
     * 只能输入汉字："^[\u4e00-\u9fa5]{0,}$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isChineseCharacters(String s) {
        String pattern="^[\\u4e00-\\u9fa5]{0,}$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证Email地址："^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isEmail(String s) {
        String pattern="^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\"
                       +".][a-z]{2,3}([\\.][a-z]{2})?$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证InternetURL："^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isInternetURL(String s) {
        String pattern="^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证电话号码："^(\(\d{3,4}-)|\d{3.4}-)?\d{7,
     * 8}$"正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX
     * -XXXXXXXX"、"XXXXXXX"和"XXXXXXXX"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isTelephoneNumber(String s) {
        String pattern="^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";
        //        "^((d{3,4})|d{3,4}-)?d{7,8}(-d{3})*$";
        return toMatcher(pattern,s);
    }

    /**
     * 手机号码:
     * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[6, 7, 8], 18[0-9], 170[0-9]
     * 移动号段: 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     * 联通号段: 130,131,132,155,156,185,186,145,176,1709
     * 电信号段: 133,153,180,181,189,177,1700
     */
    private static final String MOBILE="^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
    /**
     * 中国移动：China Mobile
     * 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     */
    private static final String CM    ="(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|"
                                       +"(^1705\\d{7}$)";
    /**
     * 中国联通：China Unicom
     * 130,131,132,155,156,185,186,145,176,1709
     */
    private static final String CU    ="(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";
    /**
     * 中国电信：China Telecom
     * 133,153,180,181,189,177,1700
     */
    private static final String CT    ="(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 电信运营商
     * <ul>
     * <li>UNKNOWN 0,"未知","Unknown"</li>
     * <li>CM 1,"中国移动","China Mobile" </li>
     * <li>CU 2,"中国联通","China Unicom" </li>
     * <li>CT 3,"中国电信","China Telecom"</li>
     * </ul>
     */
    public enum TelecomOperators {
        UNKNOWN(0,"未知","Unknown"),
        CM(1,"中国移动","China Mobile"),
        CU(2,"中国联通","China Unicom"),
        CT(3,"中国电信","China Telecom");

        int    code;
        String cn, en;

        TelecomOperators(int code,String cn,String en) {
            this.code=code;
            this.cn=cn;
            this.en=en;
        }
    }

    /**
     * 手机电话号码正则表达式
     *
     * @param s 要匹配的手机号
     *
     * @return
     */
    public static boolean isMobileNumber(String s) {
        return toMatcher(MOBILE,s);
    }

    /**
     * 匹配电信运营商
     *
     * @param s 要匹配的手机号
     *
     * @return {@link TelecomOperators}
     */
    public static TelecomOperators getTelecomOperators(String s) {
        if (toMatcher(CM,s)) {
            return TelecomOperators.CM;
        } else if (toMatcher(CU,s)) {
            return TelecomOperators.CU;
        } else if (toMatcher(CT,s)) {
            return TelecomOperators.CT;
        } else {
            return TelecomOperators.UNKNOWN;
        }
    }

    /**
     * 电话号码正则表达式（支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号）
     * 匹配格式： <br/>
     * 11位手机号码 <br/>
     * 3-4位区号，7-8位直播号码，1－4位分机号 <br/>
     * 如：12345678901、1234-12345678-1234
     *
     * @param s 要匹配的字符串
     *
     * @return
     */
    public static boolean isMobilePhoneNumber(String s) {
        String pattern="((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-"
                       +"(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-"
                       +"(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        return toMatcher(pattern,s);
    }

    /**
     * 中国邮政编码验证:^d{6}$
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isPostcode(String s) {
        String pattern="^d{6}$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证身份证号（15位或18位数字）："^\d{15}|\d{18}$"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isIDNumber(String s) {
        String pattern="^\\d{15}$)|(^\\d{17}([0-9]|X)$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证一年的12个月："^(0?[1-9]|1[0-2])$"正确格式为："01"～"09"和"1"～"12"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isMonthOfYear(String s) {
        String pattern="^(0?[1-9]|1[0-2])$";
        return toMatcher(pattern,s);
    }

    /**
     * 验证一个月的31天："^((0?[1-9])|((1|2)[0-9])|30|31)$"正确格式为；"01"～"09"和"1"～"31"
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isDayOfMonth(String s) {
        String pattern="^((0?[1-9])|((1|2)[0-9])|30|31)$";
        return toMatcher(pattern,s);
    }

    /**
     * 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isContainsChineseCharacters(String s) {
        String pattern="[\\u4e00-\\u9fa5]";
        return toMatcher(pattern,s);
    }

    /**
     * 匹配双字节字符(包括汉字在内)：[^\x00-\xff]
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isDoubleByteCharacter(String s) {
        String pattern="[^\\x00-\\xff]";
        return toMatcher(pattern,s);
    }

    /**
     * 匹配空行的正则表达式：\n[\s| ]*\r
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isEmptyLine(String s) {
        String pattern="\\n[\\s| ]*\\r";
        return toMatcher(pattern,s);
    }

    /**
     * 匹配html标签的正则表达式：<(.*)>(.*)<\/(.*)>|<(.*)\/>
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isHtmlLabel(String s) {
        String pattern="<(.*)>(.*)<\\/(.*)>|<(.*)\\/>";
        return toMatcher(pattern,s);
    }

    /**
     * 匹配首尾空格的正则表达式：(^\s*)|(\s*$)
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isLeadingOrTrailingSpaces(String s) {
        String pattern="(^\\s*)|(\\s*$)";
        return toMatcher(pattern,s);
    }

    /**
     * 匹配IP：^(\d+)\.(\d+)\.(\d+)\.(\d+)$
     *
     * @param s 要匹配的字符串
     *
     * @return 返回匹配结果
     */
    public static boolean isIP(String s) {
        String pattern="^(([0-2]*[0-9]+[0-9]+)\\.([0-2]*[0-9]+[0-9]+)\\.([0-2]*[0-9]+[0-9]+)"
                       +"\\.([0-2]*[0-9]+[0-9]+))$";
        return toMatcher(pattern,s);
    }
}
