package com.assignement.cedc.service;

import com.assignement.cedc.dto.BillRequest;
import org.springframework.stereotype.Service;

public interface DiscountService {
    public double applyDiscounts(BillRequest request) ;

}
