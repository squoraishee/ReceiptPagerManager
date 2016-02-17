package simplereceiptmanager.mobilonix.com.simplereceiptmanager.session;

import java.util.ArrayList;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.model.Receipt;

public enum Session {

    INSTANCE;

    ArrayList<Receipt> receiptArrayList;

    /* Instance initializers */
    {
        receiptArrayList = new ArrayList();
    }

    public void addReceipt(Receipt receipt) {
        receiptArrayList.add(receipt);
    }
    public void removeReceipt(int position) {
        receiptArrayList.remove(position);
    }

    public Receipt getLastReceipt() {
        try {
            return receiptArrayList.get(receiptArrayList.size() - 1);
        } catch (Exception e) {
            return Receipt.NO_ITEM;
        }
    }

    public void removeAllReceipts() {
        receiptArrayList.clear();
    }
    public ArrayList<Receipt> getReceiptList() {
        return receiptArrayList;
    }

}
