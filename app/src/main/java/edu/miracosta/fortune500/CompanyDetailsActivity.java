package edu.miracosta.fortune500;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class CompanyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        ImageView companyImageView = findViewById(R.id.companyDetailsImageView);
        TextView nameTextView = findViewById(R.id.companyDetailsNameTextView);
        RatingBar rankRatingBar = findViewById(R.id.companyDetailsRankRatingBar);
        TextView employeesTextView = findViewById(R.id.companyDetailsEmployeesTextView);
        TextView profitsTextView = findViewById(R.id.companyDetailsProfitsTextView);
        TextView marketValueTextView = findViewById(R.id.companyDetailsMarketValueTextView);

        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
        // DONE:  Use the Intent object sent from MainActivity to populate the Views on
        // DONE:  this layout, including the TextViews, RatingBar and ImageView with the Company details.
        Intent intent = getIntent();
        String name, imageName;
        int rank, employees;
        double profits, profitChange, marketValue;
        name = intent.getStringExtra("Name");
        nameTextView.setText(name);
        rank = intent.getIntExtra("Rank", 0);
        // DONE:  Ensure the rankRatingBar has both its rating AND number of stars
        // DONE:  set to the rank of the company.
        rankRatingBar.setNumStars(rank);
        rankRatingBar.setRating((float) rank);
        employees = intent.getIntExtra("Employees", 0);
        employeesTextView.setText(String.valueOf(employees));
        profits = intent.getDoubleExtra("Profits", 0);
        profitsTextView.setText(currency.format(profits));
        marketValue = intent.getDoubleExtra("MarketValue", 0);
        marketValueTextView.setText(currency.format(marketValue));
        imageName = intent.getStringExtra("ImageName");
        AssetManager am = this.getAssets();
        try {
            InputStream stream = am.open(imageName);
            Drawable drawable = Drawable.createFromStream(stream, name);
            companyImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
