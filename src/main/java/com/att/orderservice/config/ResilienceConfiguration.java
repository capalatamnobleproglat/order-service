package com.att.orderservice.config;

import com.att.orderservice.client.UserClientFallback;
import feign.Feign;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceConfiguration {
    @Bean
    public Feign.Builder feignBuilder(CircuitBreakerRegistry circuitBreakerRegistry, RetryRegistry retryRegistry) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("user-service");
        Retry retry = retryRegistry.retry("user-service");

        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .withFallbackFactory(throwable -> new UserClientFallback())
                .withRetry(retry)
                .build();

        return Resilience4jFeign.builder(decorators);
    }
}
