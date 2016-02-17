package simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.model.SettingsOption;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.BaseFragment;

public class SettingsFragmentSection extends BaseFragment {

    public static final String OPTIONS_LIST = "OPTIONS_LIST";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SettingsFragmentSection newInstance(ArrayList<SettingsOption> optionsList) {

        SettingsFragmentSection settingsFragmentSection = new SettingsFragmentSection();
        Bundle arguments = new Bundle();
        arguments.putSerializable(OPTIONS_LIST, optionsList);
        settingsFragmentSection.setArguments(arguments);
        return settingsFragmentSection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        Bundle args = getArguments();

        ArrayList<SettingsOption> optionsList = (ArrayList)args.getSerializable(OPTIONS_LIST);
        View fragmentView = inflater.inflate(R.layout.fragment_options_list, parent, false);
        ListView listView = (ListView)fragmentView.findViewById(R.id.section_option_list);
        listView.setAdapter(new SettingsOptionsListAdapter(getActivity(),
                R.layout.list_item_basic_toggle,
                optionsList));

        return fragmentView;
    }

    private static class SettingsOptionsListAdapter extends ArrayAdapter<SettingsOption> {

        ArrayList<SettingsOption> settingsOptionList;

        public SettingsOptionsListAdapter(Context context, int resource, ArrayList<SettingsOption> settingsOptionList) {
            super(context, resource, settingsOptionList);

            this.settingsOptionList = settingsOptionList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater
                        = (LayoutInflater)getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(settingsOptionList.get(position).getType().equals(SettingsOption.OptionType.CHECK)) {
                    convertView = inflater.inflate(R.layout.list_item_basic_check, parent, false);
                    CheckBox settingsCheckBox
                            = (CheckBox)convertView.findViewById(R.id.toggle_option_check);
                    settingsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            settingsOptionList.get(position).getFunction().execute(isChecked);
                        }
                    });

                } else {
                    convertView = inflater.inflate(R.layout.list_item_basic_toggle, parent, false);
                    Switch settingsSwitch
                            = (Switch)convertView.findViewById(R.id.toggle_option_switch);

                    settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            settingsOptionList.get(position).getFunction().execute(isChecked);
                        }
                    });
                }

                TextView label = (TextView)convertView.findViewById(R.id.toggle_option_text);
                label.setText(settingsOptionList.get(position).getOptionText());
            }
            return convertView;
        }
    }
}
