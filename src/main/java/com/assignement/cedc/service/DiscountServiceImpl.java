package com.assignement.cedc.service;

import com.assignement.cedc.constant.UserTypeConstants;
import com.assignement.cedc.dto.BillRequest;
import com.assignement.cedc.dto.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private double empDiscount = 0.30;
    private  double affDiscount= 0.10;
    private  double yoeDiscount = 0.05;


    public double applyDiscounts(BillRequest request) {
        logger.info("Started Calculating discount on bill");

        // Sum total amount
        List<Item> items = request.getItems();
        double totalAmount = request.getTotalAmount();
        logger.info("Bill total amount before applying discount {}",totalAmount);
        // Sum amount for non-grocery items
        double nonGroceryAmount = items.stream()
                .filter(item -> !"groceries".equalsIgnoreCase(item.getCategory()))
                .mapToDouble(Item::getPrice)
                .sum();
        logger.info("Non grocery item total  {}",nonGroceryAmount);

        // calculate  percentage discount (only on non-grocery items)
        double percentageDiscount = 0;
        if (nonGroceryAmount > 0) {
            percentageDiscount = getDiscountPercentage(request.getUserType(), request.getCustomerYears()) * nonGroceryAmount;
        }
        logger.info("Applicable percentage discount  {}",percentageDiscount);

        // Apply $5 discount for every $100 in the total bill
        double flatDiscount = ((totalAmount / 100)) * 5;
        logger.info("Applicable flat discount  {}",flatDiscount);
        logger.info("Applicable total discount  {}",(percentageDiscount + flatDiscount));

        // Final amount after applying discounts
        return totalAmount - (percentageDiscount + flatDiscount);
    }

    // Calculate the percentage discount based on user type and customer tenure
    private double getDiscountPercentage(String userType, int customerYears) {
        if (UserTypeConstants.EMPLOYEE.equalsIgnoreCase(userType)) {
            logger.info("{} Discount applied ",UserTypeConstants.EMPLOYEE);
            return empDiscount;  // 30% discount for employees
        } else if (UserTypeConstants.AFFILIATE.equalsIgnoreCase(userType)) {
            logger.info("Discount applied {}",UserTypeConstants.AFFILIATE);

            return affDiscount;  // 10% discount for affiliates
        } else if (customerYears > 2) {
            logger.info("Discount applied based on years of association");

            return yoeDiscount;  // 5% discount for customers over 2 years
        }
        return 0;
    }
}
