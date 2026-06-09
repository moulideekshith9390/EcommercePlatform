package com.ecommerce.controller;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
  @RequestMapping("/api/cart")
  public class CartController {

    @Autowired private CartItemRepository cartItemRepository;
        @Autowired private ProductRepository productRepository;
        @Autowired private UserRepository userRepository;

    private User getUser(UserDetails ud) {
              return userRepository.findByEmail(ud.getUsername()).orElseThrow();
    }

    @GetMapping
        public ResponseEntity<List<CartItem>> getCart(@AuthenticationPrincipal UserDetails ud) {
                  return ResponseEntity.ok(cartItemRepository.findByUser(getUser(ud)));
        }

    @PostMapping
        public ResponseEntity<?> addToCart(@AuthenticationPrincipal UserDetails ud,
                                                                                  @RequestBody CartRequest req) {
                  User user = getUser(ud);
                  Product product = productRepository.findById(req.getProductId())
                                    .orElse(null);
                  if (product == null || !product.getActive()) {
                                return ResponseEntity.badRequest().body(Map.of("error", "Product not found"));
                  }
                  if (product.getStock() < req.getQuantity()) {
                                return ResponseEntity.badRequest().body(Map.of("error", "Insufficient stock"));
                  }
                  CartItem existing = cartItemRepository.findByUserAndProductId(user, req.getProductId())
                                    .orElse(null);
                  if (existing != null) {
                                existing.setQuantity(existing.getQuantity() + req.getQuantity());
                                return ResponseEntity.ok(cartItemRepository.save(existing));
                  }
                  CartItem item = CartItem.builder()
                                    .user(user).product(product).quantity(req.getQuantity()).build();
                  return ResponseEntity.ok(cartItemRepository.save(item));
        }

    @PutMapping("/{id}")
        public ResponseEntity<?> updateQuantity(@AuthenticationPrincipal UserDetails ud,
                                                                                             @PathVariable Long id,
                                                                                             @RequestBody CartRequest req) {
                  User user = getUser(ud);
                  return cartItemRepository.findById(id)
                                    .filter(item -> item.getUser().getId().equals(user.getId()))
                                    .map(item -> {
                                                          if (req.getQuantity() <= 0) {
                                                                                    cartItemRepository.delete(item);
                                                                                    return ResponseEntity.noContent().build();
                                                          }
                                                          item.setQuantity(req.getQuantity());
                                                          return ResponseEntity.ok(cartItemRepository.save(item));
                                    })
                                    .orElse(ResponseEntity.notFound().build());
        }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> removeFromCart(@AuthenticationPrincipal UserDetails ud,
                                                                                                   @PathVariable Long id) {
                  User user = getUser(ud);
                  return cartItemRepository.findById(id)
                                    .filter(item -> item.getUser().getId().equals(user.getId()))
                                    .map(item -> {
                                                          cartItemRepository.delete(item);
                                                          return ResponseEntity.noContent().<Void>build();
                                    })
                                    .orElse(ResponseEntity.notFound().build());
        }

    @Data
        static class CartRequest {
                  private Long productId;
                  private Integer quantity;
        }
  }
