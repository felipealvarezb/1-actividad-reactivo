package com.example.reactivo.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ReactiveEndpoints {

  @GetMapping(value = "/numbers")
  public Flux<Integer> getPairNumbers() {
    Flux<Integer> numbers = Flux.range(1, 10)
            .filter(n -> n % 2 == 0)
            .map(n -> n * 2);
    return numbers;
  }

  @GetMapping(value = "/sum")
  public Mono<Integer> getSum() {
    Mono<Integer> sum = Flux.range(1, 10)
            .reduce((n, count) -> n + count);

    return sum;
  }

  @GetMapping(value = "/divide")
  public Flux<Integer> getDivisionByZero() {
    Flux<Integer> division = Flux.range(0, 10)
            .map(n -> n / 0)
            .onErrorResume(e -> Flux.just(-1));

    return division;
  }
}
