package edu.miracosta.fortune500;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import java.io.IOException;
import java.util.List;

import edu.miracosta.fortune500.model.Company;
import edu.miracosta.fortune500.model.JSONLoader;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText profitChangeEditText;
    private EditText rankEditText;
    private Button addCompanyButton;

    private List<Company> companiesList;
    private CompanyListAdapter companiesListAdapter;
    private ListView companiesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        profitChangeEditText = findViewById(R.id.proiftChangeEditText);
        rankEditText = findViewById(R.id.rankEditText);
        addCompanyButton = findViewById(R.id.addCompanyButton);

        // DONE: Connect the ListView with the layout
        companiesListView = findViewById(R.id.companyListView);

        // DONE:  Populate companies list using the JSONLoader
        try {
            companiesList = JSONLoader.loadJSONFromAsset(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // DONE:  Create a new ListAdapter connected to the correct layout file and list
        companiesListAdapter = new CompanyListAdapter(this, R.layout.company_list_item, companiesList);

        // DONE:  Connect the ListView with the ListAdapter
        companiesListView.setAdapter(companiesListAdapter);
    }


    public void viewCompanyDetails(View view) {

        // DONE: Extract the clickedCompany from the tag of the view
        Company clickedCompany = (Company) view.getTag();

        // DONE: Use an Intent to start the CompanyDetailsActivity with the data it needs to correctly inflate its views.
        Intent intent = new Intent(this, CompanyDetailsActivity.class);
        intent.putExtra("Name", clickedCompany.getName());
        intent.putExtra("Rank", clickedCompany.getRank());
        intent.putExtra("Employees", clickedCompany.getEmployees());
        intent.putExtra("Profits", clickedCompany.getProfits());
        intent.putExtra("ProfitChange", clickedCompany.getProfitChange());
        intent.putExtra("MarketValue", clickedCompany.getMarketValue());
        intent.putExtra("ImageName", clickedCompany.getImageName());
        startActivity(intent);
    }

    public void addCompany(View view)
    {
        // DONE:  Read information from EditTexts and RatingBar,
        String name = nameEditText.getText().toString();
        double profitChange = Double.parseDouble(profitChangeEditText.getText().toString());
        int rank = Integer.parseInt(rankEditText.getText().toString());

        // DONE:  Create a new Company object then add it to the list
        Company companyToAdd = new Company(name, rank, profitChange);
        companiesList.add(companyToAdd);

        // DONE:  Make sure the list adapter is notified
        companiesListAdapter.notifyDataSetChanged();

        // DONE:  Clear all entries the user made (edit texts and rating bar)
        nameEditText.setText("");
        profitChangeEditText.setText("");
        rankEditText.setText("");
    }

}
