package com.ecommerce.model;

  import jakarta.persistence.*;
  import jakarta.validation.constraints.Email;
  import jakarta.validation.constraints.NotBlank;
  import lombok.AllArgsConstructor;
  import lombok.Builder;
  import lombok.Data;
  import lombok.NoArgsConstructor;

  import java.time.LocalDateTime;
  import java.util.ArrayList;
  import java.util.List;

  @Entity
  @Table(name = "users")
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @NotBlank
      @Column(nullable = false)
      private String name;

      @Email
      @NotBlank
      @Column(nullable = false, unique = true)
      private String email;

      @NotBlank
      @Column(nullable = false)
      private String password;

      @Enumerated(EnumType.STRING)
      @Column(nullable = false)
      @Builder.Default
      private Role role = Role.CUSTOMER;

      private String phone;
      private String address;

      @Column(name = "created_at")
      @Builder.Default
      private LocalDateTime createdAt = LocalDateTime.now();

      @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      @Builder.Default
      private List<CartItem> cartItems = new ArrayList<>();

      @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      @Builder.Default
      private List<Order> orders = new ArrayList<>();
  }
