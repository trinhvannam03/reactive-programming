package com.project.reactiveprogramming;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Service
public class StockPriceService {
    private final Random random = new Random();

    public Flux<Double> getStockPriceStream() {
        return Flux.interval(Duration.ofMillis(500))
                   .map(i -> 100 + random.nextDouble() * 20)
                   .log();
    }
}
