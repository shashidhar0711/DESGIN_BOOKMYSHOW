package com.scaler.dbmshow.dtos;

import lombok.Data;

@Data
public class PricingResultFinalDto {
    private double baseAmount;
    private double gst;
    private double convenienceFee;
    private double totalAmount;
}
