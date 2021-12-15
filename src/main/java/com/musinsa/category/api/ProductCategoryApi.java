package com.musinsa.category.api;

import com.musinsa.category.productcategory.command.application.DeleteProductCategoryService;
import com.musinsa.category.productcategory.command.application.ModifyProductCategoryService;
import com.musinsa.category.productcategory.command.application.RegisterProductCategoryService;
import com.musinsa.category.productcategory.command.dto.ProductCategoryModifyDto;
import com.musinsa.category.productcategory.command.dto.ProductCategoryRegisterDto;
import com.musinsa.category.productcategory.query.application.ProductCategoryQueryService;
import com.musinsa.category.productcategory.query.dto.ProductCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("product-categories")
@RequiredArgsConstructor
public class ProductCategoryApi {

    private final ProductCategoryQueryService productCategoryQueryService;
    private final DeleteProductCategoryService deleteProductCategoryService;
    private final ModifyProductCategoryService modifyProductCategoryService;
    private final RegisterProductCategoryService registerProductCategoryService;

    /**
     * 전체 상품카테고리 조회
     *
     * @return 전체 상품카테고리(tree구조)
     */
    @GetMapping
    public List<ProductCategoryDto> getAllProductCategories() {
        return productCategoryQueryService.getAllProductCategories();
    }

    /**
     * 최상위 상품카테고리 등록
     *
     * @param registerDto 등록 정보 DTO
     */
    @PostMapping
    public ResponseEntity<?> registerProductCategory(@RequestBody @Valid ProductCategoryRegisterDto registerDto) {
        registerProductCategoryService.registerProductCategory(registerDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 하위 상품카테고리 등록
     *
     * @param productCategoryCode 부모 상품카테고리코드
     * @param registerDto         등록 정보 DTO
     */
    @PostMapping("{productCategoryCode}")
    public ResponseEntity<?> registerChildProductCategory(@PathVariable String productCategoryCode,
                                                          @RequestBody @Valid ProductCategoryRegisterDto registerDto) {
        registerProductCategoryService.registerChildProductCategory(productCategoryCode, registerDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 코드에 해당하는 상품카테고리 조회
     * 하위 항목이 있는 경우 포함하여 조회
     *
     * @param productCategoryCode 조회 대상 상품카테고리 코드
     * @return 상품카테고리(tree구조)
     */
    @GetMapping("{productCategoryCode}")
    public ProductCategoryDto getProductCategory(@PathVariable String productCategoryCode) {
        return productCategoryQueryService.getProductCategory(productCategoryCode);
    }

    /**
     * 상품카테고리 수정
     *
     * @param productCategoryCode 상품카테고리 코드
     * @param modifyDto           수정 정보 DTO
     */
    @PutMapping("{productCategoryCode}")
    public ResponseEntity<?> modifyProductCategory(@PathVariable String productCategoryCode,
                                                   @RequestBody @Valid ProductCategoryModifyDto modifyDto) {
        modifyProductCategoryService.modifyProductCategory(productCategoryCode, modifyDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 상품카테고리 삭제
     * 하위 항목이 있는 경우 포함하여 삭제
     *
     * @param productCategoryCode 삭제할 상품카테고리 코드
     */
    @DeleteMapping("{productCategoryCode}")
    public ResponseEntity<?> deleteProductCategory(@PathVariable String productCategoryCode) {
        deleteProductCategoryService.deleteProductCategory(productCategoryCode);
        return ResponseEntity.ok().build();
    }

}
