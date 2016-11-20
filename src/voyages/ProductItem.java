package voyages;
import exceptions.ProductException;

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