package ecommercestore.ecommercebackend.model.dao;

import ecommercestore.ecommercebackend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
