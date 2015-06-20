package com.gms.gms_meal.Meal_Package;

/**
 * Created by kam6376 on 2015-05-31.
 */
public class MealItemData {
  String date, detail, day;

  public MealItemData(String date, String day, String detail) {
    this.date = date;
    this.detail = detail;
    this.day = day;
  }

  public String getDate() {
    return this.date + " (" + this.day + ")";
  }

  public String getDetail() {
    return this.detail;
  }

}
