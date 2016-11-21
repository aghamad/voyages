
package voyages;

public class CaddyItem {
    private ProductModel product;

    private int quantity;

    public ProductModel getProduct() {
        return this.product;
    }

    public void setQuantity(int q) {
        if(q >= 0) {
            this.quantity = q;
        }
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void increaseQuantity() {
        this.setQuantity(this.getQuantity()
            + 1);
    }

    public void decreaseQuantity() {
        int qtity = this.getQuantity();

        if(qtity > 1) {
            this.setQuantity(qtity
                - 1);
        }
    }

    public CaddyItem(ProductModel f_product,
        int f_quantity) {
        this.product = f_product;
        this.quantity = f_quantity;
    }

    public CaddyItem(ProductModel f_product) {
        this(f_product,
            1);
    }
}