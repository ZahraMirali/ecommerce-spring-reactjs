package com.example.demo.controller;

import com.example.demo.dto.GraphQLRequest;
import com.example.demo.dto.HeaderResponse;
import com.example.demo.dto.order.OrderItemResponse;
import com.example.demo.dto.order.OrderRequest;
import com.example.demo.dto.order.OrderResponse;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.graphql.GraphQLProvider;
import graphql.ExecutionResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.PathConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ORDER)
public class OrderController {

    private final OrderMapper orderMapper;
    private final GraphQLProvider graphQLProvider;

    @GetMapping(ORDER_ID)
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.getOrderById(orderId));
    }

    @GetMapping(ORDER_ID_ITEMS)
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.getOrderItemsByOrderId(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal UserPrincipal user,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<OrderResponse> response = orderMapper.getUserOrders(user.getEmail(), pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order, BindingResult bindingResult) {
        return ResponseEntity.ok(orderMapper.postOrder(order, bindingResult));
    }

    @PostMapping(GRAPHQL)
    public ResponseEntity<ExecutionResult> getUserOrdersByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }
}


// When a user is authenticated, the Authentication object is created and stored in the security context. The @AuthenticationPrincipal annotation allows you to directly access the principal object from the Authentication object without having to manually retrieve it.