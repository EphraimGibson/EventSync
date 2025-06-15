package pro.ephraimgibson.ibm.internproject.eventsync.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SummaryAnalyzerTest {

    private SummaryAnalyzer summaryAnalyzer;

    @BeforeEach
    void setUp() {
        summaryAnalyzer = new SummaryAnalyzer();
    }

    @Test
    public void testShouldCountSentimentsAndReturnCorrectMapInfo() {
        //GIVEN
        List<Feedback> feedbacks = createFeedbackListsWithEventAndScores();
        Map<String, Object> expected = createMapOfSentimentCounts();


        //WHEN
        Map<String, Object> result = summaryAnalyzer.countFeedbacks(feedbacks);

        //THEN
        assertThat(result).hasSize(expected.size()).containsAllEntriesOf(expected);
    }

    @Test
    public void testShouldCalculateSumOfEachResultScoreAndReturnTheAverage() {
        //GIVEN
        List<Feedback> feedbacks = createFeedbackListsWithEventAndScores();
        Map<String, Object> expected = createMapOfAverageOfAllScoresAndOverallScore();

        //WHEN
        Map<String, Object> result = summaryAnalyzer.sumUpAndCalculateAverageOfAllScores(feedbacks);

        //THEN
        assertThat(result).hasSize(expected.size()).containsAllEntriesOf(expected);
    }

    private List<Feedback> createFeedbackListsWithEventAndScores() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();

        feedback1.setTopSentiment("POSITIVE");
        feedback1.setNeutralScore(0.1);
        feedback1.setPositiveScore(0.7);
        feedback1.setNegativeScore(0.2);

        feedback2.setTopSentiment("NEGATIVE");
        feedback2.setNeutralScore(0.1);
        feedback2.setPositiveScore(0.1);
        feedback2.setNegativeScore(0.8);

        return List.of(feedback1, feedback2);
    }


    private Map<String, Object> createMapOfSentimentCounts() {
        return Map.of(
                "Total Feedbacks", 2,
                "Sentiment Count", Map.of(
                        "POSITIVE", 1L,
                        "NEGATIVE", 1L,
                        "NEUTRAL", 0L),
                "Percentage of Sentiments", Map.of(
                        "POSITIVE", 50.0,
                        "NEGATIVE", 50.0,
                        "NEUTRAL", 0.00
                ),
                "Overall Sentiment", "SLIGHTLY_POSITIVE"
        );
    }

    private Map<String, Object> createMapOfAverageOfAllScoresAndOverallScore() {
        return Map.of(
                "Average Feedback Scores", Map.of(
                        "POSITIVE", 40L,
                        "NEGATIVE", 50L,
                        "NEUTRAL", 10L,
                        "Overall Sentiment Score", 45L)
        );
    }

}
