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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UpdateUser;
import com.gj.weidumovie.bean.UserInfo;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.QueryUserInfoPresenter;
import com.gj.weidumovie.presenter.UpdateHeadPresenter;
import com.gj.weidumovie.presenter.UpdateUserPresenter;
import com.gj.weidumovie.util.DateUtils;
import com.gj.weidumovie.util.updatemyhead.Constant;
import com.gj.weidumovie.util.updatemyhead.GetRealPath;
import com.gj.weidumovie.util.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

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
    private QueryUserInfoPresenter queryUserInfoPresenter;
    private int userId;
    private String sessionId;
    private UserInfo userInfo;
    private UpdateUserPresenter updateUserPresenter;
    private String getNickName;
    private int getSex;
    private String getEmail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initView() {
        sp = getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");

        updateHeadPresenter = new UpdateHeadPresenter(new updateHeadCall());//修改头像
        queryUserInfoPresenter = new QueryUserInfoPresenter(new queryCall());//根据id查询用户
        queryUserInfoPresenter.reqeust(userId,sessionId);
        updateUserPresenter = new UpdateUserPresenter(new updateUserCall());//修改用户信息
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override//再次查询
    protected void onResume() {
        queryUserInfoPresenter.reqeust(userId,sessionId);
        super.onResume();

    }

    @OnClick({R.id.my_info_head, R.id.my_info_username, R.id.my_info_sex, R.id.my_info_birth_date, R.id.my_info_telephone, R.id.my_info_email, R.id.my_info_reset_pwd, R.id.my_info_exit_login, R.id.my_info_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_info_head://点击我的头像 弹框修改头像 从相机或相册
                if(userId==0){//判断是否登录
                    UIUtils.showToastSafe("请登录");
                    return;
                    /*pleaseLogin();*/
                }else {
                    CharSequence[] items = {"相机", "相册"};
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("请选择图片来源")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == SELECT_CAMER) {//相册
                                        if (ContextCompat.checkSelfPermission(MyMassageActivity.this,
                                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {//申请权限
                                            ActivityCompat.requestPermissions(MyMassageActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
                                            return;
                                        }
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.addCategory("android.intent.category.DEFAULT");
                                        startActivityForResult(intent, SELECT_CAMER);
                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, SELECT_PICTURE);
                                    }
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    dialog.show();
                }
                break;
            case R.id.my_info_username://点击修改备注
                if(userId==0){//判断是否登录
                    /*pleaseLogin();*/
                    UIUtils.showToastSafe("请登录");
                    return;
                }
                final EditText editText = new EditText(this);
                AlertDialog mUpdateUserName = new AlertDialog.Builder(this)
                        .setTitle("请输入昵称")
                        .setView(editText)//设置输入框
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String trim = editText.getText().toString().trim();
                                myInfoUsername.setText(trim);
                                updateUserPresenter.reqeust(userId,sessionId,trim,getSex,getEmail);
                            }
                        }).setNegativeButton("取消",null).create();
                mUpdateUserName.show();
                break;
            case R.id.my_info_sex:
                break;
            case R.id.my_info_birth_date:
                break;
            case R.id.my_info_telephone:
                break;
            case R.id.my_info_email:
                if(userId==0){//判断是否登录
                    /*pleaseLogin();*/
                    UIUtils.showToastSafe("请登录");
                    return;
                }
                final EditText editEmail = new EditText(this);
                AlertDialog mUpdateEmail = new AlertDialog.Builder(this)
                        .setTitle("修改邮箱")
                        .setView(editEmail)//设置输入框
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String trim = editEmail.getText().toString().trim();
                                boolean email = isEmail(trim+"");
                                if(email){
                                    myInfoEmail.setText(trim);
                                    updateUserPresenter.reqeust(userId,sessionId,getNickName,getSex,trim);
                                }else{
                                    UIUtils.showToastSafe("请输入正确的邮箱");
                                    return;
                                }
                            }
                        }).setNegativeButton("取消",null).create();
                mUpdateEmail.show();
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

    //邮箱
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    private void pleaseLogin() {
        AlertDialog mPlaseLogin = new AlertDialog.Builder(this)
                .setTitle("请选择是否登录")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyMassageActivity.this,LoginActivity.class));
                    }
                }).setNegativeButton("取消",null).create();
        mPlaseLogin.show();
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
            updateHeadPresenter.reqeust(userId, seesionId, file);//"http://mobile.bwstudent.com/images/movie/head_pic/2019-01-24/20190124132033.jpg"
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
                    updateHeadPresenter.reqeust(userId, sessionId, file);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            myInfoHead.setImageURI(uri);
        } else {
            Toast.makeText(MyMassageActivity.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //修改用户头像
    class updateHeadCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            UIUtils.showToastSafe("请求成功"+data.getMessage());
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("修改头像请求失败"+e.getMessage());
        }
    }

    //根据ID获取用户信息
    private class queryCall implements DataCall<Result<UserInfo>> {
        @Override
        public void success(Result<UserInfo> data) {
            if(data.getStatus().equals("0000")){
                userInfo = data.getResult();
                getNickName = userInfo.getNickName();
                getSex = userInfo.getSex();
                getEmail = userInfo.getEmail();
                myInfoHead.setImageURI(userInfo.getHeadPic());
                myInfoUsername.setText(getNickName);
                myInfoEmail.setText(getEmail);
                if(getSex==1){
                    myInfoSex.setText("女");
                }else{
                    myInfoSex.setText("男");
                }
                try {
//                    myInfoBirthDate.setText(DateUtils.dateParse(DateUtils.DATE_PATTERN,userInfo.getBirthday()+"")+"");//DATE_PATTERN  userInfo.getBirthday()
                      myInfoBirthDate.setText(DateUtils.dateFormat(new Date(userInfo.getLastLoginTime()),DateUtils.MINUTE_PATTERN));//DATE_PATTERN  userInfo.getBirthday()
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myInfoTelephone.setText(userInfo.getPhone());
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+"根据ID获取用户信息请求失败"+e.getMessage());
        }
    }

    //修改用户信息
    private class updateUserCall implements DataCall<Result<UpdateUser>> {
        @Override
        public void success(Result<UpdateUser> data) {
            if(data.equals("0000")){
                UIUtils.showToastSafe(data.getStatus()+"修改用户信息请求成功"+data.getMessage());
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("修改用户信息请求失败"+e.getMessage());
        }
    }
}
