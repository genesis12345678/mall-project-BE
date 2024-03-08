package com.example.mallapi.controller;

import com.example.mallapi.dto.ProductDTO;
import com.example.mallapi.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil customFileUtil;

    @PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO) {
        log.info("register: {}", productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadedFileNames = customFileUtil.saveFiles(files);
        productDTO.setUploadedFileNames(uploadedFileNames);

        log.info("uploadedFileNames = {}", uploadedFileNames);

        return Map.of("RESULT", "SUCCESS");
    }
}
