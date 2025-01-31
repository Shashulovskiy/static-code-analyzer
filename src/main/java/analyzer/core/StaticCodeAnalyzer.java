package analyzer.core;

import analyzer.rules.Rule;
import analyzer.rules.RuleVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class StaticCodeAnalyzer {
    private static final JavaParser parser;

    static {
        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        typeSolver.add(new ReflectionTypeSolver());
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);

        ParserConfiguration parserConfig = new ParserConfiguration()
                .setSymbolResolver(symbolSolver);
        parser = new JavaParser(parserConfig);
    }

    private final List<Rule> rules;

    public StaticCodeAnalyzer(List<Rule> rules) {
        this.rules = rules;
    }

    public List<String> analyze(Path rootDir) {
        List<String> violations = new ArrayList<>();
        try {
            Files.walkFileTree(rootDir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.toString().endsWith(".java")) {
                        analyzeFile(file, violations);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            violations.add("Ошибка при обходе директории: " + e.getMessage());
        }
        return violations;
    }

    private void analyzeFile(Path filePath, List<String> violations) {
        try {
            FileInputStream in = new FileInputStream(filePath.toFile());
            ParseResult<CompilationUnit> parseResult = parser.parse(in);

            if (parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
                CompilationUnit cu = parseResult.getResult().get();

                RuleVisitor ruleVisitor = new RuleVisitor(rules);
                ruleVisitor.visit(cu, violations);
            } else {
                violations.add("Ошибка при парсинге файла: " + filePath);
            }
        } catch (FileNotFoundException e) {
            violations.add("Файл не найден: " + filePath);
        }
    }
}