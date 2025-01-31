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