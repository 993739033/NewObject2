package com.example.mnkj.newobject.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.View.SelectDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    ArrayAdapter adapter;
    @Bind(R.id.sp_sheng)
    Spinner sp_sheng;
    @Bind(R.id.sp_shi)
    Spinner sp_shi;
    @Bind(R.id.sp_qu)
    Spinner sp_qu;
    @Bind(R.id.sp_xukezheng)
    Spinner sp_xukezheng;
    @Bind(R.id.sp_xukezheng2)
    Spinner sp_xukezheng2;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.civ_tx_select)
    CircleImageView civ_tx_select;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private Uri imageUri;


    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y)的正方形。
    private static int output_X = 300;
    private static int output_Y = 300;

    SelectDialog selectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        spinnerData();
        imageUri = Uri.parse(getExternalCacheDir() + IMAGE_FILE_NAME);//设置截取文件名
        initView();
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        civ_tx_select.setOnClickListener(this);
    }

    private void initView() {
        selectDialog = new SelectDialog(RegisterActivity.this, R.style.MyDialog);
        selectDialog.setCallBack(new SelectDialog.CallBack() {
            @Override
            public void pz() {
                choseHeadImageFromCameraCapture();
                selectDialog.cancel();
            }

            @Override
            public void xc() {
                choseHeadImageFromGallery();
                selectDialog.cancel();
            }

            @Override
            public void cancel() {
                selectDialog.cancel();
            }
        });


    }

    private void spinnerData() {
        List<String> ShengShiQuList = new ArrayList<>();
        ShengShiQuList.add("上海");
        ShengShiQuList.add("北京");
        ShengShiQuList.add("深圳");
        ShengShiQuList.add("广州");
        ShengShiQuList.add("浙江");
        ShengShiQuList.add("杭州");
        // 初始化下拉列表加载数据适配器
        adapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_item, ShengShiQuList);
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_sheng.setAdapter(adapter);
        sp_shi.setAdapter(adapter);
        sp_qu.setAdapter(adapter);
        sp_xukezheng.setAdapter(adapter);
        sp_xukezheng2.setAdapter(adapter);

        // 添加事件Spinner事件监听，当点击下拉列表中的某一选项之后触发该事件
        sp_sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_qu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_xukezheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_xukezheng2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.civ_tx_select:
                selectDialog.show();
                break;
        }
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(getExternalCacheDir(), IMAGE_FILE_NAME)));
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }


    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");//选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        //如果你想在Activity中得到新打开Activity关闭后返回的数据，
        //你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //取消
        if (resultCode == RESULT_CANCELED && data == null) {
            return;
        }
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                File tempFile = new File(getExternalCacheDir(),
                        IMAGE_FILE_NAME);
                try {
                    cropRawPhoto(Uri.fromFile(tempFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CODE_GALLERY_REQUEST://如果是来自本地的
                try {
                    cropRawPhoto(data.getData());//直接裁剪图片
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case CODE_RESULT_REQUEST:
                if (data != null) {
                    setImageToHeadView(data);//设置图片框
                }
                break;
        }
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
        civ_tx_select.setImageBitmap(bitmap);
    }


    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) throws IOException {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);

        //设置不通过intent返回bitmap
        intent.putExtra("return-data", true);
//
//        //设置输出文件地址名称
//        intent.putExtra("output", imageUri);
        //是否人脸识别
        intent.putExtra("noFaceDetection", true);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
}
