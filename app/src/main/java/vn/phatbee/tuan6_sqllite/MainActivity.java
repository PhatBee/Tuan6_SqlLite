package vn.phatbee.tuan6_sqllite;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import vn.phatbee.tuan6_sqllite.Adapter.NotesAdapter;
import vn.phatbee.tuan6_sqllite.Database.DatabaseHandler;
import vn.phatbee.tuan6_sqllite.Model.NotesModel;

public class MainActivity extends AppCompatActivity {
    // Khai báo biến toàn cục
    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();

        // Gọi hàm Database
        initDatabaseSQLite();
        // Tạo Database
        //createDatabaseSQLite();
        databaseSQLite();
    }

    private void anhXa(){
        listView = findViewById(R.id.listview1);
        arrayList = new ArrayList<>();
        notesAdapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(notesAdapter);
    }

    private void createDatabaseSQLite() {
        // Thêm dữ liệu vào bảng
        databaseHandler.queryData("INSERT INTO Notes VALUES (NULL, 'Ví dụ về SQLite 1')");
        databaseHandler.queryData("INSERT INTO Notes VALUES (NULL, 'Ví dụ về SQLite 2')");
        databaseHandler.queryData("INSERT INTO Notes VALUES (NULL, 'Ví dụ về SQLite 3')");
    }

    private void initDatabaseSQLite(){
        // Khởi tạo Database
        databaseHandler = new DatabaseHandler(this, "notes.SQLite", null, 1);

        // Tạo bảng Notes
        databaseHandler.queryData("CREATE TABLE IF NOT EXISTS Notes (Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite(){
        // Lấy dữ liệu
        Cursor cursor = databaseHandler.getData("SELECT * FROM Notes");
        while (cursor.moveToNext()){
            // Thêm dữ liệu vào ArrayList
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
            // Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
        notesAdapter.notifyDataSetChanged();
    }
}