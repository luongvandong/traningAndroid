package com.android.project1.view.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.android.project1.R;
import com.android.project1.view.ui.adapter.GalleryAdapter;
import com.android.project1.view.ui.util.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

public class GalleryActivity extends AppBarActivity
        implements GalleryAdapter.OnCameraSelectListener, GalleryAdapter.OnImageSelectListener {

    public static final int CAM_REQUEST = 1313;
    public static final String EXTRA_MAX_IMAGE_CAN_SELECT = "EXTRA_MAX_IMAGE_CAN_SELECT";
    public static final int BACK_TO_NEWPOST = 110;
    private static final Logger LOGGER = Logger.getLogger(GalleryActivity.class);
    private List<GalleryAdapter.IndexedImage> images;
    private GalleryAdapter adapter;
    private String currentPhotoPath;

    @Override
    public void onCameraSelect() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            try {
                photoFile = createImageFile();
                // Continue only if the File was successfully created
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAM_REQUEST);
                currentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                LOGGER.error(e);
                // Error occurred while creating the File
            }
        }
        adapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            sendResultBack();
            return;
        }
        File file = new File(currentPhotoPath);
        boolean b = file.delete();
        currentPhotoPath = null;

    }

    @Override
    public void onImageSelect(int position) {
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("ConstantConditions")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        images = new ArrayList<>();
        addImage();
        GridView gridView = (GridView) findViewById(R.id.phoneImageGrid);

        String[] path = getIntent().getStringArrayExtra("image selected");
        int totalSelected = 0;
        if (path != null) {
            totalSelected = getIntent().getStringArrayExtra("image selected").length;
        }
        adapter = new GalleryAdapter(this, this, this, images,
                getIntent().getIntExtra(EXTRA_MAX_IMAGE_CAN_SELECT, 1), totalSelected);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()) {
            case (R.id.btnDone): {
                sendResultBack();
            }
            break;
            case android.R.id.home:
                setResult(BACK_TO_NEWPOST);
                GalleryActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addImage() {
        List<GalleryAdapter.IndexedImage> selectedImage = new ArrayList<>();
        List<String> listImagePaths = getAllImagesPath();
        String[] paths;

        images.add(new GalleryAdapter.IndexedImage());
        if (getIntent() != null) {
            paths = getIntent().getStringArrayExtra("image selected");
            int listImagePathsSize = listImagePaths.size();
            if (paths != null) {
                int selectedIndex = 1;
                for (int i = 0; i < listImagePathsSize; i++) {

                    GalleryAdapter.IndexedImage image = new GalleryAdapter.IndexedImage();
                    for (String path : paths) {
                        if (path.equals(listImagePaths.get(i))) {
                            image.url = path;
                            image.selectedIndex = selectedIndex;
                            selectedIndex++;
                            break;
                        }
                    }
                    image.url = listImagePaths.get(i);
                    selectedImage.add(image);
                }
                images.addAll(selectedImage);
            } else {
                for (int i = 0; i < listImagePathsSize; i++) {
                    GalleryAdapter.IndexedImage image = new GalleryAdapter.IndexedImage();
                    image.url = listImagePaths.get(i);
                    images.add(image);
                }
            }
        }
    }

    private void sendResultBack() {
        Intent intent = getIntent();
        int k = 0;
        int j = 0;
        for (GalleryAdapter.IndexedImage i : images) {
            if (i.selectedIndex > 0) {
                k++;
            }
        }
        if (k > 0 || currentPhotoPath != null) {
            String[] value = new String[currentPhotoPath != null ? k + 1 : k];

            for (GalleryAdapter.IndexedImage i : images) {
                if (i.selectedIndex > 0) {
                    value[j] = i.url + "";
                    j++;
                }
            }
            if (currentPhotoPath != null) {
                value[k] = currentPhotoPath;
            }
            intent.putExtra("data", value);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        //create folder when it not exists
        boolean b = storageDir.mkdirs();
        LOGGER.info(b ? "create folder picture" : "not created folder picture");
//      Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private List<String> getAllImagesPath() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor cursor = null;
        SortedSet<String> dirList = new TreeSet<>();
        List<String> listImagePaths = new ArrayList<>();

        String[] directories = null;
        if (uri != null) {
            cursor = getContentResolver().query(uri, projection, null, null, null);
        }
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                String tempDir = cursor.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try {
                    dirList.add(tempDir);
                } catch (Exception ignored) {
                }
            }
            while (cursor.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);
            cursor.close();
        }
        int dirListSize = dirList.size();
        for (int i = 0; i < dirListSize; i++) {
            assert directories != null;
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG") || imagePath
                            .getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")) {
                        int imgSize = Integer.parseInt(String.valueOf(imagePath.length() / 1024));
                        if (imgSize != 0) {
                            listImagePaths.add(imagePath.getAbsolutePath());
                        } else {
                            boolean b = imagePath.delete();
                            LOGGER.info(b);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return listImagePaths;
    }
}