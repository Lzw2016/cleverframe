package org.cleverframe.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * TODO 人民币小写转大写工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 11:28 <br/>
 */
public class RMBUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(RMBUtils.class);

    private static String HanDigiStr[] = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    /**
     * 人民币单位，最大支持：千万亿(16位整数)
     */
    private static String HanDiviStr[] = new String[]{
            "", "拾", "佰", "仟", "万",
            "拾", "佰", "仟", "亿",
            "拾", "佰", "仟", "万",
            "拾", "佰", "仟"};

    /**
     * 将货币转换为大写形式，最大支持：千万亿(16位整数)
     *
     * @param rmb 传入的数据，最大支持：千万亿(16位整数)，最大支持两位小数
     * @return String 返回的人民币大写形式字符串，失败返回null
     */
    public static String numToRMBStr(Double rmb) {
        BigDecimal rmbBigDecimal = new BigDecimal(rmb.toString());
        // 大小限制
        if (rmbBigDecimal.compareTo(new BigDecimal("10000000000000000")) > 0 || rmbBigDecimal.compareTo(new BigDecimal("-10000000000000000")) < 0) {
            logger.error("", new RuntimeException("将货币转换为大写形式失败，数值位数过大，最大支持：千万亿(16位整数)"));
            return null;
        }
        // 小数位限制
        if (rmbBigDecimal.scale() > 2) {
            logger.error("", new RuntimeException("将货币转换为大写形式失败，数值精度过大，最大支持两位小数"));
            return null;
        }

        // 是否是负数
        String negative = "";
        if (rmb < 0) {
            rmb = rmb * -1;
            negative = "负";
        }
        // 整 角 分 -- 末尾字符串
        String tailStr = "";
        // 角 分 的钱数
        int jiao, fen;
        // 元 的钱数
        long yuan = rmbBigDecimal.longValue();
        // 计算出：元 角 分 的钱数
        BigDecimal totalFenRmb = rmbBigDecimal.multiply(new BigDecimal("100"));
        int tmp = (int) (totalFenRmb.longValue() % 100);
        jiao = tmp / 10;
        fen = tmp % 10;

        // 构造 整 角 分 -- 末尾字符串-----------------------------------
        if (yuan != 0 && jiao == 0 && fen == 0) {
            tailStr = "整";
        }
        if (jiao == 0) {
            tailStr += HanDigiStr[jiao];
        } else {
            tailStr += HanDigiStr[jiao] + "角";
        }
        if (fen != 0) {
            tailStr += HanDigiStr[fen] + "分";
        }
        return negative + PositiveIntegerToHanStr(yuan) + "元" + tailStr;
    }

    /**
     * 将货币转换为大写形式(类内部调用)<br/>
     *
     * @param yuan 元 的钱数
     * @return String
     */
    private static String PositiveIntegerToHanStr(Long yuan) {
        StringBuilder rmbStr = new StringBuilder();
        boolean lastzero = false;
        boolean hasvalue = false; // 亿、万进位前有数值标记
        String yuanStr = yuan.toString();
        int length = yuanStr.length();
        int position; // 每一位的钱数
        for (int i = length - 1; i >= 0; i--) {
            position = yuanStr.charAt(length - i - 1) - '0';
            if (position != 0) {
                if (lastzero) {
                    // 若干零后若跟非零值，只显示一个零
                    rmbStr.append(HanDigiStr[0]);
                }
                // 除了亿万前的零不带到后面
                // 如十进位前有零也不发壹音用此行
                if (!(position == 1 && (i % 4) == 1 && i == length - 1)) {
                    // 十进位处于第一位不发壹音
                    rmbStr.append(HanDigiStr[position]);
                }
                // 非零值后加进位，个位为空
                rmbStr.append(HanDiviStr[i]);
                // 设置万进位前有值标记
                hasvalue = true;
            } else {
                if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) {
                    // 亿万之间必须有非零值方显示万
                    rmbStr.append(HanDiviStr[i]);
                }
            }
            if (i % 8 == 0) {
                // 万进位前有值标记逢亿复位
                hasvalue = false;
            }
            lastzero = (position == 0) && (i % 4 != 0);
        }
        if (rmbStr.length() == 0) {
            return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
        }
        return rmbStr.toString();
    }

}
