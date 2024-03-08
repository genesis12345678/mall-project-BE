package com.example.mallapi.controller;

import com.example.mallapi.dto.ProductDTO;
import com.example.mallapi.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable("fileName") String fileName) {
        log.info("fileName = {}", fileName);
        return customFileUtil.getFile(fileName);
    }
}
