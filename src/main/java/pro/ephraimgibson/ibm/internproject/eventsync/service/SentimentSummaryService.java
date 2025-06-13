package pro.ephraimgibson.ibm.internproject.eventsync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SentimentSummaryService {

    private final FeedbackRepository feedbackRepository;

    public void countFeedbacks(Long eventId){
        Double positiveCount = 0.00, negativeCount = 0.00, neutralCount = 0.00;

        List<Feedback> feedbackList = feedbackRepository.findByEvent_Id(eventId);

       for(Feedback feedback : feedbackList){
           List<SentimentResult> results = feedback.getSentimentAnalysis().results();

           for(SentimentResult result : results){
               switch (result.getLabel()){
                   case "POSITIVE" : positiveCount += result.getScore();
                    break;
                   case "NEGATIVE" : negativeCount += result.getScore();
                       break;
                   case "NEUTRAL" : neutralCount += result.getScore();
                       break;
               }
           }
       }

    }
}
