package pro.ephraimgibson.ibm.internproject.eventsync.service;

import org.springframework.stereotype.Component;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Component
public class SummaryAnalyzer {

    public Map<String, Object> countFeedbacks(List<Feedback> feedbacks) {

        long positiveCount = 0, negativeCount = 0, neutralCount = 0;

        positiveCount = getLabelCount(feedbacks, "POSITIVE");
        negativeCount = getLabelCount(feedbacks, "NEGATIVE");
        neutralCount = getLabelCount(feedbacks, "NEUTRAL");

        int totalCount = feedbacks.size();

        double positivePercentage = convertToPercentage(positiveCount, totalCount);
        double negativePercentage = convertToPercentage(negativeCount, totalCount);
        double neutralPercentage = convertToPercentage(neutralCount, totalCount);

        String sentimentCountAnalysis = analyseOverallSentimentByWeight(positiveCount, negativeCount, neutralCount);
        DecimalFormat df = new DecimalFormat("#.##");

        return Map.of(
                "Total Feedbacks", totalCount,
                "Sentiment Count", Map.of(
                        "POSITIVE", positiveCount,
                        "NEGATIVE", negativeCount,
                        "NEUTRAL", neutralCount
                ),
                "Percentage of Sentiments", Map.of(
                        "POSITIVE", Double.parseDouble(df.format(positivePercentage)),
                        "NEGATIVE", Double.parseDouble(df.format(negativePercentage)),
                        "NEUTRAL", Double.parseDouble(df.format(neutralPercentage))
                ),
                "Overall Sentiment", sentimentCountAnalysis
        );
    }

    private static double convertToPercentage(double entityCount, int totalCount) {
        return (entityCount / (double) totalCount) * 100;
    }

    private long getLabelCount(List<Feedback> feedbacks, String label) {
        return feedbacks.stream().filter(feedback -> label
                .equals(feedback.getTopSentiment())).count();
    }

    public Map<String, Object> sumUpAndCalculateAverageOfAllScores(List<Feedback> feedbacks) {

        Double allPositiveScores = 0.00, allNegativeScores = 0.00, allNeutralScores = 0.00;

        for (Feedback feedback : feedbacks) {
            allPositiveScores += feedback.getPositiveScore();
            allNegativeScores += feedback.getNegativeScore();
            allNeutralScores += feedback.getNeutralScore();
        }

        int total = feedbacks.size();
        double averageOfPositiveScores = convertToPercentage(allPositiveScores, total);
        double averageOfNegativeScores = convertToPercentage(allNegativeScores, total);
        double averageOfNeutralScores = convertToPercentage(allNeutralScores, total);

        double overallAverageScore = (averageOfPositiveScores - averageOfNegativeScores + 100) / 2;

        return Map.of("Average Feedback Scores", Map.of(
                "POSITIVE", Math.round(averageOfPositiveScores),
                "NEGATIVE", Math.round(averageOfNegativeScores),
                "NEUTRAL", Math.round(averageOfNeutralScores),
                "Overall Sentiment Score", Math.round(overallAverageScore)
        ));
    }

    private String analyseOverallSentimentByWeight(long positive, long negative, long neutral) {
        long total = positive + negative + neutral;

        if (total == 0) {
            return "NO_FEEDBACK";
        }

        double positiveWeight = 2.0;
        double negativeWeight = 1.5;

        double weightedPositive = (positive / (double) total) * positiveWeight;
        double weightedNegative = (negative / (double) total) * negativeWeight;

        double sentimentScore = weightedPositive - weightedNegative;

        return getAnalysisLabel(sentimentScore);
    }

    private String getAnalysisLabel(double sentimentScore) {
        if (sentimentScore > 1.5) {
            return "SUPER_POSITIVE";
        } else if (sentimentScore > 0.5) {
            return "MOSTLY_POSITIVE";
        } else if (sentimentScore > 0.0) {
            return "SLIGHTLY_POSITIVE";
        } else if (sentimentScore < -1.5) {
            return "SUPER_NEGATIVE";
        } else if (sentimentScore < -0.5) {
            return "MOSTLY_NEGATIVE";
        } else if (sentimentScore < 0.0) {
            return "SLIGHTLY_NEGATIVE";
        } else {
            return "MIXED";
        }
    }


}
