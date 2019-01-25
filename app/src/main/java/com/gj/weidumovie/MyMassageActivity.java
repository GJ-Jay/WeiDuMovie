package com.gj.weidumovie;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.UpdateHeadPresenter;
import com.gj.weidumovie.util.updatemyhead.Constant;
import com.gj.weidumovie.util.updatemyhead.GetRealPath;
import com.gj.weidumovie.util.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMassageActivity extends WDActivity {

    @BindView(R.id.my_info_head)
    SimpleDraweeView myInfoHead;
    @BindView(R.id.my_info_username)
    TextView myInfoUsername;
    @BindView(R.id.my_info_sex)
    TextView myInfoSex;
    @BindView(R.id.my_info_birth_date)
    TextView myInfoBirthDate;
    @BindView(R.id.my_info_telephone)
    TextView myInfoTelephone;
    @BindView(R.id.my_info_email)
    TextView myInfoEmail;
    @BindView(R.id.my_info_reset_pwd)
    ImageView myInfoResetPwd;
    @BindView(R.id.my_info_exit_login)
    Button myInfoExitLogin;
    @BindView(R.id.my_info_back)
    ImageView myInfoBack;
    private int SELECT_PICTURE = 1; // 从图库中选择图片
    private int SELECT_CAMER = 0; // 用相机拍摄照片
    private UpdateHeadPresenter updateHeadPresenter;
    private File file;
    private Bitmap bmp;
    private SharedPreferences sp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initView() {
        //修改头像
        updateHeadPresenter = new UpdateHeadPresenter(new updateHeadCall());//缺少登录状态的判断
        sp = getSharedPreferences("Config", MODE_PRIVATE);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.my_info_head, R.id.my_info_username, R.id.my_info_sex, R.id.my_info_birth_date, R.id.my_info_telephone, R.id.my_info_email, R.id.my_info_reset_pwd, R.id.my_info_exit_login, R.id.my_info_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_info_head://点击我的头像 弹框修改头像 从相机或相册
                CharSequence[] items = {"相机","相册"};
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("请选择图片来源")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==SELECT_CAMER){//相册
                                    if(ContextCompat.checkSelfPermission(MyMassageActivity.this,
                                            Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){//申请权限
                                        ActivityCompat.requestPermissions(MyMassageActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
                                        return;
                                    }
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    startActivityForResult(intent,SELECT_CAMER);
                                }else {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                                    intent.setType("image/*");
                                    startActivityForResult(intent,SELECT_PICTURE);
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
                dialog.show();
                break;
            case R.id.my_info_username://点击修改备注
                AlertDialog mUpdateUserName = new AlertDialog.Builder(this)
                        .setTitle("请输入昵称")
                        .setView(new EditText(this))//设置输入框
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("取消",null).create();
                mUpdateUserName.show();
                break;
            case R.id.my_info_sex:
                break;
            case R.id.my_info_birth_date:
                break;
            case R.id.my_info_telephone:
                break;
            case R.id.my_info_email:
                break;
            case R.id.my_info_reset_pwd:
                break;
            case R.id.my_info_exit_login:
                break;
            case R.id.my_info_back:
                finish();
                break;
        }
    }

    //点击相机相册回调


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){//如果没有图片返回就return
            Toast.makeText(MyMassageActivity.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if(requestCode==0){
            Bitmap bitmap = data.getParcelableExtra("data");
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                    null, null));
            myInfoHead.setImageURI(uri);
            String realPathFromUri = GetRealPath.getRealPathFromUri(MyMassageActivity.this, uri);
            file = new File(realPathFromUri);
            String seesionId = sp.getString("sessionId", "");
            int userId = sp.getInt("userId", 0);
            File file = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filepath = cursor.getString(column_index);
            file = new File(filepath);
            // TODO: 2019/1/24 上传头像
            updateHeadPresenter.reqeust(userId, seesionId, "http://mobile.bwstudent.com/images/movie/head_pic/2019-01-24/20190124132033.jpg");
            return;
        }
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                if (bmp != null) {
                    bmp.recycle();
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    file = new File(uri.toString());
                    String seesionId = sp.getString("seesionId", "");
                    int userId = sp.getInt("userId", 0);
                    updateHeadPresenter.reqeust(userId, seesionId, file);
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myInfoHead.setImageURI(uri);
        } else {
            Toast.makeText(MyMassageActivity.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    class updateHeadCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            UIUtils.showToastSafe("请求成功"+data.getMessage());
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("请求失败"+e.getMessage());
        }
    }
}
