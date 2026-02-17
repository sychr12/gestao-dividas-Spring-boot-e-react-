package com.financas.backend.dashboard;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @GetMapping
    public DashboardResponse getDashboard() {
        // Dados mockados para teste
        List<CardInfo> cards = Arrays.asList(
            new CardInfo("Nubank", 1500.0, 5000.0),
            new CardInfo("Itaú", 3000.0, 8000.0),
            new CardInfo("Bradesco", 2500.0, 6000.0)
        );

        List<ChartData> chartData = Arrays.asList(
            new ChartData("Nubank", 1500.0),
            new ChartData("Itaú", 3000.0),
            new ChartData("Bradesco", 2500.0)
        );

        double totalDebts = cards.stream()
            .mapToDouble(CardInfo::used)
            .sum();

        int cardsCount = cards.size();

        double totalUsed = cards.stream()
            .mapToDouble(CardInfo::used)
            .sum();
        
        double totalLimit = cards.stream()
            .mapToDouble(CardInfo::limit)
            .sum();

        int usedLimitPercent = (int) ((totalUsed / totalLimit) * 100);

        return new DashboardResponse(
            totalDebts,
            cardsCount,
            usedLimitPercent,
            cards,
            chartData
        );
    }

    // DTOs
    public record DashboardResponse(
        double totalDebts,
        int cardsCount,
        int usedLimitPercent,
        List<CardInfo> cards,
        List<ChartData> chartData
    ) {}

    public record CardInfo(
        String name,
        double used,
        double limit
    ) {}

    public record ChartData(
        String name,
        double value
    ) {}
}