package com.example.aninterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(MainActivity.this, "you can't increase more then this", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(MainActivity.this, "you can't decrease more then this", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantityTextView);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String displayOrder = "";
        EditText etname = (EditText) findViewById(R.id.etName);

        String name = etname.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(MainActivity.this, "please enter your name", Toast.LENGTH_LONG).show();
        } else {
            displayOrder += "hey " + name + " !";
            displayOrder += "\nyour order of " + quantity + " cup tea is successful";
            price = quantity * 5;
            CheckBox extraMilk = findViewById(R.id.extraMilk);
            CheckBox extraCookies = findViewById(R.id.extraCookies);

            if (extraCookies.isChecked() && extraMilk.isChecked()) {
                displayOrder += "\nwith extra milk and extra cookies";
                price = quantity * 8;
            } else if (extraMilk.isChecked() && !extraCookies.isChecked()) {
                displayOrder += "\nwith extra milk";
                price = quantity * 7;
            } else if (extraCookies.isChecked() && !extraMilk.isChecked()) {
                displayOrder += "\n with extra Cookies";
                price = quantity * 6;
            } else {
                displayOrder += "\nwith no extra ingradients";
            }
            displayOrder += "\nyour total price is $" + price;
            displayOrder += "\n\nthanks for this order!";

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("mailto:"));
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Successful form tea special by " + name);
            sendIntent.putExtra(Intent.EXTRA_TEXT, displayOrder);
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                // Try to invoke the intent.
                try {
                    startActivity(sendIntent);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
            //  creatOrderSummery(displayOrder);
        }
    }
}

//    private void creatOrderSummery(String displayOrder) {
//        TextView displayMessage=(TextView) findViewById( R.id.priceTextView);
//        displayMessage.setText(displayOrder);
//       // Create the text message with a string.
//
//    }
//}
