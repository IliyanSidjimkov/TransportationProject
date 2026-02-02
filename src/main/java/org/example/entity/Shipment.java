package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "shipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "destination", nullable = false)
  private String destination;

  @Column(name = "cargo", nullable = false)
  private String cargo;

  @Column(name = "price", nullable = false)
  private Double price;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @Column(name = "shipment_date", nullable = false)
  private LocalDate shipmentDate;

  // Компанията, която извършва превоза
  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  // Клиентът, който е направил поръчката
  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  // Превозното средство
  @ManyToOne
  @JoinColumn(name = "vehicle_id", nullable = false)
  private Vehicle vehicle;
}

