package ee.geir.webshop.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.geir.webshop.entity.Product;
import ee.geir.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private ProductRepository productRepository;

    private final LoadingCache<Long, Product> productCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<Long, Product>() {
                        @Override
                        public Product load(Long id) {
                            System.out.println("Loading product " + id + " from DB");
                            return productRepository.findById(id).orElseThrow();
                        }
                    });

    public Product getProduct(Long id) throws ExecutionException {
        System.out.println("Getting product " + id);
        return productCache.get(id);
    }

    public void deleteFromCache(Long id) {
        productCache.invalidate(id);
    }

    public void updateCache(Long id, Product product) {
        productCache.put(id, product);
    }

}
