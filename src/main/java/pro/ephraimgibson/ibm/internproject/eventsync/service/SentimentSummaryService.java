package pro.ephraimgibson.ibm.internproject.eventsync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SentimentSummaryService {

    SummaryAnalyzer summaryAnalyzer;
    FeedbackRepository feedbackRepository;

    public Map<String, Object> getFeedbackSummary(Long eventId){
        List<Feedback> feedbackList = feedbackRepository.findByEvent_Id(eventId);

        if (feedbackList.isEmpty()) return null;

        Map<String, Object> summary = processFeedbackSummary(feedbackList);

        return Map.of("Event Id", eventId,
                    "Summary", summary);
    }

    public Map<String, Object> getAllEventsSentimentSummary() {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        if (feedbackList.isEmpty()) return null;

        Map<String, Object> summary = processFeedbackSummary(feedbackList);

        return Map.of("Summary", summary);
    }

    private Map<String, Object> processFeedbackSummary(List<Feedback> feedbackList) {
        Map<String, Object> feedbackCounts = summaryAnalyzer.countFeedbacks(feedbackList);
        Map<String, Object> feedbackScores = summaryAnalyzer.sumUpAndCalculateAverageOfAllScores(feedbackList);

        Map<String, Object> summary = new HashMap<>(feedbackCounts);
        summary.putAll(feedbackScores);
        return summary;
    }


}
