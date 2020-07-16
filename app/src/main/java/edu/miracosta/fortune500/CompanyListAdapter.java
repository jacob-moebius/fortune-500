package edu.miracosta.fortune500;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.miracosta.fortune500.model.Company;

/**
 * Helper class to provide custom adapter for the <code>Company</code> list.
 */
public class CompanyListAdapter extends ArrayAdapter<Company> {

    private Context mContext;
    private List<Company> mCompaniesList;
    private int mResourceId;

    /**
     * Creates a new <code>CompanyListAdapter</code> given a context, resource id and list of Companies.
     *
     * @param c The context for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param companiesList The list of companies to display
     */
    public CompanyListAdapter(Context c, int rId, List<Company> companiesList) {
        super(c, rId, companiesList);
        mContext = c;
        mResourceId = rId;
        mCompaniesList = companiesList;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the Company selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        //DONE:  Code for getting the view of a list item correctly inflated.
        //DONE:  This should inflate every part of the company_list_item layout
        LinearLayout companyListLinearLayout = view.findViewById(R.id.companyListLinearLayout);
        Company selectedCompany = mCompaniesList.get(pos);
        TextView companyListNameTextView = view.findViewById(R.id.companyListNameTextView);
        companyListNameTextView.setText(selectedCompany.getName());

        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());

        TextView companyListProfitChangeTextView =
                view.findViewById(R.id.companyListProfitChangeTextView);
        double selectedCompanyProfitChange = selectedCompany.getProfitChange();
        String formattedProfitChange = currency.format(selectedCompanyProfitChange);
        companyListProfitChangeTextView.setText(formattedProfitChange);
        if (selectedCompanyProfitChange >= 0) {
            int green = view.getResources().getColor(R.color.green);
            companyListProfitChangeTextView.setTextColor(green);
        } else {
            int red = view.getResources().getColor(R.color.red);
            companyListProfitChangeTextView.setTextColor(red);
        }

        TextView companyListRankTextView =view.findViewById(R.id.companyListRankTextView);
        companyListRankTextView.setText(String.valueOf(selectedCompany.getRank()));
        ImageView companyListImageView = view.findViewById(R.id.companyListImageView);
        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(selectedCompany.getImageName());
            Drawable drawable = Drawable.createFromStream(stream, selectedCompany.getName());
            companyListImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //DONE:  Be sure to set the tag of the view to the current company (using the list and position)
        companyListLinearLayout.setTag(selectedCompany);

        return view;
    }
}
