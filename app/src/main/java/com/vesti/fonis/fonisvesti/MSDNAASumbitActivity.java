package com.vesti.fonis.fonisvesti;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vesti.fonis.fonisvesti.utils.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MSDNAASumbitActivity extends BaseActivity {
    static final int REQUEST_TAKE_PHOTO = 100;
    private static final int REQUEST_SELECT_PHOTO = 101;

    // Path where the take image is saved
    private String mCurrentPhotoPath;

    private ImageButton btnUploadPic, btnFromGallery, btnFromCamera;
    private RelativeLayout rlUploadButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_msdnaasumbit);

        rlUploadButtons = (RelativeLayout) findViewById(R.id.rlBottomButtons);
        // Initialize elements
        init();
    }

    private void init() {
        btnFromCamera = (ImageButton) findViewById(R.id.btnFromCamera);
        btnFromGallery = (ImageButton) findViewById(R.id.btnFromGallery);
        btnUploadPic = (ImageButton) findViewById(R.id.btnUploadPic);
        rlUploadButtons = (RelativeLayout) findViewById(R.id.rlBottomButtons);

        setOnClickListeners();
    }
    private void setOnClickListeners() {
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//                btnFromCamera.setLayoutParams(layoutParams);
//                btnFromGallery.setLayoutParams(layoutParams);
//                btnUploadPic.setLayoutParams(layoutParams);

                btnUploadPic.setClickable(false);
                btnUploadPic.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_uploadpic_pressed));
                animateButtons();
            }

            private void animateButtons() {
                int[] pos = new int[2];
                int btnUploadPicH, btnUploadPicW, camButtonH, camButtonW = 0;

                // Get button position and dimens
                btnUploadPic.getLocationOnScreen(pos);
                btnUploadPicH = btnUploadPic.getHeight();
                btnUploadPicW = btnUploadPic.getWidth();
                // Get camera button dimens
                camButtonH = btnFromCamera.getHeight();
                camButtonW = btnFromGallery.getWidth();

                // Create black rect to be animated later
                ShapeDrawable rect = new ShapeDrawable(new RectShape());
                rect.getPaint().setColor(getResources().getColor(R.color.colorPrimary));
                rect.setIntrinsicHeight(camButtonH);
                rect.setIntrinsicWidth(camButtonW);

                // Put rect into image views
                final ImageView leftRect = new ImageView(MSDNAASumbitActivity.this);
                final ImageView rightRect = new ImageView(MSDNAASumbitActivity.this);
                leftRect.setImageDrawable(rect);
                rightRect.setImageDrawable(rect);

                // Set right imageView attributes
                RelativeLayout.LayoutParams rectRightParams = new RelativeLayout.LayoutParams(camButtonW, camButtonH);
                rectRightParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rectRightParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                rectRightParams.setMargins(pos[0] + btnUploadPicW + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()), 0, 0, 0);
                rightRect.setLayoutParams(rectRightParams);
                // Set left imageView attrs
                RelativeLayout.LayoutParams rectLeftParams = new RelativeLayout.LayoutParams(camButtonW, camButtonH);
                rectLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rectLeftParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                rectLeftParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()), 0, 0, 0);
                leftRect.setLayoutParams(rectLeftParams);

                // Get relative layout and add views
                if (rlUploadButtons == null) { // assert check
                    btnFromGallery.setVisibility(View.VISIBLE);
                    btnFromCamera.setVisibility(View.VISIBLE);
                    Log.d(Util.TAG,"Relative layout is null");
                    return;
                }
                rlUploadButtons.addView(rightRect);
                rlUploadButtons.addView(leftRect);

                // Show views
                if (android.os.Build.VERSION.SDK_INT >= 14) {
                    btnFromCamera.setVisibility(View.VISIBLE);
                    btnFromGallery.setVisibility(View.VISIBLE);
                    startAnimation(rightRect,'r');
                    startAnimation(leftRect,'l');
                    Log.d(Util.TAG, "Animation started");
                } else {
                    btnFromCamera.setVisibility(View.VISIBLE);
                    btnFromGallery.setVisibility(View.VISIBLE);
                    rightRect.setVisibility(View.GONE);
                    leftRect.setVisibility(View.GONE);
                    Log.d(Util.TAG, "Animation not started");
                }


            }

            @TargetApi(14)
            private void startAnimation(final ImageView imageView, char flag) {
                if (flag == 'r'){
                    imageView.animate().
                            translationX(200).
                            setDuration(400).
                            setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            imageView.setVisibility(View.GONE);
                        }
                    }).start();
                } else {
                    imageView.animate().
                            translationX(-200).
                            setDuration(400).
                            setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            imageView.setVisibility(View.GONE);
                        }
                    }).start();
                }
            }
        });
        btnFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        btnFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchLoadPictureIntent();
            }
        });
    }

    private void dispatchLoadPictureIntent() {
        Intent loadPicIntent = new Intent(Intent.ACTION_PICK);
        loadPicIntent.setType("image/*");
        startActivityForResult(loadPicIntent, REQUEST_SELECT_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(MSDNAASumbitActivity.this, "Greška! Probajte ponovo ili izaberite drugu opciju :(", Toast.LENGTH_SHORT).show();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Dokument_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        switch (requestCode) {
            case REQUEST_SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    // The user choose a picture.
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    btnFromGallery.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_gallery_pressed));
                    btnFromCamera.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_camera));
                    btnUploadPic.setClickable(false);
                }
                break;
            case REQUEST_TAKE_PHOTO:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    // The user took a picture.
                    btnFromCamera.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_camera_pressed));
                    btnFromGallery.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_gallery));
                    btnUploadPic.setClickable(false);
                }
                break;
            default: break;
        }
    }

}
