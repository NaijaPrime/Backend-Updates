package com.app.naijaprime.service;

import com.app.naijaprime.entity.ContentCreator;
import com.app.naijaprime.repo.ContentCreatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentCreatorServiceImpl implements ContentCreatorService{
    @Autowired
    private ContentCreatorRepo contentCreatorRepo;

    @Override
    public Double getEarnings(Long creatorId) {
        ContentCreator creator = contentCreatorRepo.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Content Creator not found"));
        return creator.getTotalEarnings();
    }

    @Override
    public void requestWithdrawal(Long creatorId, Double amount) {
        ContentCreator creator = contentCreatorRepo.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Content Creator not found"));

        if (amount > creator.getTotalEarnings()) {
            throw new RuntimeException("Insufficient earnings for withdrawal");
        }

        creator.setTotalEarnings(creator.getTotalEarnings() - amount);
        contentCreatorRepo.save(creator);
    }

    @Override
    public ContentCreator createContentCreator(ContentCreator contentCreator) {
        ContentCreator creator = new ContentCreator();
        creator.setName(contentCreator.getName());
        creator.setEmail(contentCreator.getEmail());
        creator.setTotalEarnings(0.0);

        return contentCreatorRepo.save(creator);

    }

}

