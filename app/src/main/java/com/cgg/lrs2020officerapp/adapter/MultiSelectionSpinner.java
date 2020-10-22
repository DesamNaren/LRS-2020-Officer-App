package com.cgg.lrs2020officerapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.ui.L1ScrutinyChecklistActivity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MultiSelectionSpinner extends AppCompatSpinner implements OnMultiChoiceClickListener {

    private OnMultipleItemsSelectedListener listener;
    private String flag;

    String[] _items = null;
    boolean[] mSelection = null;
    boolean[] mSelectionAtStart = null;
    String _itemsAtStart = null;
    ArrayAdapter<String> simple_adapter;

    public interface OnMultipleItemsSelectedListener {
        //        void selectedIndices(List<Integer> indices);
        void selectedStrings(List<String> selectedlist, List<String> notSelectedlist, String flag);
    }

    public MultiSelectionSpinner(Context context) {
        super(context);

        simple_adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
        super.setAdapter(simple_adapter);
    }

    public void setListener(OnMultipleItemsSelectedListener listener, String flag) {
        this.listener = listener;
        this.flag = flag;
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        if (mSelection != null && which < mSelection.length) {

            if (((AlertDialog) dialog).getListView().isItemChecked(0)) {
                for (int i = 1; i < _items.length; ++i) {
                    ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                    mSelection[i] = false;
                }
            } else {
                mSelection[which] = isChecked;
            }
            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());

        } else {
            Log.d("TAG", "Empty Data");
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        if (_items != null && _items.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Please select");
            builder.setMultiChoiceItems(_items, mSelection, this);
            _itemsAtStart = buildSelectedItemString();

            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
//                    listener.selectedIndices(getSelectedIndices());
                    listener.selectedStrings(getSelectedStrings(), getNotSelectedStrings(), flag);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    simple_adapter.clear();
                    simple_adapter.add(_itemsAtStart);
                    System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
                }
            });
            AlertDialog alert11 = builder.create();
            alert11.show();

            Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
            buttonbackground.setBackgroundColor(getContext().getResources().getColor(R.color.red));

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttonbackground.getLayoutParams();
            params.setMargins(10, 0, 10, 0);
            buttonbackground.setLayoutParams(params);

            Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
            buttonbackground1.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));

            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) buttonbackground1.getLayoutParams();
            params1.setMargins(10, 0, 10, 0);
            buttonbackground1.setLayoutParams(params1);


        } else {
            Log.d("TAG", "NO LIST");
            if (flag.equalsIgnoreCase(AppConstants.SHORTFALL))
                ((L1ScrutinyChecklistActivity) getContext()).callSnackBar(getContext().getResources().getString(R.string.select_plot_numbers_recommended_for_approval_of_lrs));
            else if (flag.equalsIgnoreCase(AppConstants.REJECT))
                ((L1ScrutinyChecklistActivity) getContext()).callSnackBar(getContext().getResources().getString(R.string.select_plot_numbers_recommended_for_shortfall_of_lrs));
        }
        return true;

    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(List<String> items) {

        if (items != null && items.size() > 0) {
            _items = items.toArray(new String[items.size()]);
            mSelection = new boolean[_items.length];
            mSelectionAtStart = new boolean[_items.length];
            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
//        simple_adapter.add(_items[0]);
            Arrays.fill(mSelection, false);
            mSelection[0] = false;
        } else {
//            if (items != null && items.size() > 0)
//                _items = new String[items.size()];
            _items = null;
            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());

            Log.d("TAG", "Request_" + "NO LIST");
        }
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();
        if (_items != null && _items.length > 0) {
            for (int i = 0; i < _items.length; ++i) {
                if (mSelection[i]) {
                    selection.add(_items[i]);
                }
            }
        }
        return selection;
    }

    public List<String> getNotSelectedStrings() {
        List<String> selection = new LinkedList<>();
        if (_items != null && _items.length > 0) {
            for (int i = 0; i < _items.length; ++i) {
                if (!mSelection[i]) {
                    selection.add(_items[i]);
                }
            }
        }
        return selection;
    }

/*    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        mSelectionAtStart[0] = true;
    }

    public void setSelection(String[] selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(List<String> selection) {
//        if (selection != null && selection.size() > 0) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
//        }
    }

    public void setSelection(int[] selectedIndices) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<Integer> getSelectedIndices() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }


    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }*/


    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        String data;
        boolean foundOne = false;
        if (_items != null && _items.length > 0) {

            for (int i = 0; i < _items.length; ++i) {

                if (mSelection[i]) {
                    if (foundOne) {
                        sb.append(", ");
                    }
                    foundOne = true;

                    sb.append(_items[i]);
                }
            }
            if (sb.toString().isEmpty())
                data = AppConstants.SELECT;
            else {
                data = sb.toString();
            }
        } else
            data = AppConstants.SELECT;
        return data;
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        String data;
        boolean foundOne = false;
        if (_items != null && _items.length > 0) {
            for (int i = 0; i < _items.length; ++i) {
                if (mSelection[i]) {
                    if (foundOne) {
                        sb.append(", ");
                    }
                    foundOne = true;
                    sb.append(_items[i]);
                }
            }
            if (sb.toString().isEmpty())
                data = "Select";
            else
                data = sb.toString();
        } else
            data = "Select";
        return data;
    }

}
