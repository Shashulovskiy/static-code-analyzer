package analyzer.rules;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class RuleVisitor extends VoidVisitorAdapter<List<String>> {
    private final List<Rule> rules;

    public RuleVisitor(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public void visit(MethodCallExpr n, List<String> violations) {
        super.visit(n, violations);
        for (Rule rule : rules) {
            rule.check(n, violations);
        }
    }
}
