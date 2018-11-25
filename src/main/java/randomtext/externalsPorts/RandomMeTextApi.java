package randomtext.externalsPorts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import randomtext.model.RandomText;

@Service
public class RandomMeTextApi {


    RestTemplate restTemplate;

    @Autowired
    public RandomMeTextApi(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public RandomText getRandomText(int numberOfParagraph, int minNumberOfWorldInParagraph, int maxNumberOfWorldInParagraph) {

        //final String uri = "http://www.randomtext.me/api/giberish/p-3/2-5";
        final String uri = "http://www.randomtext.me/api/giberish/p-" + numberOfParagraph +
                "/" + minNumberOfWorldInParagraph + "-" + maxNumberOfWorldInParagraph;

        RandomText result = restTemplate.getForObject(uri, RandomText.class);

        return result;

    }
}
