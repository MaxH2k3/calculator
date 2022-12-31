package calculator;

import java.util.Stack;

public class featuresCalculator implements calculatorInterface {

    static featuresCalculator operator = new featuresCalculator();
    public boolean flag = true;

    public String revert(String str) {
        String temp = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            temp += str.charAt(i);
        }
        return temp;
    }

    public static int compareString(String a, String b) {
        String[] split1 = a.split("\\.");
        String[] split2 = b.split("\\.");

        if (split1.length == 1 && split2.length == 2) {
            for (int i = 0; i < split2[1].length(); i++) {
                a += "0";
            }
            b = split2[0] + split2[1];
        } else if (split1.length == 2 && split2.length == 1) {
            for (int i = 0; i < split1[1].length(); i++) {
                b += "0";
            }
            a = split1[0] + split1[1];
        } else if (split1.length == 2 && split2.length == 2) {
            a = split1[0] + split1[1];
            b = split2[0] + split2[1];
            if (split1[1].length() > split2[1].length()) {
                for (int i = 0; i < split1[1].length() - split2[1].length(); i++) {
                    b += "0";
                }
            } else if (split1[1].length() < split2[1].length()) {
                for (int i = 0; i < split2[1].length() - split1[1].length(); i++) {
                    a += "0";
                }
            }
        }
        if (a.length() > b.length()) {
            return 1;
        } else if (a.length() < b.length()) {
            return -1;
        } else {
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) > b.charAt(i)) {
                    return 1;
                } else if (a.charAt(i) < b.charAt(i)) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public static boolean getSign(String str1, String str2) {
        if (str1.charAt(0) == '-' ^ str2.charAt(0) == '-') {
            return true;
        }
        return false;
    }

    @Override
    public String pow(String a, String b) {
        String result = a;
        if (compareString(b, "0") == 0) {
            return result = "1";
        }
        b = operator.minus(b, "1");
        while (compareString(b, "0") == 1) {
            result = operator.Multiple(result, a);
            b = operator.minus(b, "1");
        }

        return result;
    }

    @Override
    public String plus(String str1, String str2) {
        //12 38
        String result = "";
        int sum;

        int remember = 0;

        int amountStr1 = str1.length() - 1;
        int amountStr2 = str2.length() - 1;

        int tempStr1 = 0, tempStr2 = 0;

        while (true) {
            sum = remember;
            if (amountStr1 >= 0) {
                tempStr1 = Integer.parseInt(String.valueOf(str1.charAt(amountStr1)));
                amountStr1 -= 1;
                sum += tempStr1;
            }
            if (amountStr2 >= 0) {
                tempStr2 = Integer.parseInt(String.valueOf(str2.charAt(amountStr2)));
                amountStr2 -= 1;
                sum += tempStr2;
            }

            result = result + String.valueOf(sum % 10);

            if (sum > 9) {
                remember = 1;
            } else {
                remember = 0;
            }

            if (amountStr1 < 0 && amountStr1 == amountStr2) {
                if (remember != 0) {
                    result = result + remember;
                }
                break;
            }

        }
        return revert(result);
    }

    public String Plus(String str1, String str2) {
        String result = "";
        if (str1.charAt(0) == '-' && str2.charAt(0) != '-') {
            result = operator.minus(str2, str1.substring(1));
        } else if (str2.charAt(0) == '-' && str1.charAt(0) != '-') {
            result = operator.minus(str1, str2.substring(1));
        } else if (str1.charAt(0) == '-' && str2.charAt(0) == '-') {
            result = "-" + operator.plus(str1.substring(1), str2.substring(1));
        } else {
            result = operator.plus(str1, str2);
        }

        return result;
    }

    @Override
    public String minus(String str1, String str2) {
        String result = "";

        if (compareString(str2, str1) == 1) {
            return result = "-" + operator.minus(str2, str1);
        } else if (compareString(str2, str1) == 0) {
            return result = "0";
        }

        int subtract;

        int remember = 0;

        int amountStr1 = str1.length() - 1;
        int amountStr2 = str2.length() - 1;

        int tempStr1 = 0, tempStr2 = 0;

        while (true) {
            subtract = -remember;
            if (amountStr1 >= 0) {
                tempStr1 = Integer.parseInt(String.valueOf(str1.charAt(amountStr1)));
                amountStr1 -= 1;

                if (remember == 1 && amountStr2 < 0) {
                    if (tempStr1 == 0) {
                        subtract = subtract + 10 + tempStr1;
                        remember = 1;
                    } else {
                        subtract += tempStr1;
                        remember = 0;
                    }
                } else {
                    subtract += tempStr1;
                }

            }
            if (amountStr2 >= 0) {
                tempStr2 = Integer.parseInt(String.valueOf(str2.charAt(amountStr2)));
                amountStr2 -= 1;
                if (subtract < tempStr2) {
                    subtract = subtract + 10;
                    remember = 1;
                } else {
                    remember = 0;
                }

                subtract -= tempStr2;
            }

            result = result + String.valueOf(subtract % 10);

            if (result.contains("-")) {
                result = result.replaceAll("-", "");
            }

            if (amountStr1 < 0 && amountStr1 == amountStr2) {
                while (result.charAt(result.length() - 1) == '0' && result.length() != 1) {
                    result = result.substring(0, result.length() - 1);
                }
                break;
            }
        }
        return revert(result);
    }

    public String Minus(String str1, String str2) {
        String result = "";
        if (str1.charAt(0) == '-' && str2.charAt(0) != '-') {
            result = "-" + operator.plus(str1.substring(1), str2);
        } else if (str2.charAt(0) == '-' && str1.charAt(0) != '-') {
            result = operator.plus(str1, str2.substring(1));
        } else if (str1.charAt(0) == '-' && str2.charAt(0) == '-') {
            result = operator.minus(str2.substring(1), str1.substring(1));
        } else {
            result = operator.minus(str1, str2);
        }

        return result;
    }

    public String calculate(String str1, String str2) {
        String result = "";

        //split a number into 2 parts: integer part and decimal part
        String[] split1 = str1.split("\\.");
        String[] split2 = str2.split("\\.");

        int dePosition;

        if (split1.length == 1 && split2.length == 1) {
            if (flag) {
                return result = operator.Plus(str1, str2);
            } else {
                return result = operator.Minus(str1, str2);
            }
        } else if (split1.length == 1 && split2.length == 2) {
            str2 = split2[0] + split2[1];
            dePosition = split2[1].length();
            for (int i = 0; i < dePosition; i++) {
                str1 += '0';
            }
        } else if (split1.length == 2 && split2.length == 1) {
            str1 = split1[0] + split1[1];
            dePosition = split1[1].length();
            for (int i = 0; i < dePosition; i++) {
                str2 += '0';
            }
        } else {
            dePosition = split1[1].length();

            str1 = split1[0] + split1[1];
            str2 = split2[0] + split2[1];

            if (split1[1].length() < split2[1].length()) {
                dePosition = split2[1].length();
                for (int i = 0; i < dePosition - split1[1].length(); i++) {
                    str1 += '0';
                }
            } else if (split1[1].length() > split2[1].length()) {
                dePosition = split1[1].length();
                for (int i = 0; i < dePosition - split2[1].length(); i++) {
                    str2 += '0';
                }
            }
        }
        if (flag) {
            result = operator.Plus(str1, str2);
        } else {
            result = operator.Minus(str1, str2);
        }

        //check digit if result < decPosition (0.002, -0.2)
        if (result.charAt(0) == '-') {
            while (result.length() - 1 <= dePosition) {
                if (result.length() == 1) {
                    result = "0" + result;
                } else {
                    result = result.substring(0, 1) + "0" + result.substring(1);
                }
            }
        } else {
            while (result.length() - 1 < dePosition) {
                if (result.length() <= 2) {
                    result = "0" + result;
                } else {
                    result = result.substring(0, 1) + "0" + result.substring(1);
                }
            }
        }

        return result.substring(0, result.length() - dePosition) + "." + result.substring(result.length() - dePosition);
    }

    @Override
    public ResultDivisor divide(String str1, String str2) {
        ResultDivisor result = new ResultDivisor();
        String tempQuotient;
        String tempLenString = String.valueOf(str1.length() - str2.length());
        int i = 0;
        boolean isNegative = false;

        //kiểm tra số âm
        if (str1.charAt(0) == '-' && str2.charAt(0) != '0') {
            str1 = str1.substring(1);
            isNegative = true;
        } else if (str1.charAt(0) != '-' && str2.charAt(0) == '0') {
            str2 = str2.substring(1);
            isNegative = true;
        } else if (str1.charAt(0) == '-' && str2.charAt(0) == '0') {
            str1 = str1.substring(1);
            str2 = str2.substring(1);
        }

        if (str2.equals("0")) {
            System.out.println("Math ERROR");
            return result;
        }

        if (compareString(str1, str2) == -1) {
            result.setQuotient("0");
            result.setRemainder(str1);

            return result;
        }

        //kiểm tra số lượng chữ số
        do {
            result.setQuotient(operator.pow("10", tempLenString));
            tempLenString = operator.minus(tempLenString, "1");
            /*
            if (compareString(result.getQuotient(), str1) == 0) {
                return result;
            }
             */
        } while (compareString(operator.multiple(result.getQuotient(), str2), str1) == 1);

        //kiểm tra hàng đầu tiên trong chữ số
        tempQuotient = operator.multiple(result.getQuotient(), str2);
        while (!(compareString(operator.minus(str1, tempQuotient), "0") == 0)) {
            result.setQuotient(operator.plus(result.getQuotient().substring(0, i + 1), "1") + result.getQuotient().substring(i + 1));
            tempQuotient = operator.multiple(result.getQuotient(), str2);

            if (compareString(str1, tempQuotient) == -1) {
                result.setQuotient(operator.minus(result.getQuotient().substring(0, i + 1), "1") + result.getQuotient().substring(i + 1));
                i++;
            } else if (compareString(result.getQuotient().substring(0, i + 1), "9") == 0) {
                i++;
            }

            if (i >= result.getQuotient().length()) {
                break;
            }
        }

        //xử lý số sau khi đã tính toán
        if (isNegative) {
            result.setQuotient("-" + operator.plus(result.getQuotient(), "1"));
            result.setRemainder(operator.minus(operator.multiple(result.getQuotient().substring(1), str2), str1));
        } else {
            result.setRemainder(operator.minus(str1, operator.multiple(result.getQuotient(), str2)));
        }

        return result;
    }

    /*
            
     */
    public String Divide(String str1, String str2) {
        //kiểm tra xem 2 số có phải là số âm ko?
        boolean sign = getSign(str1, str2);

        if (str1.charAt(0) == '-') {
            str1 = str1.substring(1);
        }
        if (str2.charAt(0) == '-') {
            str2 = str2.substring(1);
        }

        //split a number into 2 parts: integer part and decimal part
        String[] split1 = str1.split("\\.");
        String[] split2 = str2.split("\\.");

        int dePosition;
        ResultDivisor result = null;
        ResultDivisor tempResult;
        String decimalPart = "";

        if (split1.length == 2 && split2.length == 1) {
            str1 = split1[0] + split1[1];
            dePosition = split1[1].length();
            for (int i = 0; i < dePosition; i++) {
                str2 += '0';
            }

        } else if (split1.length == 1 && split2.length == 2) {
            str2 = split2[0] + split2[1];
            dePosition = split2[1].length();
            for (int i = 0; i < dePosition; i++) {
                str1 += '0';
            }

        } else if (split1.length == 2 && split2.length == 2) {
            dePosition = split1[1].length();

            str1 = split1[0] + split1[1];
            str2 = split2[0] + split2[1];

            if (split1[1].length() < split2[1].length()) {
                dePosition = split2[1].length();
                for (int i = 0; i < dePosition - split1[1].length(); i++) {
                    str1 += '0';
                }
            } else if (split1[1].length() > split2[1].length()) {
                dePosition = split1[1].length();
                for (int i = 0; i < dePosition - split2[1].length(); i++) {
                    str2 += '0';
                }
            }
        }

        //kiểm tra kí tự đầu có phải là số 0 hay ko?
        if (str1.charAt(0) == '0') {
            str1 = str1.substring(1);
        }
        if (str2.charAt(0) == '0') {
            str2 = str2.substring(1);
        }

        tempResult = result = divide(str1, str2);
        while (tempResult.getRemainder().compareTo("0") != 0) {
            tempResult = divide(tempResult.getRemainder() + "0", str2);
            decimalPart += tempResult.getQuotient();
            if (decimalPart.length() >= 3) {
                break;
            }
        }
        result.setReal(result.getQuotient() + "." + decimalPart.replace(",", ""));
        if (sign) {
            return "-" + result.getReal();
        } else {
            return result.getReal();
        }
    }

    @Override
    public String multiple(String str1, String str2) {
        str1 = revert(str1);
        str2 = revert(str2);

        int temp, remember = 0;
        String result = "";

        for (int i = 0; i < (str1.length() + str2.length()); i++) {
            result += "0";
        }

        for (int i = 0; i < str2.length(); i++) {
            for (int j = 0; j < str1.length(); j++) {
                temp = (str1.charAt(j) - '0') * (str2.charAt(i) - '0') + remember + (result.charAt(i + j) - '0');
                result = result.substring(0, i + j) + (temp % 10) + result.substring(i + j + 1);

                remember = temp / 10;

                if (j == str1.length() - 1) {
                    temp = remember + (result.charAt(i + j + 1) - '0');
                    result = result.substring(0, i + j + 1) + remember + result.substring(i + j + 1 + 1);
                    remember = 0;
                }
            }
        }

        if (result.endsWith("0")) {
            result = result.substring(0, result.length() - 1);
        }

        return revert(result);
    }

    public String Multiple(String str1, String str2) {
        boolean sign = getSign(str1, str2);

        String result = "";

        //split a number into 2 parts: integer part and decimal part
        String[] split1 = str1.split("\\.");
        String[] split2 = str2.split("\\.");

        int dePosition;

        if (split1.length == 1 && split2.length == 1) {
            dePosition = 0;
            return operator.multiple(str1, str2);
        } else if (split1.length == 2 && split2.length == 1) {
            str1 = split1[0] + split1[1];
            dePosition = split1[1].length();
            result = operator.multiple(str1, str2);
        } else if (split1.length == 1 && split2.length == 2) {
            str2 = split2[0] + split2[1];
            dePosition = split2[1].length();
            result = operator.multiple(str1, str2);
        } else {
            str1 = split1[0] + split1[1];
            str2 = split2[0] + split2[1];
            dePosition = split1[1].length() + split2[1].length();
            result = operator.multiple(str1, str2);
        }

        if (sign) {
            return "-" + result.substring(0, result.length() - dePosition) + "." + result.substring(result.length() - dePosition);
        } else {
            return result.substring(0, result.length() - dePosition) + "." + result.substring(result.length() - dePosition);
        }
    }

    @Override
    public String GCD(String str1, String str2) {
        String x = str1;
        String y = str2;

        while (compareString(y, "0") != 0) {
            ResultDivisor result = operator.divide(x, y);
            x = y;
            y = result.getRemainder();
        }
        return x;
    }

    @Override
    public String LCM(String str1, String str2) {
        ResultDivisor result = operator.divide(operator.multiple(str1, str2), GCD(str1, str2));
        return result.getQuotient();
    }

    @Override
    public String convert(String str, String coefficient) {
        Stack<String> stackResult = new Stack<>();
        String result;
        do {
            result = operator.divide(str, coefficient).getRemainder();
            stackResult.push(result);
            str = operator.divide(str, coefficient).getQuotient();
        } while (str != "0");

        System.out.println(stackResult);

        result = "";

        while (!stackResult.isEmpty()) {
            result += stackResult.pop();
        }

        return result;
    }

    @Override
    public String factorial(String str) {
        String result = "1";

        if (operator.compareString(str, "0") == -1) {
            return result = "";
        } else {
            String count = "1";
            while (operator.compareString(str, count) != -1) {
                result = operator.multiple(result, count);
                count = operator.Plus(count, "1");
            }
            return result;
        }
    }

    @Override
    public String root(String str1, String str2) {
        String count = "1";
        String result = operator.pow(count, str2);

        //kiểm tra xem kết quả check lần đầu có thỏa ko? Nếu như ko lớn hơn so với str1 thì:
        while (operator.compareString(result, str1) != 1) {
            if (operator.compareString(result, str1) == 0) {
                return count;
            }
            count += "0";
            result = operator.pow(count, str2);
        }

        //nếu giá trị lớn hơn str1 thì:
        count = operator.divide(count, "10").getQuotient();

        //check
        int i = 1;
        do {
            if ((i - 1 == count.length() && count.indexOf(".") != -1) || count.charAt(count.length() - 1) == '9') {
                break;
            }

            if (count.indexOf(".") != -1) {
                count = count.substring(0, i - 1) + Plus(count.substring(i - 1, i), "1") + count.substring(i);
            } else {
                count = Plus(count.substring(0, i), "1") + count.substring(i);
            }

            result = pow(count, str2);

            //kiểm tra kết quả có bằng với str1 ko?
            if (compareString(result, str1) == 0) {
                return count;
            }

            //kiểm tra mỗi chữ số đã đạt tới giới hạn check chưa?
            if (compareString(str1, result) == -1) {
                if (count.indexOf(".") != -1) {
                    count = count.substring(0, i - 1) + minus(count.substring(i - 1, i), "1") + count.substring(i);
                } else {
                    count = minus(count.substring(0, i), "1") + count.substring(i);
                }
                i++;
            } else if (compareString(count.substring(0, i), "9") == 0) {
                i++;
            }

            if (count.indexOf(".") == -1 && i > count.length()) {
                count += ".000";
                i++;
            }

        } while (true);

        return count;
    }
}
