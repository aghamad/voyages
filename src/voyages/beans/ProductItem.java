package voyages.beans;
import exceptions.ProductException;
import voyages.deprectaed.Product;

/**
 * 
 * @author 201419001
 *
 * DTO de Product a partir du fichier XML
 * DEPRECTAED
 *
 */
public class ProductItem {
    public int code;
    public int quantity;
    
    public Product getProduct() throws ProductException {
        return Product.getProduct(this.code);
    }
    public ProductItem() {
        super();
    }
}