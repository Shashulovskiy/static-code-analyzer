# Применение
Анализатор позволяет находить ситуации, когда передаваемые аргументы одного типа, возможно, перепутаны местами.

Пример кода:
```java
public class Example {
    public static void doWork(boolean needsProcessing, boolean useNewApi) {
        if (needsProcessing) {
            System.out.println("Processing...");
        }
        if (useNewApi) {
            System.out.println("Using new API...");
        }
    }

    public static void main(String[] args) {
        boolean randomName = true;
        boolean useNewApi = true;
        boolean needsProcessing = false;

        // Правильный порядок
        doWork(needsProcessing, useNewApi);

        // Неправильный порядок
        doWork(useNewApi, needsProcessing); // Нарушение правила

        // Ничего не можем сказать про порядок
        doWork(randomName, randomName);
    }
}
```
Вывод анализатора:
```text
Нарушен порядок аргументов в вызове метода doWork в строке 20: аргументы 0 и 1 перепутаны местами
```

# Использование
```java
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
```

# Запуск примера
Для запуска примера нужно выполнить следующую команду
```bash
./gradlew run
```