package com.gms.gms_meal.Rate_Package;

/**
 * Created by kam6376 on 2015-06-16.
 */
public class RateItemData {
  String rate, num, date;

  public RateItemData(String rate, String num, String date) {
    this.rate = rate;
    this.num = num;
    this.date = date;
  }

  public String getRate() {
    return this.rate;
  }

  public String getNum() {
    return this.num;
  }

  public String getDate() {
    return this.date;
  }
}
