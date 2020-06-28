package org.techtown.mediclock;
import android.app.Activity;
import android.os.Bundle;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomScannerActivity extends Activity {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
   // private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scanner);

     //   backPressCloseHandler = new BackPressCloseHandler(this);

        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        //barcodeScannerView.setTorchListener((DecoratedBarcodeView.TorchListener) this);//없어도 되면 쓰지 말자.
        //DecorateBarcodeView 에 만들어둔 xml 을 적용시키고 라이트를 켜고끄기위한 Torch리스너를 연결

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

  //  public void onBackPressed() {
     //   backPressCloseHandler.onBackPressed();
    }

//}
