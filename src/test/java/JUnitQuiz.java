import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitQuiz {
    @DisplayName("null아닌지, 같은지, 다른지")
    @Test
    public void junitQuiz1() {
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

        // null 인지 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name1).isEqualTo(name2);
        assertThat(name1).isNotEqualTo(name3);

    }

    @DisplayName("양수인지 음수인지 큰지 작은지")
    @Test
    public void junitQuiz2() {
        int n1 = 13;
        int n2 = 0;
        int n3 = -5;

        // n1이 양수인지 확인
        assertThat(n1).isPositive();

        // n3가 음수인지 확인
        assertThat(n3).isNegative();

        // n1이 n2보다 큰지 확인
        assertThat(n1).isGreaterThan(n2);

        // n3이 n2보다 작은지 확인
        assertThat(n3).isLessThan(n2);
    }
}
