package com.app.naijaprime.service;

import com.app.naijaprime.entity.ContentCreator;

public interface ContentCreatorService {
    public Double getEarnings(Long creatorId);
    public void requestWithdrawal(Long creatorId, Double amount);
    public ContentCreator createContentCreator(ContentCreator contentCreator);
}
