package voyages;


import java.util.ArrayList;

public class Caddy {
    
    private ArrayList<CaddyItem> items = new ArrayList<CaddyItem>();
    
    public Caddy() {
        
    }
    public void add(ProductModel p) {
        for(CaddyItem item : this.items){
            if(item.getProduct().ProductId == p.ProductId){
                item.increaseQuantity();
                return;
            }
        }
        
        this.items.add(new CaddyItem(p));
    }
    
    public ArrayList<CaddyItem> getItems() {
        return items;
    }
}
