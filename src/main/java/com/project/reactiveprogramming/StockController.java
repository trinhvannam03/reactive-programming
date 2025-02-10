package com.project.reactiveprogramming;

import io.rsocket.core.RSocketConnector;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockPriceService stockPriceService;
    private final RSocketRequester.Builder builder;
    private RSocketRequester requester;

    @PostConstruct
    public void init() {
        requester = builder.tcp("localhost", 8081);
    }

    @GetMapping(value = "/stocks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Double> streamStockPrices() {
        return requester.route("acstream").data("Hello Reactor").retrieveFlux(Double.class);
    }

    @MessageMapping("acstream")
    public Flux<Double> getStockPriceACStream() {
        return stockPriceService.getStockPriceStream();
    }
}
