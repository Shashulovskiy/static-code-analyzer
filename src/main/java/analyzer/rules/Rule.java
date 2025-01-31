package analyzer.rules;

import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.List;

public interface Rule {
    void check(MethodCallExpr methodCall, List<String> violations);
}
