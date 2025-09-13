package com.example.crud.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts(){
        return this.productRepository.findAll();
    }

    public ResponseEntity<Object> newProduct(Product product) {
        Optional<Product> respuesta = productRepository.findProductByName(product.getName());

        HashMap<String,Object> datos = new HashMap<>();

        if(respuesta.isPresent()){
            datos.put("error",true);
            datos.put("message","ya existe un producto con este nombre");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        productRepository.save(product);
        datos.put("data",product);
        datos.put("message","se guardo el producto");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
//        String res = respuesta.isPresent() ? "el producto ya existe" : "el producto no existe";
//        throw new IllegalStateException(res);
    }

    public ResponseEntity<Object> updateProduct(Product product) {
        Optional<Product> respuesta = productRepository.findById(product.getId());
        HashMap<String, Object> data = new HashMap<>();

        if (respuesta.isPresent()) {
            Product productoExistente = respuesta.get();

            // Actualizar los campos (ajusta según tu clase)
            productoExistente.setName(product.getName());
            productoExistente.setPrice(product.getPrice());
            // Agrega más campos si los tienes...

            // Guardar el producto actualizado
            productRepository.save(productoExistente);

            data.put("mensaje", "Producto actualizado correctamente");
            data.put("producto", productoExistente);

            return ResponseEntity.ok(data);
        } else {
            data.put("error", "Producto no encontrado con ID: " + product.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }
    }

}
