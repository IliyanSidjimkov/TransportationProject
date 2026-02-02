package org.example.service;

import org.example.entity.Shipment;
import org.example.entity.PaymentStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentFileService {

  private static final String FILE_NAME = "shipments.txt";

  // Запис във файл
  public static void writeToFile(List<Shipment> shipments) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

      writer.write("ID;DESTINATION;CARGO;PRICE;PAYMENT_STATUS");
      writer.newLine();

      for (Shipment s : shipments) {
        writer.write(
          s.getId() + ";" +
            s.getDestination() + ";" +
            s.getCargo() + ";" +
            s.getPrice() + ";" +
            s.getPaymentStatus()
        );
        writer.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Четене от файл
  public static List<Shipment> readFromFile() {
    List<Shipment> shipments = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
      String line;
      reader.readLine(); // пропускаме header-а

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(";");

        Shipment s = new Shipment();
        s.setId(Long.parseLong(data[0]));
        s.setDestination(data[1]);
        s.setCargo(data[2]);
        s.setPrice(Double.parseDouble(data[3]));
        s.setPaymentStatus(PaymentStatus.valueOf(data[4]));

        shipments.add(s);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return shipments;
  }
}
