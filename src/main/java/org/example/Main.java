package org.example;

import org.example.infra.SlackNotifier;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<Integer> lotto = new HashSet<>();
        while (lotto.size() < 6) {
            lotto.add((int) (Math.random() * 45) + 1);
        }

        SlackNotifier notifier = new SlackNotifier();
        String message = "오늘의 추천 번호 : ";
        message += lotto.toString();
        notifier.sendMessage(message);

    }
}