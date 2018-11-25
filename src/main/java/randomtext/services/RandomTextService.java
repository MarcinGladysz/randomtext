package randomtext.services;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import randomtext.externalsPorts.RandomMeTextApi;
import randomtext.model.ParagraphComputationItem;
import randomtext.model.RandomText;
import randomtext.model.Statistic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class RandomTextService {


    @Autowired
    RandomMeTextApi randomMeTextApi;

    private BiFunction<String, Integer, Integer> ifNewReturn1OrIncrementBy1Ifpresent = (k, v) -> {
        if (v != null) return v + 1;
        else return 1;
    };


    public Statistic getRandomTextMeStatistics(int startNumberOfParagraph, int endNumberOfParagraph,
                                               int minNumberOfWordsinParagraph, int maxNumberOFWordsinParagraph) {
        if (minNumberOfWordsinParagraph > maxNumberOFWordsinParagraph) {
            throw new IllegalArgumentException();
        }

        long startTime = System.currentTimeMillis();

        Statistic statistic = new Statistic();

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        List<ParagraphComputationItem> paragraphComputations =
                IntStream.range(startNumberOfParagraph, endNumberOfParagraph).parallel().boxed().flatMap(n -> {
                    return generateOneItem(map, n, minNumberOfWordsinParagraph, maxNumberOFWordsinParagraph);
                }).collect(Collectors.toList());


        statistic.setMostFrequentWord(getMostFrequentWordFromMap(map));
        statistic.setAvrParagraphLength(paragraphComputations.stream().
                collect(Collectors.averagingInt(ParagraphComputationItem::getNumberOfwords)).intValue());
        statistic.setAvrParagraphProcessingTime(paragraphComputations.stream().
                collect(Collectors.averagingLong(ParagraphComputationItem::getProcessingTime)));

        long endTime = System.currentTimeMillis();

        statistic.setTotalProcessingTime(endTime - startTime);
        return statistic;
    }

    private Stream<ParagraphComputationItem> generateOneItem(final ConcurrentHashMap<String, Integer> map, final int numberOfParagraph,
                                                             final int minNumberOfWordsinParagraph, final int maxNumberOFWordsinParagraph) {
        RandomText random = randomMeTextApi.getRandomText(numberOfParagraph, minNumberOfWordsinParagraph, maxNumberOFWordsinParagraph);

        return extractParagraphs(random.getText_out()).map(paragraph -> {

                    long startTime = System.currentTimeMillis();

                    ParagraphComputationItem computationItem = new ParagraphComputationItem();

                    String[] words = splitParagraphToWords(paragraph);
                    Arrays.stream(words).forEach(s -> map.compute(s, ifNewReturn1OrIncrementBy1Ifpresent));


                    long endTime = System.currentTimeMillis();
                    computationItem.setNumberOfwords(words.length);
                    computationItem.setProcessingTime(endTime - startTime);
                    return computationItem;
                }
        );

    }

    private String[] splitParagraphToWords(String text_out) {
        String s = Jsoup.parse(text_out).text();

        return s.split("\\W+");

    }

    private static Stream<String> extractParagraphs(String text) {

        String[] words = text.split("<p>|</p>\r<p>|</p>\r");
        return Arrays.stream(words);

    }

    private String getMostFrequentWordFromMap(ConcurrentHashMap<String, Integer> map) {

        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
