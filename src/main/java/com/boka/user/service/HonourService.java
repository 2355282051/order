package com.boka.user.service;

import com.boka.common.constant.PageConstant;
import com.boka.user.model.Honour;
import com.boka.user.repository.HonourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonourService {

    @Autowired
    private HonourRepository honourRepository;


    public List<Honour> getHonours(String designerId, int page) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
        Sort sort = new Sort(order);
        Pageable pages = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE, sort);
        Page<Honour> list = honourRepository.findByDesignerId(designerId, pages);
        return list.getContent();
    }

}
