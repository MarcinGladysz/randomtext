package randomtext.services;

import org.jsoup.Jsoup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestowyField {

    private static Stream<String> splitParagraphToWords(String text_out) {
        //String[] words=text_out.split("\\s|\\.|\\?|\\!|\\,");

        String s = Jsoup.parse(text_out).text();

        String[] words = s.split("\\W+");
        return Arrays.stream(words);
    }

    private static Stream<String> extractParagraphs(String text){

        String[] words = text.split("<p>|</p>\r<p>|</p>\r");
        return Arrays.stream(words);

    }

    public static void main(String[] args) {
        ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();

        String test ="<p>To jest proba slow. wielu slow? obaczymy, hej    hoo more than     one.</p>";
        String test1 ="<p>And readily winced dealt caterpillar.<\\/p>\\r<p>Contrary moth spluttered or.</p>\\r<p>Overheard"+
            " through porcupine far up.</p>\\r";

        String test2 ="<p>Much.</p>\r" +
            "<p>Overshot memorable grabbed well.</p>\r" +
            "<p>On.</p>\r";
        splitParagraphToWords(test2).forEach(System.out::println);
        extractParagraphs(test2).forEach(System.out::println);
/*
        BiFunction<String,Integer,Integer> ifNewReturn1OrIncrementBy1Ifpresent= (k, v)->{
            if(v != null) return v+1; else return 1;};

        map.put("cos",1);
        map.compute("cos",ifNewReturn1OrIncrementBy1Ifpresent);
        map.compute("to",ifNewReturn1OrIncrementBy1Ifpresent);
        map.compute("to",ifNewReturn1OrIncrementBy1Ifpresent);
        map.compute("tamto",ifNewReturn1OrIncrementBy1Ifpresent);
        map.forEach((k,v) ->{
            System.out.println("key "+k+" value "+v);
        });
        Integer max = map.entrySet()
            .stream()
            .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
            .get()
            .getValue();

        List listOfMax = map.entrySet()
            .stream()
            .filter(entry -> entry.getValue() == max)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        listOfMax.forEach(System.out::println);*/
    }
}
