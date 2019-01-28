package com.gj.weidumovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.bean.MovieScheduleBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.BuyMovieTicketPresenter;
import com.gj.weidumovie.presenter.PayPresenter;
import com.gj.weidumovie.util.UIUtils;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/27 15:56
 * author:陈国星(陈国星)
 * function:
 */
public class MovieBuyShowActivity extends WDActivity {


    @BindView(R.id.txt_session)
    TextView txtSession;
    @BindView(R.id.ll_zuo)
    LinearLayout llZuo;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.seat_view)
    SeatTable seatView;
    @BindView(R.id.txt_jiesuan)
    TextView txtJiesuan;
    @BindView(R.id.txt_fuhao)
    TextView txtFuhao;
    @BindView(R.id.txt_choose_price)
    TextView txtChoosePrice;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.img_abandon)
    ImageView imgAbandon;
    @BindView(R.id.rl_bot)
    RelativeLayout rlBot;
    private BuyMovieTicketPresenter buyMovieTicketPresenter;
    private MovieScheduleBean movieScheduleBean;
    private double price = 0.0;
    private int count = 0;
    private int userId;
    private String sessionId;
    private View parent;
    private String orderId;
    private PopupWindow window;
    private PayPresenter payPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        parent = View.inflate(MovieBuyShowActivity.this, R.layout.activity_choose, null);
        SharedPreferences sharedPreferences = getSharedPreferences("Config", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        sessionId = sharedPreferences.getString("sessionId", "");
        final Intent intent = getIntent();
        movieScheduleBean = (MovieScheduleBean) intent.getSerializableExtra("movieScheduleBean");
        //String name = intent.getStringExtra("name");
        String movieName = intent.getStringExtra("movieName");
        txtSession.setText(movieName);
        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new MyBuy());
        payPresenter = new PayPresenter(new MyPay());

        seatView.setScreenName(movieScheduleBean.getScreeningHall());//设置屏幕名称
        seatView.setMaxSelected(2);//设置最多选中



        seatView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                rlBot.setVisibility(View.VISIBLE);
                price += movieScheduleBean.getPrice();
                Log.d("a", "unCheck: " + price);
                txtChoosePrice.setText(price + "");
                count++;
            }

            @Override
            public void unCheck(int row, int column) {
                price -= movieScheduleBean.getPrice();
                Log.d("a", "unCheck: " + price);
                txtChoosePrice.setText(price + "");
                if (price == 0.0) {
                    rlBot.setVisibility(View.GONE);
                }
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatView.setData(10, 15);

        imgConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId == 0) {
                    UIUtils.showToastSafe("请先登录");
                    Intent intent1 = new Intent(MovieBuyShowActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    final String md5 = MD5(userId +""+ movieScheduleBean.getId()+""+ count + "movie");
                    buyMovieTicketPresenter.reqeust(userId, sessionId, movieScheduleBean.getId(), count, md5);
                }
            }
        });
        imgAbandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlBot.setVisibility(View.GONE);
            }
        });
        //
    }

    @Override
    protected void destoryData() {
        buyMovieTicketPresenter.unBind();
        payPresenter.unBind();
    }
    private void showPopu(){
        View contentView = View.inflate(MovieBuyShowActivity.this, R.layout.popu_buy_layout, null);
        window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //问题：不能操作窗口内部的控件
        window.setFocusable(true);//获取焦点
        window.setTouchable(true);//
        //点击窗口外部窗口不消失
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(parent, Gravity.BOTTOM,0,0);
        getShow(contentView);

    }

    private void getShow(View contentView) {
      ImageView popu_buy_back=  contentView.findViewById(R.id.popu_buy_back);
      popu_buy_back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              window.dismiss();
          }
      });
      final RadioButton radio_buy_one=contentView.findViewById(R.id.radio_buy_one);
      final RadioButton radio_buy_two=contentView.findViewById(R.id.radio_buy_two);
      radio_buy_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b){
                  radio_buy_two.setChecked(false);
              }else {
                  radio_buy_two.setChecked(true);
              }
          }
      });
      radio_buy_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b){
                  radio_buy_one.setChecked(false);
              }else {
                  radio_buy_one.setChecked(true);
              }
          }
      });

        Button popu_buy_ok = contentView.findViewById(R.id.popu_buy_ok);
        popu_buy_ok.setText("微信支付"+price+"元");
        popu_buy_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPresenter.reqeust(userId,sessionId,1,orderId);
                window.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class MyBuy implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                 orderId = result.getOrderId();
                showPopu();


            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class MyPay implements DataCall<Result> {

        @Override
        public void success(Result result) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(MovieBuyShowActivity.this, "wxb3852e6a6b7d9516");
            msgApi.registerApp("wxb3852e6a6b7d9516");
            PayReq request = new PayReq();
            request.appId = result.getAppId();
            request.partnerId = result.getPartnerId();
            request.prepayId= result.getPrepayId();
            request.packageValue = result.getPackageValue();
            request.nonceStr= result.getNonceStr();
            request.timeStamp=result.getTimeStamp();
            request.sign= result.getSign();
            msgApi.sendReq(request);
            finish();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * MD5加密
     *
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
}
