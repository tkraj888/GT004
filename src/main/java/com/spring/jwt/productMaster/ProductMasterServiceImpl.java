package com.spring.jwt.productMaster;

import com.spring.jwt.entity.ProductMaster;
import com.spring.jwt.exception.DuplicateProductException;
import com.spring.jwt.exception.ProductMasterIdNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductMasterRepo productMasterRepo;


    public ProductMasterDTO saveProductMaster(ProductMasterDTO productMasterDTO) {
        ProductMaster productMaster = new ProductMaster();

        productMaster.setName(productMasterDTO.getName());
        productMaster.setBrand(productMasterDTO.getBrand());
        productMaster.setStock90ml(productMasterDTO.getStock90ml());
        productMaster.setStock180ml(productMasterDTO.getStock180ml());
        productMaster.setStock360ml(productMasterDTO.getStock360ml());
        productMaster.setStock760ml(productMasterDTO.getStock760ml());
        productMaster.setStock1Liter(productMasterDTO.getStock1Liter());
        productMaster.setStock2Liter(productMasterDTO.getStock2Liter());
        productMaster.setType(productMasterDTO.getType());
        productMaster.setMainType(productMasterDTO.getMainType());

        ProductMaster existingProduct = productMasterRepo.findByNameAndBrand(productMaster.getName(), productMaster.getBrand());
        if (existingProduct != null) {
            throw new DuplicateProductException("Product with the same name and brand already exists.");
        }

        ProductMaster savedProduct = productMasterRepo.save(productMaster);

        ProductMasterDTO savedProductDTO = new ProductMasterDTO();
        savedProductDTO.setProductMasterId(savedProduct.getProductMasterId());
        savedProductDTO.setName(savedProduct.getName());
        savedProductDTO.setBrand(savedProduct.getBrand());
        savedProductDTO.setStock90ml(savedProduct.getStock90ml());
        savedProductDTO.setStock180ml(savedProduct.getStock180ml());
        savedProductDTO.setStock360ml(savedProduct.getStock360ml());
        savedProductDTO.setStock760ml(savedProduct.getStock760ml());
        savedProductDTO.setStock1Liter(savedProduct.getStock1Liter());
        savedProductDTO.setStock2Liter(savedProduct.getStock2Liter());
        savedProductDTO.setType(savedProduct.getType());
        savedProductDTO.setMainType(savedProduct.getMainType());

        return savedProductDTO;
    }


    @Override
    public ProductMasterDTO getProductByID(Integer id) {
        ProductMaster productMaster = productMasterRepo.findById(id)
                .orElseThrow(() -> new ProductMasterIdNotFound("ProductMaster not found with id: " + id));
        ProductMasterDTO productMasterDTO = new ProductMasterDTO();
        productMasterDTO.setProductMasterId(productMaster.getProductMasterId());
        productMasterDTO.setName(productMaster.getName());
        productMasterDTO.setBrand(productMaster.getBrand());
        productMasterDTO.setStock90ml(productMaster.getStock90ml());
        productMasterDTO.setStock180ml(productMaster.getStock180ml());
        productMasterDTO.setStock360ml(productMaster.getStock360ml());
        productMasterDTO.setStock760ml(productMaster.getStock760ml());
        productMasterDTO.setStock1Liter(productMaster.getStock1Liter());
        productMasterDTO.setStock2Liter(productMaster.getStock2Liter());
        productMasterDTO.setType(productMaster.getType());
        productMasterDTO.setMainType(productMaster.getMainType());

        return productMasterDTO;
    }


    @Override
    public List<ProductMasterDTO> getAllProducts(Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
        pageable.getSort().descending();
        Page<ProductMaster> productPage = productMasterRepo.findAll(pageable);

        return productPage.getContent().stream()
                .map(product -> {
                    ProductMasterDTO productDTO = new ProductMasterDTO();
                    productDTO.setProductMasterId(product.getProductMasterId());
                    productDTO.setName(product.getName());
                    productDTO.setBrand(product.getBrand());
                    productDTO.setStock90ml(product.getStock90ml());
                    productDTO.setStock180ml(product.getStock180ml());
                    productDTO.setStock360ml(product.getStock360ml());
                    productDTO.setStock760ml(product.getStock760ml());
                    productDTO.setStock1Liter(product.getStock1Liter());
                    productDTO.setStock2Liter(product.getStock2Liter());
                    productDTO.setType(product.getType());
                    productDTO.setMainType(product.getMainType());
                    return productDTO;
                })
                .collect(Collectors.toList()); // Convert to List<ProductMasterDTO>
    }




    @Override
    public void deleteProductByID(Integer id) {
        ProductMaster productMaster = productMasterRepo.findById(id)
                .orElseThrow(() -> new ProductMasterIdNotFound("Product not found with id: " + id));
        productMasterRepo.delete(productMaster);
    }

    @Override
    public List<ProductMasterDTO> getProductByBrandAndName(String brand, String name) {
        List<ProductMaster> products = productMasterRepo.findByBrandContainingIgnoreCaseAndNameContainingIgnoreCase(brand, name);

        if (products.isEmpty()) {
            throw new ProductMasterIdNotFound("No products found for brand: " + brand + " and name: " + name);
        }

        // Convert Entity List to DTO List
        return products.stream().map(ProductMasterDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProductMasterDTO updateProductMasterByID(Integer id, ProductMasterDTO productMasterDTO) {
        ProductMaster productMaster = productMasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductMaster not found with id: " + id));

        if (productMasterDTO.getName() != null) {
            productMaster.setName(productMasterDTO.getName());
        }
        if (productMasterDTO.getBrand() != null) {
            productMaster.setBrand(productMasterDTO.getBrand());
        }
        if (productMasterDTO.getStock90ml() != null) {
            productMaster.setStock90ml(productMasterDTO.getStock90ml());
        }
        if (productMasterDTO.getStock180ml() != null) {
            productMaster.setStock180ml(productMasterDTO.getStock180ml());
        }
        if (productMasterDTO.getStock360ml() != null) {
            productMaster.setStock360ml(productMasterDTO.getStock360ml());
        }
        if (productMasterDTO.getStock760ml() != null) {
            productMaster.setStock760ml(productMasterDTO.getStock760ml());
        }
        if (productMasterDTO.getStock1Liter() != null) {
            productMaster.setStock1Liter(productMasterDTO.getStock1Liter());
        }
        if (productMasterDTO.getStock2Liter() != null) {
            productMaster.setStock2Liter(productMasterDTO.getStock2Liter());
        }
        if (productMasterDTO.getType() != null) {
            productMaster.setType(productMasterDTO.getType());
        }
        if (productMasterDTO.getMainType() != null) {
            productMaster.setMainType(productMasterDTO.getMainType());
        }

        ProductMaster updatedProductMaster = productMasterRepo.save(productMaster);

        ProductMasterDTO updatedProductDTO = new ProductMasterDTO();
        updatedProductDTO.setProductMasterId(updatedProductMaster.getProductMasterId());
        updatedProductDTO.setName(updatedProductMaster.getName());
        updatedProductDTO.setBrand(updatedProductMaster.getBrand());
        updatedProductDTO.setStock90ml(updatedProductMaster.getStock90ml());
        updatedProductDTO.setStock180ml(updatedProductMaster.getStock180ml());
        updatedProductDTO.setStock360ml(updatedProductMaster.getStock360ml());
        updatedProductDTO.setStock760ml(updatedProductMaster.getStock760ml());
        updatedProductDTO.setStock1Liter(updatedProductMaster.getStock1Liter());
        updatedProductDTO.setStock2Liter(updatedProductMaster.getStock2Liter());
        updatedProductDTO.setType(updatedProductMaster.getType());
        updatedProductDTO.setMainType(updatedProductMaster.getMainType());

        return updatedProductDTO;
    }


}

