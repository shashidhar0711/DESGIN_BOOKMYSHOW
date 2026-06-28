package com.scaler.dbmshow.PricingStrategy;

import com.scaler.dbmshow.dtos.PricingResultFinalDto;

public interface PricingStrategy {
    void apply(PricingResultFinalDto pricing);
}
