package org.example;

import org.example.dao.*;
import org.example.entity.*;
import org.example.service.ShipmentFileService;

import java.time.LocalDate;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    CompanyDao companyDao = new CompanyDao();
    EmployeeDao employeeDao = new EmployeeDao();
    ClientDao clientDao = new ClientDao();
    VehicleDao vehicleDao = new VehicleDao();
    ShipmentDao shipmentDao = new ShipmentDao();

    /*  1. Companies */
    Company c1 = new Company();
    c1.setCompanyName("Speed Transport");
    companyDao.create(c1);

    Company c2 = new Company();
    c2.setCompanyName("Global Logistics");
    companyDao.create(c2);

    System.out.println("✔ Companies created");

        /*  2. Employees  */
    Employee e1 = new Employee();
    e1.setFirstName("Иван");
    e1.setLastName("Иванов");
    e1.setPosition("Шофьор");
    e1.setSalary(2200.0);
    employeeDao.create(e1);

    Employee e2 = new Employee();
    e2.setFirstName("Петър");
    e2.setLastName("Петров");
    e2.setPosition("Шофьор");
    e2.setSalary(1800.0);
    employeeDao.create(e2);

    Employee e3 = new Employee();
    e3.setFirstName("Георги");
    e3.setLastName("Георгиев");
    e3.setPosition("Шофьор");
    e3.setSalary(3000.0);
    employeeDao.create(e3);

    System.out.println("Employees created");


    employeeDao.hireEmployee(e1.getId(), c1.getId());
    employeeDao.hireEmployee(e2.getId(), c1.getId());
    employeeDao.hireEmployee(e3.getId(), c1.getId());

    employeeDao.hireEmployee(e3.getId(), c2.getId());

    System.out.println("Employees assigned to companies");

        /*   3. Clients */
    Client cl1 = new Client();
    cl1.setClientName("ABC Logistics");
    cl1.setCompany(c1);
    clientDao.create(cl1);

    Client cl2 = new Client();
    cl2.setClientName("Mega Trade");
    cl2.setCompany(c1);
    clientDao.create(cl2);

    System.out.println("Clients created");

        /*  4. Vehicles */
    Vehicle v1 = new Vehicle();
    v1.setRegistrationNumber("CB1111AA");
    v1.setType("Камион");
    v1.setCapacity(12000.0);
    v1.setCompany(c1);
    v1.setDriver(e1);
    vehicleDao.create(v1);

    Vehicle v2 = new Vehicle();
    v2.setRegistrationNumber("CB2222BB");
    v2.setType("Камион");
    v2.setCapacity(15000.0);
    v2.setCompany(c1);
    v2.setDriver(e2);
    vehicleDao.create(v2);

    System.out.println("Vehicles created");

        /* 5. Shipments */
    Shipment s1 = new Shipment();
    s1.setDestination("София – Варна");
    s1.setCargo("Мебели");
    s1.setPrice(1500.0);
    s1.setShipmentDate(LocalDate.now().minusDays(3));
    s1.setPaymentStatus(PaymentStatus.PAID);
    s1.setCompany(c1);
    s1.setClient(cl1);
    s1.setVehicle(v1);
    shipmentDao.create(s1);

    Shipment s2 = new Shipment();
    s2.setDestination("Пловдив – Бургас");
    s2.setCargo("Електроника");
    s2.setPrice(2300.0);
    s2.setShipmentDate(LocalDate.now().minusDays(1));
    s2.setPaymentStatus(PaymentStatus.PAID);
    s2.setCompany(c2);
    s2.setClient(cl2);
    s2.setVehicle(v2);
    shipmentDao.create(s2);

    Shipment s3 = new Shipment();
    s3.setDestination("София – Русе");
    s3.setCargo("Строителни материали");
    s3.setPrice(3200.0);
    s3.setShipmentDate(LocalDate.now());
    s3.setPaymentStatus(PaymentStatus.PAID);
    s3.setCompany(c1);
    s3.setClient(cl1);
    s3.setVehicle(v1);
    shipmentDao.create(s3);

    System.out.println("Shipments created");

        /*  6. Sorting */
    System.out.println("\n--- Employees ordered by salary ---");
    employeeDao.findAllOrderBySalaryDesc()
            .forEach(e ->
                    System.out.println(
                            e.getFirstName() + " " +
                                    e.getLastName() + " → " +
                                    e.getSalary()
                    )
            );

    System.out.println("\n--- Shipments with destination containing 'София' ---");
    shipmentDao.findByDestination("София")
            .forEach(s ->
                    System.out.println(
                            s.getDestination() + " → " + s.getPrice()
                    )
            );

        /* 7. Reports */
    System.out.println("\n--- Total shipments ---");
    System.out.println(shipmentDao.getTotalShipmentsCount());

    System.out.println("\n--- Total revenue ---");
    System.out.println(shipmentDao.getTotalRevenue() + " лв.");

    System.out.println("\n--- Shipments per driver ---");
    shipmentDao.getShipmentsCountPerDriver()
            .forEach(r ->
                    System.out.println(
                            r[0] + " " + r[1] + " → " + r[2]
                    )
            );

    System.out.println("\n--- Revenue per driver ---");
    shipmentDao.getRevenuePerDriver()
            .forEach(r ->
                    System.out.println(
                            r[0] + " " + r[1] + " → " + r[2] + " лв."
                    )
            );

    System.out.println("\n--- Company1 revenue for last 7 days ---");
    System.out.println(
            shipmentDao.getCompanyRevenueForPeriod(
                    c1,
                    LocalDate.now().minusDays(7),
                    LocalDate.now()
            ) + " лв."
    );
    System.out.println("\n--- Company2 revenue for last 7 days ---");
    System.out.println(
            shipmentDao.getCompanyRevenueForPeriod(
                    c2,
                    LocalDate.now().minusDays(7),
                    LocalDate.now()
            ) + " лв."
    );


           //8. File save / load //

    List<Shipment> allShipments = shipmentDao.findAll();
    ShipmentFileService.writeToFile(allShipments);

    System.out.println("\n--- Shipments read from file ---");
    ShipmentFileService.readFromFile()
            .forEach(System.out::println);


  }
}
