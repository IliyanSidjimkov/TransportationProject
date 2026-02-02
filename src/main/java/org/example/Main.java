package org.example;

import org.example.dao.*;
import org.example.entity.*;
import org.example.service.ShipmentFileService;

import java.io.Console;
import java.time.LocalDate;
import java.util.List;

public class Main {


  public static void main(String[] args) {




    CompanyDao companyDao = new CompanyDao();
    Company company = companyDao.findById(3L);
    System.out.println(company);






  }
}

