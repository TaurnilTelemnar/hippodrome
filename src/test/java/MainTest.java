import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Disabled("Run only manually")
    @Test
    @Timeout(22)
    void checkMainThenWillCompleteTheJobLessTimeout() throws Exception {
        Main.main(new String[0]);
    }

}