package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private static final String DEFAULT_DELIMITERS = ",|:";  // 기본 구분자: 쉼표 또는 콜론

    // 입력 문자열을 처리하여 합을 계산하는 메서드
    public int add(String input) {
        if (isEmpty(input)) {
            return 0;  // 빈 문자열 또는 null 입력 시 0 반환
        }

        String[] numbers = extractNumbers(input);
        return calculateSum(numbers);
    }

    // 입력이 빈 문자열 또는 null인지 확인하는 메서드
    private boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    // 숫자 문자열을 추출하여 배열로 반환하는 메서드
    private String[] extractNumbers(String input) {
        if (input.startsWith("//")) {
            return splitWithCustomDelimiter(input);  // 커스텀 구분자 처리
        }
        return splitWithDefaultDelimiters(input);  // 기본 구분자 처리
    }

    // 커스텀 구분자가 있는 문자열을 처리하여 숫자를 분리하는 메서드
    private String[] splitWithCustomDelimiter(String input) {
        Matcher matcher = Pattern.compile("//(.+)\n(.*)").matcher(input);
        if (matcher.matches()) {
            String customDelimiter = matcher.group(1);
            String numbers = matcher.group(2);
            return numbers.split(Pattern.quote(customDelimiter));  // 커스텀 구분자로 분리
        } else {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    // 기본 구분자(쉼표 또는 콜론)로 문자열을 분리하는 메서드
    private String[] splitWithDefaultDelimiters(String input) {
        return input.split(DEFAULT_DELIMITERS);
    }

    // 숫자 배열을 합하여 반환하는 메서드
    private int calculateSum(String[] numbers) {
        int sum = 0;
        for (String number : numbers) {
            int num = parsePositiveNumber(number);
            sum += num;
        }
        return sum;
    }

    // 음수가 입력되면 예외를 던지는 숫자 파싱 메서드
    private int parsePositiveNumber(String number) {
        int parsedNumber = Integer.parseInt(number.trim());
        if (parsedNumber < 0) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다: " + number);
        }
        return parsedNumber;
    }
}
