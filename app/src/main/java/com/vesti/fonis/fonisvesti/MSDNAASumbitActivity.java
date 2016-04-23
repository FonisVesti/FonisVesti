package com.vesti.fonis.fonisvesti;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vesti.fonis.fonisvesti.email.GMailSender;
import com.vesti.fonis.fonisvesti.utils.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;

public class MSDNAASumbitActivity extends BaseActivity {
    static final int REQUEST_TAKE_PHOTO = 100;
    private static final int REQUEST_SELECT_PHOTO = 101;

    // Email data
    private static final String FONIS_MSDNAA_EMAIL = "radovanr995@gmail.com";
    private static final String EMAIL_SUBJECT = "MSDNAA Nalog";
    private SenderDetails mDetails;
    // Path where the take image is saved
    private String mCurrentPhotoPath_1;
    private String mCurrentPhotoPath_2;
    // To controll what button is pressed and what pic path will be used
    private int mLoadPic = 0;


    private ImageButton btnUploadPic_1, btnUploadPic_2;
    private Button btnSendMail;
    private EditText etName, etIndexNumber, etEmailAdress;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_msdnaasumbit);

        // Initialize elements
        init();

        //The AsyncTask class must be loaded on the UI thread. This is done automatically as of JELLY_BEAN.
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        btnUploadPic_1 = (ImageButton) findViewById(R.id.btnUploadPic1);
        btnUploadPic_2 = (ImageButton) findViewById(R.id.btnUploadPic2);
        btnSendMail = (Button) findViewById(R.id.btnSend);
        etEmailAdress = (EditText) findViewById(R.id.etEmailAdress);
        etIndexNumber = (EditText) findViewById(R.id.etIndexNumber);
        etName = (EditText) findViewById(R.id.etName);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnUploadPic_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicMethodDialog();
                btnUploadPic_1.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_uploadpic_pressed));
                mLoadPic = 1;
            }
        });

        btnUploadPic_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicMethodDialog();
                btnUploadPic_2.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_uploadpic_pressed));
                mLoadPic = 2;

            }
        });

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void showChoosePicMethodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MSDNAASumbitActivity.this);
        builder.setMessage("Izaberite unos slike")
                .setTitle("Slika vaših dokumenta")
                .setPositiveButton("KAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dispatchTakePictureIntent();
                    }
                })
                .setNegativeButton("GALERIJA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dispatchLoadPictureIntent();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendMail() {
        try {
            validateInput();
        } catch (NullPointerException e) {
            Log.d(Util.TAG, e.getLocalizedMessage(), e);
            return;
        }

        mDetails = new SenderDetails();
        mDetails.senderEmail = etEmailAdress.getText().toString();
        mDetails.senderIndexNo = etIndexNumber.getText().toString();
        mDetails.senderName = etName.getText().toString();
        // TODO - show progress dialog
        mProgressDialog = ProgressDialog.show(MSDNAASumbitActivity.this, null, "Slanje u toku..", true, false);
        SendMailTask sendMailTask = new SendMailTask();
        sendMailTask.execute(mDetails);
    }

    private void validateInput() throws NullPointerException {
        String etText = etName.getText().toString().trim();
        if (etText.length() == 0 || etText.equals("") || etText == null) {
            showAlertDialog("Polje za ime i prezime je prazno!");
            throw new NullPointerException("Name is empty.");
        }

        etText = etIndexNumber.getText().toString().trim();
        if (etText.length() == 0 || etText.equals("") || etText == null) {
            showAlertDialog("Polje za indeks je prazno!");
            throw new NullPointerException("Index number is empty.");
        }

        etText = etEmailAdress.getText().toString().trim();
        if (etText.length() == 0 || etText.equals("") || etText == null) {
            showAlertDialog("Polje za imejl adresu je prazno!");
            throw new NullPointerException("Email adress is empty.");
        }

        if (mCurrentPhotoPath_1 == null || mCurrentPhotoPath_2 == null) {
            showAlertDialog("Morate ubaciti slike!");
            throw new NullPointerException("Pictures are null.");
        }
    }

    private void dispatchSendMailIntent() {

        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{FONIS_MSDNAA_EMAIL});
//            emailIntent.putExtra(android.content.Intent.EXTRA_CC,
//                    new String[]{emailCC});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mDetails.getBody());
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
//            for (String file : filePaths) {
//                File fileIn = new File(file);
//                Uri u = Uri.fromFile(fileIn);
//                uris.add(u);
//            }
        File fileIn = new File(mCurrentPhotoPath_1);
        Uri u = Uri.fromFile(fileIn);
        uris.add(u);
        fileIn = new File(mCurrentPhotoPath_2);
        u = Uri.fromFile(fileIn);
        uris.add(u);

        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        this.startActivity(Intent.createChooser(emailIntent, "Pošaljite putem..."));

    }


    private void showAlertDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MSDNAASumbitActivity.this);
        builder.setMessage(message)
                .setTitle("Greška")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlertDialogWithYesNo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MSDNAASumbitActivity.this);
        builder.setMessage("Da li želite pokušati da pošaljete podatke koristeći vaš lični imejl?")
                .setTitle("Podaci neuspešno poslati")
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dispatchSendMailIntent();
                    }
                })
                .setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        if (mLoadPic == 1)
            mCurrentPhotoPath_1 = image.getAbsolutePath();
        else if (mLoadPic == 2)
            mCurrentPhotoPath_2 = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        switch (requestCode) {
            case REQUEST_SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    // The user choose a picture.
                    // Getting the abs path to the image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    if (mLoadPic == 1) {
                        mCurrentPhotoPath_1 = cursor.getString(columnIndex);
//                        btnUploadPic_1.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_gallery_pressed));
                        mLoadPic = 0;
                    } else if (mLoadPic == 2) {
                        mCurrentPhotoPath_2 = cursor.getString(columnIndex);
                        //                        btnUploadPic_2.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_gallery_pressed));
                        mLoadPic = 0;
                    }
                    cursor.close();
                }
                break;
            case REQUEST_TAKE_PHOTO:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    // The user took a picture.
                    if (mLoadPic == 1) {
//                        btnUploadPic_1.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_gallery_pressed));
                        mLoadPic = 0;
                    } else if (mLoadPic == 2) {
                        //                        btnUploadPic_2.setImageDrawable(getResources().getDrawable(R.drawable.msdnaa_bstyle_gallery_pressed));
                        mLoadPic = 0;
                    }
                }
                break;
            default:
                break;
        }
    }

    private static class SenderDetails {
        protected String senderName;
        protected String senderIndexNo;
        protected String senderEmail;

        protected String getBody() {
            return this.senderName + ", broj indeksa: "
                    + this.senderIndexNo + ", email: " +
                    this.senderEmail;
        }
    }

    private class SendMailTask extends AsyncTask<SenderDetails, Void, Boolean> {

        @Override
        protected Boolean doInBackground(SenderDetails... params) {
            try {
                SenderDetails details = params[0];
                GMailSender sender = new GMailSender("fonisapp@gmail.com", "Radovan1995");
                String[] files = {mCurrentPhotoPath_1, mCurrentPhotoPath_2};
                sender.sendMail(EMAIL_SUBJECT,
                        details.getBody(),
                        files,
                        "fonisapp@gmail.com",
                        FONIS_MSDNAA_EMAIL);

                Log.d(Util.TAG, "Mail sent.");
            } catch (MessagingException e) {
                Log.e(Util.TAG, "sendMail: " + e.getLocalizedMessage(), e);
                return false;
            } catch (Exception e) {
                Log.e(Util.TAG, "sendMail: " + e.getMessage(), e);
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean succes) {
            if (succes) {
                mProgressDialog.dismiss();
                showSuccessAlertDialog();
                Log.e(Util.TAG, "Async task finished correctly");
            } else {
                mProgressDialog.dismiss();
                showAlertDialogWithYesNo();
                Log.e(Util.TAG, "Async task didn't finish correctly");
            }
        }
    }

    private void showSuccessAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MSDNAASumbitActivity.this);
        builder.setMessage("Podaci su uspešno poslati!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(MSDNAASumbitActivity.this, MSDNAASumbitSuccessActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
