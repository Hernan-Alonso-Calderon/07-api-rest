package com.restaurant.restaurant_management.controllers;

import com.restaurant.restaurant_management.dto.OrderDetailRequestDTO;
import com.restaurant.restaurant_management.dto.OrderDetailResponseDTO;
import com.restaurant.restaurant_management.models.ClientOrder;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.OrderDetail;
import com.restaurant.restaurant_management.services.DishService;
import com.restaurant.restaurant_management.services.OrderDetailService;
import com.restaurant.restaurant_management.services.OrderService;
import com.restaurant.restaurant_management.utils.DtoOrderDetailConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {

  private final OrderDetailService orderDetailService;
  private final DishService dishService;
  private final OrderService orderService;

  public OrderDetailController(OrderDetailService orderDetailService, DishService dishService, OrderService orderService) {
    this.dishService = dishService;
    this.orderDetailService = orderDetailService;
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDetailResponseDTO> getOrderDetail(@PathVariable Long id){
    return orderDetailService.getOrderDetail(id)
        .map(orderDetail -> ResponseEntity.ok(DtoOrderDetailConverter.convertToResponseDTO(orderDetail)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<List<OrderDetailResponseDTO>> getOrderDetailsByOrderId(@PathVariable Long orderId){
    List<OrderDetail> orderDetails = orderDetailService.listOrderDetailsByOrderId(orderId);
    List<OrderDetailResponseDTO> response = orderDetails.stream()
        .map(DtoOrderDetailConverter::convertToResponseDTO)
        .toList();
    return ResponseEntity.ok(response);
  }


  @PostMapping
  public ResponseEntity<OrderDetailResponseDTO> saveOrderDetail(@RequestBody OrderDetailRequestDTO orderDetailRequestDTO) {
    try {
      Dish dish = dishService.getDish(orderDetailRequestDTO.getDishId()).orElseThrow();
      ClientOrder order = orderService.getOrder(orderDetailRequestDTO.getOrderId()).orElseThrow();
      OrderDetail orderDetail = DtoOrderDetailConverter.convertToOrderDetail(orderDetailRequestDTO, order, dish);
      OrderDetail newDetail = orderDetailService.saveOrderDetail(orderDetail);
      orderService.updateTotalOrder(order.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(DtoOrderDetailConverter.convertToResponseDTO(newDetail));
    }
    catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderDetailResponseDTO> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailRequestDTO orderDetailRequestDTO){
    try {
      Dish dish = dishService.getDish(orderDetailRequestDTO.getDishId()).orElseThrow();
      ClientOrder order = orderService.getOrder(orderDetailRequestDTO.getOrderId()).orElseThrow();
      OrderDetail updated = orderDetailService.updateOrderDetail(id, DtoOrderDetailConverter.convertToOrderDetail(orderDetailRequestDTO, order, dish));
      orderService.updateTotalOrder(order.getId());
      return ResponseEntity.ok(DtoOrderDetailConverter.convertToResponseDTO(updated));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id, @RequestParam("orderId") Long orderId){
    orderDetailService.deleteOrderDetail(id);
    orderService.updateTotalOrder(orderId);
    return ResponseEntity.noContent().build();
  }

}
