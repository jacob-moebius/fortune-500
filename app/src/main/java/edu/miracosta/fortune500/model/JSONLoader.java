package edu.miracosta.fortune500.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class loads Company data from a formatted JSON (JavaScript Object Notation) file.
 * Populates data model (Company) with data.
 */

public class JSONLoader {

    public static final String TAG = JSONLoader.class.getSimpleName();

    /**
     * Loads JSON data from a file in the assets directory.
     * @param context The activity from which the data is loaded.
     * @throws IOException If there is an error reading from the JSON file.
     */
    public static List<Company> loadJSONFromAsset(Context context) throws IOException {
        List<Company> allCompanies = new ArrayList<>();
        String json;
            InputStream is = context.getAssets().open("Fortune500.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        try {
            JSONObject jsonRootObject = new JSONObject(json);
            //DONE: Complete the name of the root object in the JSON file
            JSONArray allCompaniesJSON = jsonRootObject.getJSONArray("Companies");

            // DONE: Loop through the root object array and
            // DONE: Extract each single Company from the JSON file.
            JSONObject company;
            int count = allCompaniesJSON.length();
            String name, imageName;
            int rank, employees;
            double profits, profitChange, marketValue;
            for (int i = 0; i < count; i++) {
                // DONE: Create an object to represent each company, then
                // DONE: add each Company to the list.
                company = allCompaniesJSON.getJSONObject(i);
                name = company.getString("Name");
                rank = company.getInt("Rank");
                employees = company.getInt("Employees");
                profits = 1000000.0 * company.getDouble("Profits");
                profitChange = 1000000.0 * company.getDouble("ProfitChange");
                marketValue = 1000000.0 * company.getDouble("MarketValue");
                imageName = company.getString("ImageName");
                allCompanies.add(
                        new Company(
                                name,
                                rank,
                                employees,
                                profits,
                                profitChange,
                                marketValue,
                                imageName));
            }

        }
        catch (JSONException e)
        {
            Log.e(TAG, e.getMessage());
        }

        return allCompanies;
    }
}
