package randomtext.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import randomtext.model.Statistic;
import randomtext.services.RandomTextService;

@RestController
public class RandomTextRestApi {


    @Autowired
    RandomTextService randomTextService;

    private ObjectMapper mapper = new ObjectMapper();

    //HTTP GET /betvictor/text?p_start=1&p_end=100&w_count_min=1&w_count_max=25
    @RequestMapping(value = "/betvictor/text", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getText(@RequestParam("p_start") int p_start,
                                          @RequestParam("p_end") int p_end,
                                          @RequestParam("w_count_min") int w_count_min,
                                          @RequestParam("w_count_max") int w_count_max) throws JsonProcessingException {
        Statistic statistic = randomTextService.getRandomTextMeStatistics(p_start, p_end, w_count_min, w_count_max);


        return new ResponseEntity<String>(mapper.writeValueAsString(statistic), HttpStatus.OK);
    }
}
