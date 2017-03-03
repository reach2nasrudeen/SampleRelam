package lea.madebyfire.com.samplerelam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private String TAG = "REALM_SAMPLE";
    private EditText edName,edAge;
    private TextView textStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        // Create the Realm instance
        realm = Realm.getDefaultInstance();


        textStatus = (TextView) findViewById(R.id.textStatus);
        edName = (EditText) findViewById(R.id.edName);
        edAge = (EditText) findViewById(R.id.edAge);
        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicCRUD(edName.getText().toString(),Integer.parseInt(edAge.getText().toString()));
            }
        });

    }

    private void basicCRUD(final String name, final int age) {
//        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...");

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                Person person = realm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
                realm.insertOrUpdate(person);
            }
        });

//        realm.delete(Person.class);

        // Find the first person (no query conditions) and read a field
        Long count = realm.where(Person.class).count();
//        final Person person = realm.where(Person.class).findAll();

        textStatus.setText("");

        showStatus(String.valueOf(count));

        for (Person pers : realm.where(Person.class).findAll()) {
            showStatus("\n" + pers.getName() + ":" + pers.getAge());
        }

        // Update person in a transaction
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("Senior Person");
                person.setAge(99);
                showStatus(person.getName() + " got older: " + person.getAge());
            }
        });*/

        // Delete all persons
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });*/
    }

    private void showStatus(String status) {
        Log.e(TAG,status);
        textStatus.append(status);
    }
}
