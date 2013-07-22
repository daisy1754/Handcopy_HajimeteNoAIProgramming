package jp.gr.java_conf.daisy.first_ai_programming.ch7;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class JankenAgent {
    static private final Map<Hand, ImmutableMap<Hand, BattleResult>> winLoseMap
            = ImmutableMap.of(
                Hand.ROCK, ImmutableMap.<Hand, BattleResult>of(
                    Hand.SCISSORS, BattleResult.WIN,
                    Hand.ROCK, BattleResult.EVEN,
                    Hand.PAPER, BattleResult.LOSE),
                Hand.SCISSORS, ImmutableMap.<Hand, BattleResult>of(
                    Hand.PAPER, BattleResult.WIN,
                    Hand.SCISSORS, BattleResult.EVEN,
                    Hand.ROCK, BattleResult.LOSE),
                Hand.PAPER, ImmutableMap.<Hand, BattleResult>of(
                    Hand.ROCK, BattleResult.WIN,
                    Hand.PAPER, BattleResult.EVEN,
                    Hand.SCISSORS, BattleResult.LOSE)
    );

    static private final Map<BattleResult, Integer> rewards = ImmutableMap.of(
            BattleResult.WIN, 1,
            BattleResult.EVEN, 0,
            BattleResult.LOSE, -1);
    static private final int INITIAL_SCORE = 10;
    static private final int NUM_OF_TRIAL = 1000;
    static private final int NUM_OF_OUTPUT = 10;
    Map<Hand, Map<Hand, Integer>> scores;
    Hand myHand, opponentHand;
    Hand lastOpponentHand;
    Opponent opponent = new RandomHand(NUM_OF_TRIAL);

    public static void main(String[] args) {
        new JankenAgent().doJanken();
    }

    private void init() {
        scores = new HashMap<Hand, Map<Hand, Integer>>();
        scores.put(Hand.ROCK, new HashMap<Hand, Integer>());
        scores.put(Hand.SCISSORS, new HashMap<Hand, Integer>());
        scores.put(Hand.PAPER, new HashMap<Hand, Integer>());
        for (Map<Hand, Integer> score: scores.values()) {
            for (Hand hand: Hand.values()) {
                score.put(hand, INITIAL_SCORE);
            }
        }
    }

    private void doJanken() {
        init();
        ResultCollector resultCollector =
                new ResultCollector(NUM_OF_TRIAL /NUM_OF_OUTPUT);
        myHand = Hand.ROCK; lastOpponentHand = Hand.ROCK;
        while (opponent.opponentHasNextHand()) {
            opponentHand = opponent.getOpponentHand();
            BattleResult result = battle(myHand, opponentHand);
            int reward = rewards.get(result);
            learn(reward, myHand, lastOpponentHand);
            myHand = determineHandByRoulette(opponentHand);
            lastOpponentHand = opponentHand;

            resultCollector.memoryResult(result);
            resultCollector.mayOutputResults();
        }
    }

    private BattleResult battle(Hand myHand, Hand opponentHand) {
        return winLoseMap.get(myHand).get(opponentHand);
    }

    private void learn(int reward, Hand myHand, Hand lastOpponentHand) {
        if (reward == 0)
            return;

        for (Hand hand: Hand.values()) {
            int alpha = (hand == myHand) ? 1 : -1;
            int score = scores.get(lastOpponentHand).get(hand);
            if (score + alpha * reward > 0) {
                scores.get(lastOpponentHand).put(hand, score + alpha * reward);
            }
        }
    }

    private Hand determineHandByRoulette(Hand opponentHand) {
        Map<Hand, Integer> score = scores.get(opponentHand);
        int scoreSum = 0;
        for (Integer s: score.values()) {
            scoreSum += s;
        }
        int randVal = (int) (Math.random() * scoreSum);
        if (randVal < score.get(Hand.ROCK)) {
            return Hand.ROCK;
        } else {
            randVal -= score.get(Hand.ROCK);
            if (randVal < score.get(Hand.SCISSORS)) {
                return Hand.SCISSORS;
            } else {
                return Hand.PAPER;
            }
        }
    }

    static private enum Hand {ROCK, SCISSORS, PAPER};
    static private enum BattleResult {WIN, EVEN, LOSE};
    static private interface Opponent {
        public boolean opponentHasNextHand();
        public Hand getOpponentHand();
    }
    static private class RandomHand implements Opponent {
        private int opponentCount = 0;
        private int numOfGetHands;

        public RandomHand(int numOfGetHands) {
            this.numOfGetHands = numOfGetHands;
        }

        @Override
        public boolean opponentHasNextHand() {
            opponentCount++;
            return (opponentCount < numOfGetHands);
        }

        @Override
        public Hand getOpponentHand() {
            int rest = opponentCount % 3;
            switch (rest) {
                case 0:
                    return Hand.ROCK;
                case 1:
                    return Hand.SCISSORS;
                case 2:
                    return Hand.PAPER;
            }
            return null;
        }
    }

    private class ResultCollector {
        int count = 0, numOfWin = 0;
        int countBeforeOutput;

        public ResultCollector(int countBeforeOutput) {
            this.countBeforeOutput = countBeforeOutput;
        }

        public void memoryResult(BattleResult result) {
            if (result == BattleResult.WIN) {
                numOfWin++;
            }
        }

        public void mayOutputResults() {
            count++;
            if (count >= countBeforeOutput) {
                System.out.println("last opponent: Rock" + scores.get(Hand.ROCK));
                System.out.println("last opponent: Scissors" + scores.get(Hand.SCISSORS));
                System.out.println("last opponent: Paper" + scores.get(Hand.PAPER));
                System.out.println((double)numOfWin / countBeforeOutput * 100);
                numOfWin = 0;
                count = 0;
            }
        }
    }
}
