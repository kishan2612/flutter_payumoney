package nowapps.app.payumoney_plugin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import java.util.Objects;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

import static android.app.Activity.RESULT_OK;

/**
 * PayumoneyPlugin
 */
public class PayumoneyPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {

    public static final String TAG = "LOGGER : ";
    private Activity activity;

    private MethodChannel channel;
    private MethodChannel.Result wholeResult;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "payumoney_plugin");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getResult")) {
            wholeResult = result;
            launchPayUMoneyFlow(call);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        activity = binding.getActivity();
        binding.addActivityResultListener(this);

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }


    private void launchPayUMoneyFlow(MethodCall call) {

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        String environment = call.argument("environment");
        String amount = call.argument("amount");
        String txnId = call.argument("txnid");
        String phone = call.argument("phone");
        String merchantKey = call.argument("merchant_key");
        String merchantId = call.argument("merchant_id");
        String productName = call.argument("productinfo");
        String surl = call.argument("surl");
        String furl = call.argument("furl");
        String firstName = call.argument("firstname");
        String email = call.argument("email");
        String udf1 = call.argument("udf1");
        String udf2 = call.argument("udf2");
        String udf3 = call.argument("udf3");
        String udf4 = call.argument("udf4");
        String udf5 = call.argument("udf5");
        String udf6 = call.argument("udf6");
        String udf7 = call.argument("udf7");
        String udf8 = call.argument("udf8");
        String udf9 = call.argument("udf9");
        String udf10 = call.argument("udf10");
        String hash = call.argument("hash");

        System.out.println(environment);

        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setIsDebug(Objects.equals(environment, "sandbox"))
                .setsUrl(surl)
                .setfUrl(furl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setKey(merchantKey)
                .setMerchantId(merchantId);

        try {
            PayUmoneySdkInitializer.PaymentParam mPaymentParams = builder.build();
            mPaymentParams.setMerchantHash(hash);
            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,activity, R.style.AppTheme_default, false);
        } catch (Exception e) {
            Log.d("pay u execption","Exception  Exception Exception Exception --------> ");

        }
    }


    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result Code is -1 send from Payumoney activity
        Log.d("Selfride", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    wholeResult.success(transactionResponse.getPayuResponse());
                } else {
                    //Failure Transaction
                    wholeResult.success(transactionResponse.getPayuResponse());
                }

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        } else {
            if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT)
                wholeResult.success(null);
        }
        return  true;
    }
}
