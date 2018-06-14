package com.example.lwh.project_school.Activity.Notice.NoticeDetail;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.ResponseBody;
import com.example.lwh.project_school.NetWork.RetroService;
import com.example.lwh.project_school.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeDetailActivity extends AppCompatActivity {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    DownloadManager dm;
    String fileLink;
    boolean haveFile = false, alreadyDownload = false;
    String baseURL = "http://flow.cafe24app.com/";
    Bitmap bitmap;
    RetroService retroService;
    ImageView imgView;
    TextView tvBoardIdx, tvBoardContent, tvBoardCreDate,
            tvBoardMoDate, tvBoardWriter, tvBoardDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);          //Instantiation Section
        Intent intent = getIntent();                                                //End of Instantiation

        tvBoardIdx = findViewById(R.id.tvBoardIdx);                                 //Binding Section
        tvBoardContent = findViewById(R.id.tvBoardContent);
        tvBoardCreDate = findViewById(R.id.tvBoardCreDate);
        tvBoardMoDate = findViewById(R.id.tvBoardMoDate);
        tvBoardWriter = findViewById(R.id.tvBoardWriter);
        tvBoardDownload = findViewById(R.id.tvBoardFile);
        Button btnDownload = findViewById(R.id.btnDownload);
        imgView = findViewById(R.id.imgView01);                                     //End of Binding

        btnDownload.setOnClickListener(btnDownloadClick);                           //Initializing Section
        retroService = new RetroService(getApplicationContext(), true);
        doNetwork(intent.getStringExtra("idx"));                              //End of Initializing
    }

    Button.OnClickListener btnDownloadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (alreadyDownload) {
                Toast.makeText(NoticeDetailActivity.this, "이미 다운로드한 파일입니다!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (haveFile) {
                Toast.makeText(NoticeDetailActivity.this, "다운로드중 ...", Toast.LENGTH_SHORT).show();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(baseURL + fileLink));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                dm.enqueue(request);
                Toast.makeText(NoticeDetailActivity.this, "다운로드 되었습니다.", Toast.LENGTH_SHORT).show();

                alreadyDownload = true;
            } else {
                Toast.makeText(NoticeDetailActivity.this, "다운로드할 파일이 없습니다!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void getImage() {
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseURL + fileLink);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException ex) {
                    Toast.makeText(NoticeDetailActivity.this, "이미지를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mThread.start();
        try {
            mThread.join();
            imgView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            Toast.makeText(NoticeDetailActivity.this, "Unfortunately, Thread cause the exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void doNetwork(String idx) {
        retroService.getNoticeService().noticeDetail(idx).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() == null) {
                    Toast.makeText(NoticeDetailActivity.this, "데이터를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvBoardIdx.setText(String.format("공지 번호\n%s", String.valueOf(response.body().getData().getIdx())));
                tvBoardCreDate.setText(String.format("작성 날짜\n%s", format.format(response.body().getData().getWrite_date())));
                tvBoardMoDate.setText(String.format("수정 날짜\n%s", format.format(response.body().getData().getModify_date())));
                tvBoardWriter.setText(String.format("작성자\n%s", response.body().getData().getWriter().replace("@dgsw.hs.kr", "")));
                tvBoardContent.setText(response.body().getData().getContent());
                if (response.body().getData().getNotice_files().length > 0) {
                    int maxIdx = response.body().getData().getNotice_files().length;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < maxIdx; i++) {
                        stringBuilder.append(response.body().getData().getNotice_files()[i].getUpload_name());
                        fileLink = response.body().getData().getNotice_files()[i].getUpload_dir();
                    }
                    String temp = String.valueOf(stringBuilder).toUpperCase();
                    if (temp.contains("JPG") || temp.contains("JPEG") || temp.contains("PNG")) {
                        getImage();
                    }
                    tvBoardDownload.setText(stringBuilder);
                    haveFile = true;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(NoticeDetailActivity.this, "서버와 통신 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
