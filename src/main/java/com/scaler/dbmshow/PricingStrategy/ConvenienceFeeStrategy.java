package com.scaler.dbmshow.PricingStrategy;

import com.scaler.dbmshow.dtos.PricingResultFinalDto;
import org.springframework.stereotype.Component;

@Component
public class ConvenienceFeeStrategy implements PricingStrategy{
    @Override
    public void apply(PricingResultFinalDto pricing) {
        double convenience_fee = 30;

        pricing.setConvenienceFee(convenience_fee);
        pricing.setTotalAmount(
                pricing.getTotalAmount() + convenience_fee
        );
    }
}
