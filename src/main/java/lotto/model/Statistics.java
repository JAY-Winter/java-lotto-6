package lotto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {

    private static final Map<MatchResult, Integer> PRIZE_MONEY = initializePrizeMoney();

    private final int totalAmount;
    private final List<MatchResult> matchResults;

    public Statistics(int totalAmount, List<MatchResult> matchResults) {
        this.totalAmount = totalAmount;
        this.matchResults = matchResults;
    }

    public float evaluateTotalProfit() {
        Map<MatchResult, Integer> counts = calculateMatchCounts();

        float totalEarningPrize = calculateTotalEaringPrize(counts);
        float totalProfit = getProfitRate(totalEarningPrize);
        return totalProfit;
    }

    private static Map<MatchResult, Integer> initializePrizeMoney() {
        Map<MatchResult, Integer> prizeMoney = new HashMap<>();
        prizeMoney.put(new MatchResult(3, false), 5_000);
        prizeMoney.put(new MatchResult(4, false), 50_000);
        prizeMoney.put(new MatchResult(5, false), 1_500_000);
        prizeMoney.put(new MatchResult(5, true), 30_000_000);
        prizeMoney.put(new MatchResult(6, false), 2_000_000_000);
        return prizeMoney;
    }

    private Map<MatchResult, Integer> calculateMatchCounts() {
        return matchResults.stream()
            .collect(Collectors.toMap(Function.identity(), matchResult -> 1, Integer::sum));
    }

    private int calculateTotalEaringPrize(Map<MatchResult, Integer> matchCounts) {
        return matchCounts.entrySet().stream()
            .mapToInt(entry -> entry.getValue() * PRIZE_MONEY.getOrDefault(entry.getKey(), 0))
            .sum();
    }

    private float getProfitRate(float totalWinnings) {
        float profitRate = (totalWinnings / totalAmount) * 100;
        return profitRate;
    }
}
