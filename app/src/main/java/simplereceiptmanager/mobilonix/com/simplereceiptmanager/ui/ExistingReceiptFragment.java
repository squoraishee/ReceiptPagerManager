package simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.SRMApplication;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.SimpleReceiptManagerActivity;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.model.Receipt;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;

public class ExistingReceiptFragment extends Fragment {

    public static final String RECEIPT = "RECEIPT";
    public static final String POSITION = "POSITION";

    Button deleteReceiptButton;
    Button receiptDetailsButton;
    ImageView receiptImage;

    ExistingReceiptFragment fragmentContext = this;

    int position;

    public static ExistingReceiptFragment newInstance(Receipt receipt, int position) {
        GlobalUtil.toast("Got fragment pager!");
        ExistingReceiptFragment fragment = new ExistingReceiptFragment();
        Bundle arguments = new Bundle();

        arguments.putSerializable(RECEIPT, receipt);
        arguments.putInt(POSITION, position);

        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup fragmentView = (ViewGroup) inflater.inflate(R.layout.fragment_existing_receipt, parent, false);
        Receipt receipt = (Receipt) getArguments().getSerializable(RECEIPT);
        position = (Integer)getArguments().getInt(POSITION);

        initUI(fragmentView);

        try {
            receiptImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                    receipt.getReceiptImageUri()));
        } catch (IOException e) {
            GlobalUtil.toast("Could not fetch receipt image!");
        }

        receiptDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        deleteReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteReceiptConfirmationDialog();
            }
        });

        /* On deleting the receipt, an animation occurs which crumbles up the receipt and drops is into the trash */
        return fragmentView;
    }

    public void initUI(View fragmentView) {
        deleteReceiptButton = (Button) fragmentView.findViewById(R.id.delete_receipt_button);
        receiptDetailsButton = (Button) fragmentView.findViewById(R.id.receipt_details_button);
        receiptImage = (ImageView) fragmentView.findViewById(R.id.receipt_image);
    }


    public void showDeleteReceiptConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you'd like to remove this receipt?");
        builder.setTitle("Delete Receipt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((SimpleReceiptManagerActivity)getActivity()).removeItem(position);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();

        GlobalUtil.toast("Pausing fragment: " + position);
    }

    @Override
    public void onDestroy() {

        SRMApplication.getRefWatcher().watch(this);

        super.onDestroy();
    }

    /**
     * Things to get familiar with:
     *
     * KeyedWeakReferecne.
     *
     * So leak canary tries to GC the reference, and if that doesn't work it puts the reference in the
     * .hprop file.  so is leak canary basicallly a wrapper fo hrpof?.  YOu can run hrpof form the command line
     */
}

