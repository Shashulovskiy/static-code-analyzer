package analyzer.rule;

import analyzer.core.StaticCodeAnalyzer;
import analyzer.rules.ArgumentOrderRule;
import analyzer.rules.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArgumentOrderRuleTest {
    @Test
    public void testArgumentOrderRule() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new ArgumentOrderRule());

        StaticCodeAnalyzer analyzer = new StaticCodeAnalyzer(rules);

        Path rootDir = Paths.get("src/test/resources");
        List<String> violations = analyzer.analyze(rootDir);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Нарушен порядок аргументов в вызове метода doWork в строке 20: аргументы 0 и 1 перепутаны местами", violations.get(0));
    }

}
