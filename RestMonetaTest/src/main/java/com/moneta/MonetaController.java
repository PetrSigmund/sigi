package com.moneta;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonetaController {

  @RequestMapping(value = "/moneta/{number}", method = RequestMethod.GET)
  public String hello(@PathVariable("number") Long number) {

    Long shiftNumber = shiftDigits(number);

    Long countNumber = countDigits(shiftNumber);

    Long deleteNumber = deleteDigits(countNumber);

    Long result = processEvenDigits(deleteNumber);

    return String.valueOf(result);
  }

  private static Long shiftDigits(Long testNumber) {

    String testNumberString = String.valueOf(testNumber);
    char[] testNumberChars = testNumberString.toCharArray();
    char[] newNumberChars = new char[testNumberChars.length];
    char previousDigitChar = 'x';

    for (int i = 0; i < testNumberChars.length; i++) {

      char digitChar = testNumberChars[testNumberString.length() - 1 - i];
      Integer digit = getDigitFromChar(digitChar);

      if (isLastDigit(i)) {
        newNumberChars[testNumberString.length() - 1 - i] = digitChar;
        previousDigitChar = digitChar;
        continue;
      }

      if (processShift(digit, getDigitFromChar(previousDigitChar))) {
        newNumberChars[testNumberString.length() - 1 - i] = previousDigitChar;
        newNumberChars[testNumberString.length() - i] = digitChar;
        continue;
      }

      newNumberChars[testNumberString.length() - 1 - i] = digitChar;
      previousDigitChar = digitChar;
    }
    return convertCharArrayToLong(newNumberChars);
  }


  private static Long countDigits(Long testNumber) {

    String testNumberString = String.valueOf(testNumber);
    char[] testNumberChars = testNumberString.toCharArray();
    StringBuffer newNumberStringBuf = new StringBuffer();

    for (char digitChar : testNumberChars) {
      Integer digit = getDigitFromChar(digitChar);

      if (isDigitEightOrNine(digit)) {
        Integer countDigit = 2 * digit;
        newNumberStringBuf.append(countDigit);
        continue;
      }
      newNumberStringBuf.append(digit);
    }

    return Long.valueOf(newNumberStringBuf.toString());
  }

  private static Long deleteDigits(Long testNumber) {
    String testNumberString = String.valueOf(testNumber);
    char[] testNumberChars = testNumberString.toCharArray();
    StringBuffer newNumberStringBuf = new StringBuffer();

    for (char digitChar : testNumberChars) {
      Integer digit = getDigitFromChar(digitChar);

      if (!isDigitSeven(digit)) {
        newNumberStringBuf.append(digit);

      }
    }
    return Long.valueOf(newNumberStringBuf.toString());
  }

  private static Long processEvenDigits(Long testNumber) {
    String testNumberString = String.valueOf(testNumber);
    char[] testNumberChars = testNumberString.toCharArray();
    StringBuffer newNumberStringBuf = new StringBuffer();
    int countEvenDigits = 0;

    for (char digitChar : testNumberChars) {
      Integer digit = getDigitFromChar(digitChar);
      if (isDigitEven(digit)) {
        countEvenDigits++;
      }
    }

    if (countEvenDigits == 0) {
      return testNumber;
    }

    return (Long) testNumber / countEvenDigits;
  }


  private static boolean isLastDigit(int i) {
    return i == 0;
  }

  private static boolean processShift(Integer digit, Integer previousDigit) {
    boolean isActualDigitLowerEqualThree = isDigitLowerEqualThree(digit);
    boolean previousDigitLowerEqualThree = isDigitLowerEqualThree(previousDigit);

    return !previousDigitLowerEqualThree && isActualDigitLowerEqualThree;
  }

  private static Integer getDigitFromChar(char digitChar) {
    return Character.getNumericValue(digitChar);
  }

  private static Long convertCharArrayToLong(char[] numberChars) {
    String numberString = String.valueOf(numberChars);
    return Long.valueOf(numberString);
  }

  private static boolean isDigitLowerEqualThree(Integer digit) {
    return digit <= 3;
  }

  private static boolean isDigitEightOrNine(Integer digit) {
    return digit == 8 || digit == 9;
  }

  private static boolean isDigitSeven(Integer digit) {
    return digit == 7;
  }

  private static boolean isDigitEven(Integer digit) {
    return (digit & 1) == 0;
  }

}
