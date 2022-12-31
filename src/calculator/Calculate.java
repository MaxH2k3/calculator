/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author DELL
 */


public class Calculate extends featuresCalculator {
    /**
     * c: biến lưu trữ dấu hiện tại trong biểu thức
     * tempDigit: giá trị số tạm thời
     * tempOperator: biến lưu giữ dấu trước đó trong queueStr
     * @param strA
     * @return 
     */
    public String tinh(String strA) {
        strA = "(" + strA + ")";
        boolean flagPush = false;

        Stack<String> stackStr = new Stack<>();
        Queue<String> queueStr = new LinkedList<>();

        String tempDigit = "";
        String tempOperator;

        
        for (char c : strA.toCharArray()) {
            if(c == '-' && queueStr.isEmpty())  queueStr.offer("0");
            
            if (Character.isDigit(c) || c == '.') {
                tempDigit = tempDigit + c;
                flagPush = true;
            }
            else if (c == 'D') {
                stackStr.push("G");
            }
            else if (c == 'M') {
                stackStr.push("L");
            }
            else if (c == '$') {
                stackStr.push("$");
            }
            else if (checkOperator(c)) {
                //push all digit in queue after check digit in string
                if (flagPush == true) {
                    queueStr.offer(tempDigit);
                    flagPush = false;
                    tempDigit = "";
                }

                //check stack is empty, isn't it?
                if (stackStr.isEmpty()) {
                    stackStr.push(String.valueOf(c));
                    continue;
                }

                //take the operator assign to varrible
                tempOperator = stackStr.peek();

                //proccess operating stack is not empty following (ký pháp nghịch đảo ba lan)
                switch (checkPriority(c)) {
                    case 1: {
                        stackStr.push(String.valueOf(c));
                        break;
                    }
                    case 3: {//the previous char was (+, -)
                        while (checkPriority(tempOperator.charAt(0)) == 2 || checkPriority(tempOperator.charAt(0)) == 3) {
                            queueStr.offer(stackStr.pop());
                            if (stackStr.isEmpty()) break;
                            tempOperator = stackStr.peek();
                        }
                        stackStr.push(String.valueOf(c));
                        break;
                    }  
                    case 2: {//the previous char was (*, /, %)
                        while (checkPriority(tempOperator.charAt(0)) == 1 || checkPriority(tempOperator.charAt(0)) == 2) {
                            queueStr.offer(stackStr.pop());
                            if (stackStr.isEmpty()) break;
                            tempOperator = stackStr.peek();
                        }
                        stackStr.push(String.valueOf(c));
                        break;
                    } 
                    case 0: { //the previous char was ("(", ")")
                        if (c == ')') {
                            while (!tempOperator.equals("(")) {
                                queueStr.offer(stackStr.pop());
                                if (stackStr.isEmpty()) break;
                                tempOperator = stackStr.peek();
                            }
                            stackStr.pop();
                            break;
                        }

                        stackStr.push(String.valueOf(c));
                        break;
                    }
                }
            }
            else if (c == ',') {
                System.out.println(stackStr + " = ");
                if (flagPush == true) {
                    queueStr.offer(tempDigit);
                    flagPush = false;
                    tempDigit = "";
                }
                while(true) {
                    if (stackStr.peek().equals("G") || stackStr.peek().equals("L") || stackStr.peek().equals("√") || stackStr.peek().equals("$")) {
                        break;
                    }
                    else if (stackStr.peek().equals("(")) {
                        stackStr.pop();
                        continue;
                    }
                    queueStr.offer(stackStr.pop());
                }
            }
        }
        
        System.out.println(stackStr);
        System.out.println(queueStr);
        
        //chuyển toàn bộ dấu ở stackStr sang queueStr
        while(!stackStr.isEmpty()) {
            queueStr.offer(stackStr.pop());
        }
        
        System.out.println("stack: " + stackStr);
        System.out.println("Queue: " + queueStr);
        
        //xử lý phép tính
        while (!queueStr.isEmpty()) {   
            if (queueStr.size() == 1 && queueStr.peek().charAt(0) == ')') {
                queueStr.poll();
                break;
            }
            
            System.out.println("============");
            System.out.println("stack: " + stackStr);
            System.out.println("Queue: " + queueStr);
            //
            //gán các giá trị số cho stack
            char tempChar = queueStr.peek().charAt(0);
            if (Character.isDigit(tempChar)) { //
                stackStr.push(queueStr.poll());
            }
            //nếu đã hết chữ số thì bắt đâu xử lý phép tính
            else {
                tempChar = queueStr.poll().charAt(0);
                String b = stackStr.pop();//4.0
                String a = stackStr.pop();//1.2
                switch (tempChar) {
                    case '+': {
                        flag = true;
                        String result = calculate(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case '-': {
                        flag = false;
                        String result = calculate(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case '*': {
                        String result = Multiple(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case '/': {
                        String result = Divide(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case '%': {
                        ResultDivisor result = divide(a, b);
                        stackStr.push(result.getRemainder());
                        break; 
                    }
                    case '^': {
                        String result = pow(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case 'G': {
                        String result = GCD(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case 'L': {
                        String result = LCM(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case '$': {
                        String result = convert(a, b);
                        stackStr.push(result);
                        break;
                    }
                    case '√': {
                        String result = root(a, b);
                        stackStr.push(result);
                        break;
                    }
                }
            }
        }
        
        return stackStr.peek();
    }

    public static int checkPriority(char c) {
        switch (c) {
            case '^':
                return 1;
            case '*':
            case '/':
            case '%':
            case '√':
                return 2;
            case '+':
            case '-':
                return 3;
            case 'G': 
            case 'L':
            case '$':
                return 5;
            default:
                return 0;
        }
    }

    public static boolean checkOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c == '%' || c == '^' || c == '√';
    }

}
