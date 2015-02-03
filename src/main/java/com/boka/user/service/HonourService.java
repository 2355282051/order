package com.boka.user.service;

import com.boka.common.constant.PageConstant;
import com.boka.user.model.Designer;
import com.boka.user.model.Honour;
import com.boka.user.repository.DesignerRepository;
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
    @Autowired
    private DesignerRepository designerRepository;


    public List<Honour> getHonours(String designerId, int page) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
        Sort sort = new Sort(order);
        Pageable pages = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE, sort);
        Page<Honour> list = honourRepository.findByDesignerId(designerId, pages);
        return list.getContent();
    }


    public void addHonour(Honour honour) {
        Designer designer = designerRepository.findOne(honour.getDesignerId());
        honour.setDesigner(designer);
        honourRepository.save(honour);
    }

}
