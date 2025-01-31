package analyzer;

import analyzer.core.StaticCodeAnalyzer;
import analyzer.rules.ArgumentOrderRule;
import analyzer.rules.Rule;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new ArgumentOrderRule());

        StaticCodeAnalyzer analyzer = new StaticCodeAnalyzer(rules);

        Path rootDir = Paths.get("src/main/java");
        List<String> violations = analyzer.analyze(rootDir);

        if (violations.isEmpty()) {
            System.out.println("Нарушений не найдено.");
        } else {
            System.out.println("Найдены нарушения:");
            violations.forEach(System.out::println);
        }
    }
}