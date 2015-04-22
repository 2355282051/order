package com.boka.user.repository;

public interface HonourRepositoryAdvance {


    /**
     * 统计发型师所有风采
     * @param designerId
     * @return
     */
    public long countHonourByDesigner(String designerId);


}
