package com.example.restro.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restro.MainActivity;
import com.example.restro.R;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_db.my_entity.ProductTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewProductActivity extends AppCompatActivity {
    private EditText edtProductName,edtProductPrize;
    private String productName,productPrize,spinnerCode;
    private FloatingActionButton btnCapture;
    private Button btnAddNewProduct;
    private Spinner productCodeSpinner;
    private int mysize;
    private String myImgUri;
    private String pictureFilePath;
    private ImageView imageView;
    private ArrayList<String> productCode=new ArrayList<>();
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add New Product");

        setContentView(R.layout.add_new_product);
        setUp();
        requestMultiplePermissions();
        getMenu();

        productCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productName=edtProductName.getText().toString().trim();
                spinnerCode=parent.getItemAtPosition(position).toString();
                getProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productName=edtProductName.getText().toString().trim();
                spinnerCode=productCodeSpinner.getSelectedItem().toString();
                getProducts();
            }
        });


        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mysize>0){
                    Toast.makeText(getApplicationContext(),"Product already exit",Toast.LENGTH_SHORT).show();
                }
                if (mysize==0){
                    saveTables();
                }

            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


//        boolean isFocous=false;
    }
    private void setUp(){
        edtProductName=findViewById(R.id.edt_product_name);
        edtProductPrize=findViewById(R.id.edt_product_price);
        btnAddNewProduct=findViewById(R.id.btn_add_product);
        productCodeSpinner=findViewById(R.id.spinner_product_code);
        btnCapture=findViewById(R.id.btn_capture);
        imageView=findViewById(R.id.menu_img);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(AddNewProductActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }

                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    saveImage(bitmap);
                    Cursor cursor = getContentResolver().query(contentURI,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imagePath  = cursor.getString(columnIndex);
                    cursor.close();
                    File imgFile=new File(pictureFilePath);
                    myImgUri= String.valueOf(Uri.fromFile(imgFile));
                    imageView.setImageURI(Uri.parse(myImgUri));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            saveImage(thumbnail);
            File imgFile=new File(pictureFilePath);
            myImgUri= String.valueOf(Uri.fromFile(imgFile));
            imageView.setImageURI(Uri.parse(myImgUri));

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddNewProductActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void getProducts() {
        productName=edtProductName.getText().toString().trim();
        spinnerCode=productCodeSpinner.getSelectedItem().toString();
        class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {

            @Override
            protected List<ProductTable> doInBackground(Void... voids) {
                List<ProductTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getDuplicateProduct(productName,spinnerCode);

                return stores;
            }

            @Override
            protected void onPostExecute(List<ProductTable> mystore) {
                super.onPostExecute(mystore);
                mysize=mystore.size();
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }

    private void saveTables(){
        productName=edtProductName.getText().toString().trim();
        productPrize=edtProductPrize.getText().toString().trim();
        spinnerCode=productCodeSpinner.getSelectedItem().toString();
        if (productName.isEmpty()) {
            edtProductName.setError("Product name required");
            edtProductName.requestFocus();
            return;
        }
        if (productPrize.isEmpty()) {
            edtProductPrize.setError("Product name required");
            edtProductPrize.requestFocus();
            return;
        }


        class SaveClass extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                ProductTable tables = new ProductTable();
                tables.setProduct_name(productName);
                tables.setProduct_code(spinnerCode);
                tables.setPrize(productPrize);
                tables.setImage_uri(myImgUri);


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .insert(tables);
               return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
                getProducts();

            }
        }
        SaveClass st = new SaveClass();
        st.execute();
    }
    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());
            pictureFilePath=f.getAbsolutePath();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }



                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    private void getMenu(){
        class GetTasks extends AsyncTask<Void, Void, ArrayList<String>> {

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                List<MyMenuTable> stores = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .myMenuDao()
                        .getAll();
                for (int i=0;i<stores.size();i++){
                    productCode.add(stores.get(i).getCategory_name());
                }
                return productCode;
            }

            @Override
            protected void onPostExecute(ArrayList<String> mystore) {
                super.onPostExecute(mystore);
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(AddNewProductActivity.this,
                        android.R.layout.simple_list_item_1, mystore);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                productCodeSpinner.setAdapter(adp1);
            }

        }
        GetTasks gt=new GetTasks();
        gt.execute();
    }

}
