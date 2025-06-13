package pro.ephraimgibson.ibm.internproject.eventsync.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentRequest;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentAnalysis;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentResult;

import java.util.List;

@Service
public class SentimentService {

    @Value("${hugging.api.token}")
    private String apiToken;

    private final WebClient webClient;

    public SentimentService(ObjectMapper objectMapper) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api-inference.huggingface.co")
                .build();
    }

    public SentimentAnalysis getAISentimentAnalysis(String content) {
        try {
            List<List<SentimentResult>> nestedAISentimentResponse = webClient.post()
                    .uri("/models/cardiffnlp/twitter-roberta-base-sentiment")
                    .header("Authorization", "Bearer " + apiToken)
                    .header("Content-Type", "application/json")
                    .bodyValue(new SentimentRequest(content))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<List<SentimentResult>>>() {
                    })
                    .block();

            if (nestedAISentimentResponse == null || nestedAISentimentResponse.isEmpty()) {
                throw new RuntimeException();
            }
            List<SentimentResult> AISentimentAnalysis = nestedAISentimentResponse.get(0);
            return new SentimentAnalysis(AISentimentAnalysis);
        } catch (Exception e) {
            throw new RuntimeException("Sentiment analysis failed: " + e.getMessage());
        }
    }


}



