package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/11/26.
 */
public class IntegralInfo {
    private String Integral="0";
    private List<integralDetail> integralDetails = new ArrayList<>();

    public String getIntegral() {
        return Integral;
    }

    public void setIntegral(String integral) {
        Integral = integral;
    }

    public List<integralDetail> getIntegralDetails() {
        return integralDetails;
    }

    public void setIntegralDetails(List<integralDetail> integralDetails) {
        this.integralDetails = integralDetails;
    }
}
