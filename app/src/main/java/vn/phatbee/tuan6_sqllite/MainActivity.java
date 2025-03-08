package vn.phatbee.tuan6_sqllite;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
    EditText editName;
    Button btnThem;
    Button btnHuy;
    Button btnEdit;
    Toolbar toolbar;

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

        // Thiết lập Toolbar làm ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        anhXa();
        arrayList = new ArrayList<>();
        notesAdapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(notesAdapter);
        // Gọi hàm Database
        initDatabaseSQLite();
        // Tạo Database
        // createDatabaseSQLite();
        databaseSQLite();

    }

    public void DialogCapNhatNotes(String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_notes);

        // Ánh xạ trong Dialog
        editName = dialog.findViewById(R.id.editName);
        btnEdit = dialog.findViewById(R.id.btnEdit);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        editName.setText(name);

        // Bắt sự kiện cho nút cập nhật
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                if (name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else{
                    databaseHandler.queryData("UPDATE Notes SET NameNotes = '"+ name +"' WHERE Id = '"+ id +"'");
                    Toast.makeText(MainActivity.this, "Đã cập nhật Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite(); // Load lại dữ liệu
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_notes);

        // Ánh xạ trong Dialog
        editName = dialog.findViewById(R.id.editName);
        btnThem = dialog.findViewById(R.id.btnAdd);
        btnHuy = dialog.findViewById(R.id.btnHuy);

        // Bắt sự kiện cho nút thêm và huỷ
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                if (name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else{
                    databaseHandler.queryData("INSERT INTO Notes VALUES(null, '"+ name +"')");
                    Toast.makeText(MainActivity.this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite(); // Load lại dữ liệu
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // Gọi Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện cho menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhXa(){
        listView = findViewById(R.id.listview1);
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