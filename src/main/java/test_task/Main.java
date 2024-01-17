package test_task;

import java.time.Duration;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        TicketsService ticketsService = new TicketsService();
        List<Ticket> tickets= ticketsService.getTicketsFromJson("src/main/resources/tickets.json");

        Map<String, Duration> minTime = ticketsService.minTimeForEach(tickets,"Владивосток","Тель-Авив");
        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика:");
        minTime.forEach((key, value) -> System.out.println(key + " " + value.toHours() + "ч " + value.toMinutesPart() + "м"));

        System.out.println("Разницу между средней ценой  и медианой для полета между городами  Владивосток и Тель-Авив:");
        System.out.println(ticketsService.diffAverageAndMedianPrice(tickets,"Владивосток","Тель-Авив"));
    }
}