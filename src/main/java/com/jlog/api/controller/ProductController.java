package com.jlog.api.controller;

import com.jlog.api.domain.Product;
import com.jlog.api.repository.DeptRepository;
import com.jlog.api.repository.FactoryRepository;
import com.jlog.api.repository.ProductRepository;
import com.jlog.api.response.DeptResponse;
import com.jlog.api.response.FactoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final DeptRepository deptRepository;
    private final FactoryRepository factoryRepository;
    private final ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getProduct() {
        return productRepository.findAllBy();
    }

    @GetMapping("/factories")
    public List<FactoryResponse> getFactory() {
        return factoryRepository.findAllBy().stream()
                .map(FactoryResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/depts")
    public List<DeptResponse> getDepts() {
        return deptRepository.findAllBy().stream()
                .map(DeptResponse::new)
                .collect(Collectors.toList());
    }

}
