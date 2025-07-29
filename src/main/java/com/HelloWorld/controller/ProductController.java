
package com.HelloWorld.controller;

import com.HelloWorld.model.PurchaseHistory;
import com.HelloWorld.service.ProductService;
import com.HelloWorld.service.PurchaseHistoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.HelloWorld.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String index(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product product) {
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products/detail/{id}")
    public String showProductDetail(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> results = productService.searchProducts(keyword);
        model.addAttribute("products", results);
        return "product-ec-list";
    }

    @GetMapping("/products/ec")
    public String showEcProductList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-ec-list";
    }

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @PostMapping("/products/purchase")
    public String purchaseProduct(@RequestParam("productId") Integer productId,
                                  @RequestParam("quantity") Integer quantity,
                                  HttpSession session,
                                  Model model) {

        Integer userId = (Integer) session.getAttribute("userId");
        Product product = productService.getProductById(productId);

        purchaseHistoryService.savePurchase(userId, productId, quantity);

        product.setQuantity(product.getQuantity() - quantity);
        productService.updateProduct(product);

        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);

        return "purchase-complete";
    }

    @GetMapping("/purchase-history")
    public String showPurchaseHistory(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "desc") String sort,
            HttpSession session,
            Model model) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // 総件数とページ数の計算
        int totalCount = purchaseHistoryService.countByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        // ページングされた履歴取得
        List<PurchaseHistory> historyList = purchaseHistoryService.getHistoryByUserIdPagedSorted(userId, page, size, sort);

        // 日付フォーマットと表示用リスト作成
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Map<String, Object>> displayList = new ArrayList<>();
        for (PurchaseHistory history : historyList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", history.getId());
            map.put("formattedDate", history.getPurchaseDate() != null ? history.getPurchaseDate().format(formatter) : "未登録");
            displayList.add(map);
        }

        model.addAttribute("historyList", displayList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sort", sort);

        return "purchase-history";
    }

    @GetMapping("/purchase-history/detail/{id}")
    public String showPurchaseDetail(@PathVariable("id") Long id, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        PurchaseHistory history = purchaseHistoryService.getById(id);
        if (history == null || !history.getUserId().equals(userId)) {
            model.addAttribute("error", "履歴が見つかりません。");
            return "error";
        }

        Product product = productService.getProductById(history.getProductId());
        if (product == null) {
            model.addAttribute("error", "商品情報が見つかりません。");
            return "error";
        }

        int totalPrice = product.getSalePrice() * history.getQuantity();
        model.addAttribute("history", history);
        model.addAttribute("product", product);
        model.addAttribute("totalPrice", totalPrice);
        return "purchase-history-detail";
    }
}
