package com.scaler.dbmshow.PricingStrategy;

import com.scaler.dbmshow.dtos.PricingResultFinalDto;
import org.springframework.stereotype.Component;

@Component
public class GstStrategy implements PricingStrategy{
    @Override
    public void apply(PricingResultFinalDto pricing) {
        double gst = pricing.getBaseAmount() * 0.18;

        pricing.setGst(gst);
        pricing.setTotalAmount(
                pricing.getTotalAmount() + gst
        );
    }
}
