package vocabletrainer.heinecke.aron.vocabletrainer.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vocabletrainer.heinecke.aron.vocabletrainer.R;
import vocabletrainer.heinecke.aron.vocabletrainer.activity.lib.TableListAdapter;
import vocabletrainer.heinecke.aron.vocabletrainer.fragment.ListPickerFragment;
import vocabletrainer.heinecke.aron.vocabletrainer.lib.Comparator.GenTableComparator;
import vocabletrainer.heinecke.aron.vocabletrainer.lib.Comparator.GenericComparator;
import vocabletrainer.heinecke.aron.vocabletrainer.lib.Database;
import vocabletrainer.heinecke.aron.vocabletrainer.lib.Storage.VList;

import static vocabletrainer.heinecke.aron.vocabletrainer.activity.MainActivity.PREFS_NAME;
import static vocabletrainer.heinecke.aron.vocabletrainer.lib.Database.ID_RESERVED_SKIP;

/**
 * List selector activity
 */
public class ListActivity extends FragmentActivity implements ListPickerFragment.FinishListener {

    /**
     * Set whether multi-select is enabled or not<br>
     * Boolean expected
     */
    public static final String PARAM_MULTI_SELECT = "multiselect";
    /**
     * Param key for return of selected lists<br>
     * This key contains a {@link VList} object or a {@link List} of {@link VList}
     */
    public static final String RETURN_LISTS = "selected";
    /**
     * Pass this flag as true to call this as an deletion activity
     */
    public static final String PARAM_DELETE_FLAG = "delete";
    /**
     * Optional Param key for already selected lists, available when multiselect is set<br>
     * Expects a {@link List} of {@link VList}<br>
     *     This can be null, if nothing is selected
     */
    public static final String PARAM_SELECTED = "selected";
    private static final String TAG = "ListActivity";
    private static final String P_KEY_LA_SORT = "LA_sorting";
    Database db;
    List<VList> lists;
    private boolean multiselect;
    private ListView listView;
    private TableListAdapter adapter;
    private boolean delete;
    private Button bOk;
    private int sort_type;
    private GenTableComparator compName;
    private GenTableComparator compA;
    private GenTableComparator compB;
    private GenTableComparator cComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        // handle passed params
        multiselect = intent.getBooleanExtra(PARAM_MULTI_SELECT, false);
        delete = intent.getBooleanExtra(PARAM_DELETE_FLAG, false);
        bOk = (Button) findViewById(R.id.btnOkSelect);

        Serializable val = intent.getSerializableExtra(PARAM_SELECTED);
        ArrayList<VList> preselected = new ArrayList<>();
        if(val instanceof ArrayList){
            preselected = (ArrayList<VList>) intent.getSerializableExtra(PARAM_SELECTED);
        } else if(val != null){
            Log.wtf(TAG,"Pre-selection from illegal type!");
        }

        ListPickerFragment listPickerFragment = ListPickerFragment.newInstance(multiselect,delete,preselected,this,true);

        setFragment(listPickerFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void selectionFinished(ArrayList<VList> selected) {
        Intent returnIntent = new Intent();
        if(multiselect)
            returnIntent.putExtra(RETURN_LISTS, selected);
        else
            returnIntent.putExtra(RETURN_LISTS, selected.get(0));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void cancel() {
        onBackPressed();
    }
}
