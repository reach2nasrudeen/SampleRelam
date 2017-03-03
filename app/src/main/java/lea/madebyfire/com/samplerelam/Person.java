package lea.madebyfire.com.samplerelam;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nasrudeen on 02/03/17.
 */

public class Person extends RealmObject {
    private String name;
    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
