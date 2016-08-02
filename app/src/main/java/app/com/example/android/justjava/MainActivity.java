package app.com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText name = (EditText) findViewById(R.id.name);
        String value = name.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price;

        if (whippedCream == true && chocolate == true) {
            price = quantity * (5 + 3);
        }
        else if (whippedCream == true && chocolate == false) {
            price = quantity * (5 + 1);
        }
        else if (whippedCream == false && chocolate == true) {
            price = quantity * (5 + 2);
        }
        else {
            price = quantity * 5;
        }
        return price;
    }



    //Increment button
    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
        }
        display(quantity);
    }

    //Decrement button
    public void decrement(View view) {
        if (quantity > 0) {
            quantity -= 1;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */


    private String createOrderSummary(int price, boolean whippedCream, boolean chocolate, String name) {
        String message = "Name: " + name;
        message += "/nHas whipped cream? " + whippedCream;
        message += "\nHas chocolate? " + chocolate;
        message += "\nQuantity: " + quantity;
        message += "\nTotal: $ " + price;
        message += "\n" + getString(R.string.thank_you);
        return message;
    }
}
