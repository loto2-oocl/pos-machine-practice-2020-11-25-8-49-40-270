package pos.machine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Receipt {

    private final List<ReceiptItem> receiptItemList;
    private final Integer totalAmount;

    public Receipt(List<String> barcodes) {
        this.receiptItemList = constructReceiptItemList(barcodes);
        this.totalAmount = findTotalAmount();
    }

    private Integer findTotalAmount() {
        return this.receiptItemList.stream()
            .map(ReceiptItem::getSubTotal)
            .reduce(Integer::sum).orElse(0);
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public List<ReceiptItem> getReceiptItemList() {
        return receiptItemList;
    }

    private List<ReceiptItem> constructReceiptItemList(List<String> barcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<ItemInfo> allProducts = ItemDataLoader.loadAllItemInfos();

        Map<String, Long> barcodeItemCountMap = countProductByBarcode(barcodes);
        barcodeItemCountMap.forEach((barcode, count) -> {
            ItemInfo matchedProduct = findProductByBarcode(barcode, allProducts);
            receiptItems.add(new ReceiptItem(
                barcode,
                matchedProduct.getName(),
                matchedProduct.getPrice(),
                barcodeItemCountMap.get(barcode).intValue()
            ));
        });

        return receiptItems;
    }

    private Map<String, Long> countProductByBarcode(List<String> barcodes) {
        return barcodes.stream()
            .collect(Collectors.groupingBy(barcode -> barcode, LinkedHashMap::new, Collectors.counting()));
    }

    private ItemInfo findProductByBarcode(String barcode, List<ItemInfo> allProducts) {
        return allProducts.stream()
            .filter(product -> product.getBarcode().equals(barcode))
            .findAny()
            .orElse(null);
    }
}
