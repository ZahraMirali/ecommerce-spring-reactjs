package com.example.demo.controller;

import com.example.demo.dto.review.ReviewRequest;
import com.example.demo.dto.review.ReviewResponse;
import com.example.demo.mapper.ReviewMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_REVIEW)
public class ReviewController {

    private final ReviewMapper reviewMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping(PERFUME_ID)
    public ResponseEntity<List<ReviewResponse>> getReviewsByPerfumeId(@PathVariable Long perfumeId) {
        return ResponseEntity.ok(reviewMapper.getReviewsByPerfumeId(perfumeId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReviewToPerfume(@Valid @RequestBody ReviewRequest reviewRequest,
                                                             BindingResult bindingResult) {
        ReviewResponse review = reviewMapper.addReviewToPerfume(reviewRequest, reviewRequest.getPerfumeId(), bindingResult);
        messagingTemplate.convertAndSend("/topic/reviews/" + reviewRequest.getPerfumeId(), review);
        return ResponseEntity.ok(review);
    }
}

// SimpMessagingTemplate is for WebSocket-based messaging. the client that is subscribed to the specified destination will receive the message.