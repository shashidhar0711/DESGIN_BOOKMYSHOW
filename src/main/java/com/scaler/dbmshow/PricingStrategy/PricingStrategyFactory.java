package com.scaler.dbmshow.PricingStrategy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricingStrategyFactory {
    private final List<PricingStrategy> strategies;

    public PricingStrategyFactory(List<PricingStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<PricingStrategy> getStrategies() {
        return strategies;
    }

}
