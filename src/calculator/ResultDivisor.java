/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

/**
 *
 * @author DELL
 */
public class ResultDivisor {
    private String quotient;
    private String remainder;
    private String real;
        
    public ResultDivisor() {
        this.quotient = "";
        this.remainder = "";
        this.real = "";
    }

    public String getQuotient() {
        return quotient;
    }

    public void setQuotient(String quotient) {
        this.quotient = quotient;
    }

    public String getRemainder() {
        return remainder;
    }

    public void setRemainder(String remainder) {
        this.remainder = remainder;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    
    
    @Override
    public String toString() {
        return "Quotient = " + quotient + "\nRemainder = " + remainder + "\nRealnumber: " + real;
    }
}
