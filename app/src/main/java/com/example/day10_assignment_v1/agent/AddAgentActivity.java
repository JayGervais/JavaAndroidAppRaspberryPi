package com.example.day10_assignment_v1.agent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.day10_assignment_v1.DBHelper;
import com.example.day10_assignment_v1.R;
import com.example.day10_assignment_v1.Validation.Validation;
import com.example.day10_assignment_v1.agency.Agency;
import com.example.day10_assignment_v1.agency.AgencyDB;

public class AddAgentActivity extends AppCompatActivity
{
    EditText etAgtFirstName, etAgtMiddleInitial, etAgtLastName, etAgtBusPhone, etAgtEmail,
            etAgtPosition, etPassword;
    Button btnSave, btnCancel;
    Spinner spinnerAgencies;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);

        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etAgtFirstName = findViewById(R.id.etAgtFirstName);
        etAgtMiddleInitial = findViewById(R.id.etAgtMiddleInitial);
        etAgtLastName = findViewById(R.id.etAgtLastName);
        etAgtBusPhone = findViewById(R.id.etAgtBusPhone);
        etAgtEmail = findViewById(R.id.etAgtEmail);
        etAgtPosition = findViewById(R.id.etAgtPosition);
        etPassword = findViewById(R.id.etPassword);

        spinnerAgencies = findViewById(R.id.spinnerAgencies);

        // buttons
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        spinnerAgencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Agency agency = (Agency) spinnerAgencies.getSelectedItem();
                // Toast.makeText(AddAgentActivity.this, String.valueOf(agency.getAgencyId()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        // save button click listener
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(validateAgt()==true){
                Agency agency = (Agency) spinnerAgencies.getSelectedItem();

                AgentDB.UpdateAgent(null,
                        etAgtFirstName.getText().toString(),
                        etAgtMiddleInitial.getText().toString(),
                        etAgtLastName.getText().toString(),
                        etAgtBusPhone.getText().toString(),
                        etAgtEmail.getText().toString(),
                        etAgtPosition.getText().toString(),
                        String.valueOf(agency.getAgencyId()),
                        etPassword.getText().toString(),
                        "sait_oosd_2019_updateSecret",
                        DBHelper.apiURL() + "/api/agent_add.php",
                        AddAgentActivity.this);
                Toast.makeText(AddAgentActivity.this, "New Agent Added", Toast.LENGTH_LONG).show();
                Intent savedIntent = new Intent(AddAgentActivity.this, AgentListActivity.class);
                AddAgentActivity.this.startActivity(savedIntent);
            }
        }});

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent savedIntent = new Intent(AddAgentActivity.this, AgentListActivity.class);
                AddAgentActivity.this.startActivity(savedIntent);
            }
        });

        AgencyDB.GetAgencyDataDropdown(DBHelper.apiURL() + "/api/agency_dropdown.php", this, spinnerAgencies);
    }

    public boolean validateAgt(){
        String alpha = etAgtFirstName.getText().toString();
        if (!Validation.isValidAlpha(alpha)) {
            etAgtFirstName.setError(getString(R.string.Alpha));
            return false;
        }
        alpha = etAgtMiddleInitial.getText().toString();
        if (!Validation.isChar(alpha)) {
            etAgtMiddleInitial.setError(getString(R.string.Alpha));
            return false;
        }
        alpha = etAgtLastName.getText().toString();
        if (!Validation.isValidAlpha(alpha)) {
            etAgtLastName.setError(getString(R.string.Alpha));
            return false;
        }
        String phone = etAgtBusPhone.getText().toString();
        if (!Validation.isValidPhoneNum(phone)) {
            etAgtBusPhone.setError(getString(R.string.Phone));
            return false;
        }
        String email = etAgtEmail.getText().toString();
        if (!Validation.isValidEmail(email)) {
            etAgtEmail.setError(getString(R.string.Email));
            return false;
        }
        alpha = etAgtPosition.getText().toString();
        if (!Validation.isValidAlpha(alpha)) {
            etAgtPosition.setError(getString(R.string.Alpha));
            return false;
        } else {
            return true;

        }
    }
}
