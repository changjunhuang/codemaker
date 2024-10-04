import com.self.codemaker.util.KieSessionUtils;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * @author huangchangjun
 * @date
 */
public class DroolsTest {

    @Test
    public void test() throws Exception{
        KieSession kieSession = KieSessionUtils.getAllRules();
        kieSession.insert("it");
//        kieSession.getAgenda().getAgendaGroup("abc").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
