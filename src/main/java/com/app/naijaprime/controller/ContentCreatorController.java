package com.app.naijaprime.controller;

import com.app.naijaprime.entity.ContentCreator;
import com.app.naijaprime.entity.MovieEnt;
import com.app.naijaprime.service.ContentCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/content-creators")
public class ContentCreatorController {
    @Autowired
    private ContentCreatorService contentCreatorService;

    @GetMapping("/{creatorId}/earnings")
    public ResponseEntity<Double> getTotalEarnings(@PathVariable Long creatorId) {
        Double totalEarnings = contentCreatorService.getEarnings(creatorId);
        return ResponseEntity.ok(totalEarnings);
    }
    @PostMapping("/{creatorId}/withdraw")
    public ResponseEntity<String> requestWithdrawal(@PathVariable Long creatorId, @RequestParam Double amount) {
        contentCreatorService.requestWithdrawal(creatorId, amount);
        return ResponseEntity.ok("Withdrawal request processed.");
    }
    @PostMapping("/create")
    public ResponseEntity<ContentCreator> createContentCreator(@RequestBody ContentCreator contentCreator) {
        return ResponseEntity.ok(contentCreatorService.createContentCreator(contentCreator));
    }

}
