package com.example.clonebuysell.controllers;

import com.example.clonebuysell.dto.ProductDto;
import com.example.clonebuysell.exception.productException.ProductNotFoundException;
import com.example.clonebuysell.models.Product;
import com.example.clonebuysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public String filterProducts(@RequestParam(name = "title", required = false
    ) String title, Model model, Principal principal) {
        List<Product> productList = productService.filteredProductList(title);
        model.addAttribute("products", productList);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }

    @GetMapping("/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal,
                              HttpServletRequest request) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("images", product.getImagesPathList());
            model.addAttribute("productUserId", product.getUser().getId());
            model.addAttribute("user", productService.getUserByPrincipal(principal));
            if (principal == null)
                request.getSession(true).setAttribute("SESSION_REDIRECT_URL", request.getRequestURI());
        } catch (ProductNotFoundException exception) {
            model.addAttribute("productException", exception.getMessage());
        }
        return "product-info";
    }

    @PostMapping("/create")
    public String createProduct(@RequestParam(value = "file") MultipartFile[] file,
                                @Valid @ModelAttribute ProductDto productDto,
                                BindingResult bindingResult, Model model, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDto);
            return "/new-product";
        }
        try {
            productService.saveProduct(principal, productDto, file);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("imageAddError", exception.getMessage());
            model.addAttribute("product", productDto);
            return "/new-product";
        }

        return "redirect:/";
    }

    @GetMapping("/new")
    public String newProduct() throws IOException {
        return "new-product";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/product-edit")
    public String editProduct(@PathVariable Long id, Model model, Principal principal) {
        if (principal.getName().equals(productService.getProductById(id).getUser().getEmail())) {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("images", product.getImagesPathList());
            model.addAttribute("user", productService.getUserByPrincipal(principal));
            return "product-edit";
        } else {
            return "redirect:/products/{id}";
        }
    }


    @PostMapping("/{id}/update")
    public String updateProduct(@RequestParam(value = "file", required = false) MultipartFile[] file,
                                @ModelAttribute @Valid ProductDto productDto,
                                @PathVariable Long id, Principal principal) throws IOException {
        productService.updateProduct(id, principal, productDto, file);
        return "redirect:/products/{id}";
    }


    @PostMapping("{product}/images/delete")
    public String deleteImagesProduct(@PathVariable Product product) throws IOException {
        productService.deleteImagesOfProduct(product);
        return "redirect:/products/{product}";
    }

}