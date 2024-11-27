package com.aurealab.api.model.interfaces;

import java.util.Date;

public interface IPerson {
    Long getPersonId();
    String getDni();
    String getNames();
    String getSurnames();
    String getPhoneNumber();
    String getAddress();
    Date getBirthDate();
}
