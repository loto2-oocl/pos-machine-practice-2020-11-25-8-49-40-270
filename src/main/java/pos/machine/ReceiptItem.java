package pos.machine;

public class ReceiptItem extends ItemInfo {
    private final int count;
    private final int subTotal;

    public ReceiptItem(String barcode, String name, int price, int count) {
        super(barcode, name, price);
        this.count = count;
        this.subTotal = count * price;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public int getCount() {
        return count;
    }

    public String getItemDescription() {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)", this.getName(), this.getCount(), this.getPrice(), this.getSubTotal());
    }
}