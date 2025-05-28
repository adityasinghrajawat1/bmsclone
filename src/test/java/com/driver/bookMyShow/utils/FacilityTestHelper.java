package com.driver.bookMyShow.utils;

import com.driver.bookMyShow.Models.Facility;

public class FacilityTestHelper {
      public static Facility createFacilityInstance(String name,String logo){
        Facility facility=new Facility();
        facility.setId(Utils.generateUUID(10));
        facility.setIsActive(true);
        facility.setDeleted(false);
        facility.setName(name);
        facility.setLogo(logo);
        return facility;
      }
}
