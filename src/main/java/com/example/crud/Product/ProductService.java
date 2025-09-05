package com.example.crud.Product;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class ProductService {
    @GetMapping
    public List<Product> getProducts(){
        return List.of(
                new Product(
                        2L,
                        "televisor",
                        500f,
                        LocalDate.of(2025, Month.DECEMBER,12),
                        2
                )
        );
    }
}
