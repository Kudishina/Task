package Test;

public class CommentsFilter {
    @FunctionalInterface
    interface TextAnalyzer {
        Label processText(String text);    //в задании говорится о том, что
        // метод фильтрации в интерфейсе,должен возвращать значение из ENUM. Вспоминаю что это xD
    }

    //ENUM это крутая штука, где у нас находятся const.
    enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }

    //абстрактный класс (суперкласс) имплементим!
    abstract class KeywordAnalyzer implements TextAnalyzer {
        abstract protected String[] getKeywords();

        abstract protected Label getLabel();

        @Override
        public Label processText(String text) {
            for (String keyword : getKeywords()) {
                if (text.contains(keyword))
                    return getLabel();
            }
            return Label.OK;
        }
    }

    class SpamAnalyzer extends KeywordAnalyzer {
        private String[] keywords;

        SpamAnalyzer(String[] spamKeywords) {
            this.keywords = spamKeywords;
        }

        @Override
        protected String[] getKeywords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.SPAM;
        }
    }

    class NegativeTextAnalyzer extends KeywordAnalyzer {
        NegativeTextAnalyzer() {
        }

        @Override
        protected String[] getKeywords() {
            String[] keywords = new String[]{":(", "=(", ":|"};
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }
    }

    class TooLongTextAnalyzer implements TextAnalyzer {
        private int maxLength;

        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public Label processText(String text) {
            if (text.length() > maxLength)
                return Label.TOO_LONG;
            return Label.OK;
        }
    }

    private Label checkLabels(TextAnalyzer[] analyzers, String text) {
        for (TextAnalyzer analyzer : analyzers) {
            Label label = analyzer.processText(text);
            if (label != Label.OK)
                return label;
        }
        return Label.OK;
    }

    public static void main(String[] args) {
        CommentsFilter task = new CommentsFilter();
        task.test();
    }

    //это уже для того, чтобы Идея не выкидывала ошибки, так как в стэпике всё топ
    public void test() {
        TooLongTextAnalyzer l = new TooLongTextAnalyzer(15);
        NegativeTextAnalyzer a = new NegativeTextAnalyzer();
        SpamAnalyzer s = new SpamAnalyzer(new String[]{"Спам", "Еще"});
        TextAnalyzer[] T = {a, s, l};
        checkLabels(T, "Спам или не спам");
    }

}