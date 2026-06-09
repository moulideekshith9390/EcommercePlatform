package com.ecommerce.model;

  import jakarta.persistence.*;
  import lombok.*;

  import java.math.BigDecimal;
  import java.time.LocalDateTime;
  import java.util.ArrayList;
  import java.util.List;

  @Entity
  @Table(name = "orders")
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class Order {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id", nullable = false)
      @ToString.Exclude
      private User user;

      @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
      @Builder.Default
      private List<OrderItem> items = new ArrayList<>();

      @Column(nullable = false, precision = 10, scale = 2)
      private BigDecimal totalAmount;

      @Enumerated(EnumType.STRING)
      @Column(nullable = false)
      @Builder.Default
      private OrderStatus status = OrderStatus.PENDING;

      private String shippingAddress;
      private String paymentMethod;

      @Column(name = "created_at")
      @Builder.Default
      private LocalDateTime createdAt = LocalDateTime.now();

      @Column(name = "updated_at")
      @Builder.Default
      private LocalDateTime updatedAt = LocalDateTime.now();

      public enum OrderStatus {
          PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
      }
  }
