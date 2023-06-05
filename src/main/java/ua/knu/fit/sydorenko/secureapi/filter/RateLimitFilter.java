package ua.knu.fit.sydorenko.secureapi.filter;

import io.github.bucket4j.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ua.knu.fit.sydorenko.secureapi.exception.RateLimitException;

import java.time.Duration;

@Configuration
@Slf4j
public class RateLimitFilter implements WebFilter {

    private Bucket bucket;

    public RateLimitFilter() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(1, refill);
        bucket = Bucket4j.builder().withNanosecondPrecision().addLimit(limit).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ConsumptionProbe consumptionProbe = getBucket().tryConsumeAndReturnRemaining(1);
        Long remainingLimit = consumptionProbe.getRemainingTokens();
        if (!consumptionProbe.isConsumed()) {
            return Mono.error(new RateLimitException("TOO MANY REQUEST"));
        }
        response.getHeaders().set("X-Rate-Limit-Remaining", String.valueOf(remainingLimit));
        return chain.filter(exchange);
    }

    public Bucket getBucket() {
        return this.bucket;
    }
}
