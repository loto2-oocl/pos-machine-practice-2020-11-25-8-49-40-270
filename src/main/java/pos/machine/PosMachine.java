package pos.machine;

import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        Receipt receipt = new Receipt(barcodes);
        return buildReceiptMessage(receipt);
    }

    public String buildReceiptMessage(Receipt receipt) {
        return new StringBuilder()
            .append(generateMessageHeader())
            .append(generateMessageBody(receipt))
            .append(generateMessageFooter(receipt))
            .toString();
    }

    private String generateMessageBody(Receipt receipt) {
        StringBuilder body = new StringBuilder();
        receipt.getReceiptItemList().forEach((receiptItem -> {
            body.append(receiptItem.getItemDescription());
            body.append("\r\n");
        }));

        return body.toString();
    }

    private String generateMessageFooter(Receipt receipt) {
        return new StringBuilder("----------------------\r\n")
            .append(String.format("Total: %s (yuan)", receipt.getTotalAmount()))
            .append("\r\n**********************")
            .toString();
    }

    private String generateMessageHeader() {
        return "***<store earning no money>Receipt***\r\n";
    }
}
