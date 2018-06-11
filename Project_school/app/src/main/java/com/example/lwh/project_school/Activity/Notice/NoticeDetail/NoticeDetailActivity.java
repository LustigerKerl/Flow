package com.example.lwh.project_school.Activity.Notice.NoticeDetail;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lwh.ProjectSchool.R;
import com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.ResponseBody;
import com.example.lwh.project_school.NetWork.RetroService;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeDetailActivity extends AppCompatActivity {

    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    DownloadManager dm;
    String fileLink;
    boolean haveFile=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Intent intent= getIntent();
        String idx=intent.getStringExtra("idx");
        Log.d("test324234",idx);
        final TextView tvBoardIdx=findViewById(R.id.tvBoardIdx);
        final TextView tvBoardContent=findViewById(R.id.tvBoardContent);
        final TextView tvBoardCreDate=findViewById(R.id.tvBoardCreDate);
        final TextView tvBoardMoDate=findViewById(R.id.tvBoardMoDate);
        final TextView tvBoardWriter=findViewById(R.id.tvBoardWriter);
        final TextView tvBoardDownload=findViewById(R.id.tvBoardFile);
        Button btnDownload=findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveFile) {
                    dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse("http://flow.cafe24app.com/" + fileLink);
                    DownloadManager.Request request=new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    dm.enqueue(request);
                }
                else{
                    Toast.makeText(NoticeDetailActivity.this, "다운로드할 파일이 없습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RetroService retroService=new RetroService(getApplicationContext(),true);
        retroService.getNoticeService().noticeDetail(idx).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("responseTest",response.toString());
                ResponseBody responseBody=response.body();
                Log.d("responseTEst2",responseBody.toString());
                tvBoardIdx.setText(String.format("공지 번호\n%s", String.valueOf(responseBody.getData().getIdx())));
                tvBoardCreDate.setText(String.format("작성 날짜\n%s", format.format(responseBody.getData().getWrite_date())));
                tvBoardMoDate.setText(String.format("수정 날짜\n%s", format.format(responseBody.getData().getModify_date())));
                tvBoardWriter.setText(String.format("작성자\n%s", responseBody.getData().getWriter()));
                tvBoardContent.setText(String.format("내용\n%s", responseBody.getData().getContent()));
                if(responseBody.getData().getNotice_files().length>0){
                    int maxIdx=responseBody.getData().getNotice_files().length;
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<maxIdx;i++){
                        stringBuilder.append(responseBody.getData().getNotice_files()[i].getUpload_name());
                        fileLink=responseBody.getData().getNotice_files()[i].getUpload_dir();
                    }
                    tvBoardDownload.setText(stringBuilder);
                    haveFile=true;
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NoticeDetailActivity.this, "서버와 통신 오류", Toast.LENGTH_SHORT).show();
                Log.d("asgdasdg", t.getMessage());
            }
        });
    }
}
