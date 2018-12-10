package edu.tjrac.swant.kotlin.baselib.util


import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import java.util.regex.Pattern

class StringUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        private val emailer = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")

        private val IMG_URL = Pattern
                .compile(".*?(gif|jpeg|png|jpg|bmp)")

        private val URL = Pattern
                .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)")

        //验证手机格式
        fun isMobileNO(mobiles: String): Boolean {
            /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、177（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
            val telRegex = "[1][3456789]\\d{9}"//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            if (isEmpty(mobiles)) {
                return false
            } else {
                return mobiles.matches(telRegex.toRegex())
            }
        }

        fun getSpanEndString(s: String, f: Float): SpannableString? {
            var span = SpannableString(s)
            span.setSpan(RelativeSizeSpan(f), s.length - 1, s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return span
        }

        fun isSupportPhoneNum(type: String, phone: String): Boolean {
            var pattern = ""
            when (type) {
                "+86"//中国大陆
                -> pattern = "^1[3456789]{1}\\d{9}$"
                "+852"//香港
                -> pattern = "^[5689]{1}\\d{7}$"
                "+853"//澳门
                -> pattern = "^6{1}\\d{7}$"
                "+886"//台湾
                -> pattern = "^09{1}\\d{8}$"
                else -> return false
            }
            return phone.matches(pattern.toRegex())
//            val p = Pattern.compile(pattern)
//            val m = p.matcher(phone)
//            return m.matches()
        }

        //验证手机格式
        fun isMobileNO(type: String, mobiles: String): Boolean {
            Log.i("isMobileNo",type+"_"+mobiles)
            var telRegex: String
            when (type) {
                "+86"//中国大陆
                -> telRegex = "[1][1345789]\\d{9}"
                "+852"//香港
                -> telRegex = "[5689]\\d{7}"
                "+853"//澳门
                -> telRegex = "[6]\\d{7}"
                "+886"//台湾
                -> telRegex = "[0][9]\\d{8}"
                else -> return false
            }
            //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            if (isEmpty(mobiles)) {
                Log.i("isMobileNo",type+"_"+mobiles+false)
                return false
            } else {
                var result=mobiles.matches(telRegex.toRegex())
                Log.i("isMobileNo",type+"_"+mobiles+result)

                return result
            }
        }


        /**
         * 判断是不是一个合法的电子邮件地址

         * @param email
         * *
         * @return
         */
        fun isEmail(email: String?): Boolean {
            if (email == null || email.trim { it <= ' ' }.length == 0)
                return false
            return emailer.matcher(email).matches()
        }
        //TextUtil.isEmpty
        //    public static boolean isEmpty(CharSequence string) {
        //        return string == null || string.length() == 0;
        //    }

        //  * 判断字符串是否为null或全为空格
        fun isSpace(string: String?): Boolean {
            return string == null || string.trim { it <= ' ' }.length == 0
        }

        fun null2Length0(string: String?): String {
            return string ?: ""
        }


        //返回字符串长度

        fun length(string: CharSequence?): Int {
            return string?.length ?: 0
        }


        //首字母大写
        fun upperFirstLetter(string: String): String {
            if (isEmpty(string) || !Character.isLowerCase(string[0])) {
                return string
            }
            return (string[0].toInt() - 32).toChar().toString() + string.substring(1)
        }

        //   首字母小写

        fun lowerFirstLetter(string: String): String {
            if (isEmpty(string) || !Character.isUpperCase(string[0])) {
                return string
            }
            return (string[0].toInt() + 32).toChar().toString() + string.substring(1)
        }

        //   转化为半角字符

        fun toDBC(string: String): String {
            if (isEmpty(string)) {
                return string
            }
            val chars = string.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i].toInt() == 12288) {
                    chars[i] = ' '
                } else if (65281 <= chars[i].toInt() && chars[i].toInt() <= 65374) {
                    chars[i] = (chars[i].toInt() - 65248).toChar()
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }

        //转化为全角字符

        fun toSBC(string: String): String {
            if (isEmpty(string)) {
                return string
            }
            val chars = string.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i] == ' ') {
                    chars[i] = 12288.toChar()
                } else if (33 <= chars[i].toInt() && chars[i].toInt() <= 126) {
                    chars[i] = (chars[i].toInt() + 65248).toChar()
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }

        fun getString(childs: Array<String>): String {
            val sb = StringBuffer()
            for (str in childs) {
                sb.append(str)
            }
            return sb.toString()
        }

        fun isEmpty(dir: String?): Boolean {
            if (dir == null || dir == "") {
                return true
            }
            return false
        }

        fun getEndString(name: String): String {
            if (name.contains(".")) {
                return name.substring(name.lastIndexOf("."), name.length)
            } else {
                return ""
            }
        }

        fun getHexString(value: Int, length: Int): String {
            val str = Integer.toHexString(value)
            if (length > str.length) {
                val sb = StringBuffer()
                for (i in 0..length - str.length - 1) {
                    sb.append("0")
                }
                return sb.append(str).toString()
            } else {
                return str.substring(str.length - length, str.length)
            }
        }


        fun join(entrys: List<String>, s: String): String {
            if (entrys.isEmpty()) {
                return ""
            } else {
                val sb = StringBuffer()
                for (i in entrys.indices) {
                    sb.append(entrys[i])
                    if (i != entrys.size - 1) {
                        sb.append(s)
                    }
                }
                return sb.toString()
            }

        }

        fun highLightString(info: String, f: Int, t: Int): CharSequence? {
            val spannableString = SpannableString(info)
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#0A93FF")), f, t,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            return spannableString
        }

        fun getIndicater(index: Int): Char {
            var index0 = 'A'.toInt()//A
            return (index0 + index).toChar()
        }

        // 0 32'23''
        // 1 32:23
        // 2
        fun secToTime(time: Int, chin: Boolean): String {
            var timeStr: String? = null
            var hour = 0
            var minute = 0
            var second = 0
            if (time <= 0)
                return ""
            else {
                minute = time / 60
                if (minute < 60) {
                    second = time % 60
                    if (chin) {
                        timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒"
                    } else {
                        timeStr = unitFormat(minute) + ":" + unitFormat(second)
                    }

                } else {
                    hour = minute / 60
                    if (hour > 99)
                        return "99时59分59秒"
                    minute = minute % 60
                    second = time - hour * 3600 - minute * 60
                    if (chin) {
                        timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒"
                    } else {
                        timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second)
                    }

                }
            }
            return timeStr
        }

        fun unitFormat(i: Int): String {
            var retStr: String? = null
            if (i >= 0 && i < 10)
                retStr = "0" + Integer.toString(i)
            else
                retStr = "" + i
            return retStr
        }
    }
}