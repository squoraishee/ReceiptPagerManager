package simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;

public class NewReceiptFragment extends Fragment {

    public static NewReceiptFragment getInstance() {
        return new NewReceiptFragment();
    }

    FloatingActionButton addNewReceipt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragmentBase = inflater.inflate(R.layout.fragment_new_receipt, parent, false);

        initUi(fragmentBase);

        addNewReceipt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(intent, 0);

                /*
                * So just to confirm an implicit intent is called using an action name and is interceipted by an
                * activity using a specific intent ilter.  An expcliti intent id called on an excpiolty actiivyt classs
                * and i guess i'ts usually within the same acitivyt?
                * */
            }
        });

        return fragmentBase;
    }

    public void initUi(View fragmentView) {
        addNewReceipt = (FloatingActionButton)fragmentView.findViewById(R.id.add_receipt_fab);
    }
}
