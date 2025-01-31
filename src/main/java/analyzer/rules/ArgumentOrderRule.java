package analyzer.rules;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;

import java.util.ArrayList;
import java.util.List;

public class ArgumentOrderRule implements Rule {
    @Override
    public void check(MethodCallExpr methodCall, List<String> violations) {
        try {
            ResolvedMethodDeclaration resolvedMethod = methodCall.resolve();
            int paramCount = resolvedMethod.getNumberOfParams();

            if (paramCount == 0) {
                return;
            }

            List<Expression> arguments = methodCall.getArguments();

            if (arguments.size() != paramCount) {
                return;
            }

            List<ResolvedType> argumentTypes = new ArrayList<>();
            List<ResolvedType> parameterTypes = new ArrayList<>();

            for (int i = 0; i < arguments.size(); i++) {
                argumentTypes.add(arguments.get(i).calculateResolvedType());
                parameterTypes.add(resolvedMethod.getParam(i).getType());
            }

            for (int i = 0; i < argumentTypes.size(); i++) {
                for (int j = i + 1; j < argumentTypes.size(); j++) {
                    boolean typesMatch = argumentTypes.get(i).equals(parameterTypes.get(j)) &&
                            argumentTypes.get(j).equals(parameterTypes.get(i));
                    if (typesMatch) {
                        String firstArgumentName = ((NameExpr) arguments.get(i)).getName().asString();
                        String secondArgumentName = ((NameExpr) arguments.get(j)).getName().asString();
                        String firstParamName = resolvedMethod.getParam(i).getName();
                        String secondParamName = resolvedMethod.getParam(j).getName();
                        if (firstArgumentName.equals(secondParamName) && secondArgumentName.equals(firstParamName)) {
                            violations.add("Нарушен порядок аргументов в вызове метода " + methodCall.getNameAsString() +
                                    " в строке " + methodCall.getBegin().get().line + ": аргументы " + i + " и " + j +
                                    " перепутаны местами");
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // No op
        }
    }
}