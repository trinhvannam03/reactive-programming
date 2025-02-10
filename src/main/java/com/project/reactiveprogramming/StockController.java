package com.project.reactiveprogramming;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockPriceService stockPriceService;


    @GetMapping(value = "/stocks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Double> streamStockPrices() {
        return stockPriceService.getStockPriceStream();
    }
}
