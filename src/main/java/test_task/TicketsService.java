package test_task;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketsService {
    private ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.registerModule(new JavaTimeModule());
    }
    public List<Ticket> getTicketsFromJson(String filePath) {
        InputStream is = this.getClass().getResourceAsStream(filePath);
        TicketDTO ticketDTO=new TicketDTO();
        try {
            ticketDTO = objectMapper.readValue(is, TicketDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketDTO.getTickets();
    }
    public Map<String, Duration> minTimeForEach(List<Ticket> tickets,String cityFrom, String cityTo) {
        return tickets.stream()
                .filter(t->t.getCityFrom().equals(cityFrom)&&t.getCityTo().equals(cityTo))
                .collect(Collectors.groupingBy(Ticket::getCarrier,
                        Collectors.collectingAndThen(Collectors.minBy(Comparator.comparing(t1->Duration.between(t1.getDepartureDateTime(),t1.getArrivalDateTime()))),
                                ticket -> ticket.map(t -> Duration.between(t.getDepartureDateTime(), t.getArrivalDateTime())).orElse(Duration.ZERO))));
    }

    public Double diffAverageAndMedianPrice(List<Ticket> tickets,String cityFrom, String cityTo){
        List<Double> sortTickets = tickets.stream().filter(t->t.getCityFrom().equals(cityFrom)&&t.getCityTo().equals(cityTo)).
                mapToDouble(Ticket::getPrice)
                .sorted().boxed().toList();
        if(sortTickets.isEmpty() || sortTickets.size()==1) {
            return 0d;
        }
        int size = sortTickets.size()/2;
        double medianPrice = sortTickets.size()%2==1? sortTickets.get(size) :
                (sortTickets.get(size)+sortTickets.get(size-1))/2;
        double averagePrice = sortTickets.stream().reduce(0d,Double::sum)/sortTickets.size();
        return averagePrice-medianPrice;
    }
    @Data
    private static class TicketDTO {
        private List<Ticket> tickets;
    }
}
