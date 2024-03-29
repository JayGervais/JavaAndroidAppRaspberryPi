package com.example.day10_assignment_v1.agent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.day10_assignment_v1.DBHelper;
import com.example.day10_assignment_v1.MainActivity;
import com.example.day10_assignment_v1.R;
import com.example.day10_assignment_v1.login.LoginActivity;
import com.example.day10_assignment_v1.booking.BookingListActivity;
import com.example.day10_assignment_v1.customer.CustomerListActivity;
import com.example.day10_assignment_v1.login.Session;
import com.example.day10_assignment_v1.product.ProductListActivity;

public class AgentListActivity extends AppCompatActivity
{
    ListView listAgents;
    Button btnAddNewAgent;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        btnAddNewAgent = findViewById(R.id.btnAddNewAgent);
        listAgents = findViewById(R.id.listAgents);

        // check session
        session = new Session(this);
        if (session.getsessionid() == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
        else
        {
            session.getsessionid();
            if (session.getsessionrole().equals("admin"))
            {
                btnAddNewAgent.setVisibility(View.VISIBLE);
            } else {
                btnAddNewAgent.setVisibility(View.INVISIBLE);
            }
        }

        listAgents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), AgentDetailActivity.class);
                intent.putExtra("agent", (Agent) listAgents.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        btnAddNewAgent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addAgentIntent = new Intent(AgentListActivity.this, AddAgentActivity.class);
                AgentListActivity.this.startActivity(addAgentIntent);
            }
        });

        AgentDB.GetAgentListData(DBHelper.apiURL() + "/api/agent_data.php", this, listAgents);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menu_agents:
                startActivity(new Intent(this, AgentListActivity.class));
                return true;
            case R.id.menu_bookings:
                startActivity(new Intent(this, BookingListActivity.class));
                return true;
            case R.id.menu_customers:
                startActivity(new Intent(this, CustomerListActivity.class));
                return true;
            case R.id.menu_products:
                startActivity(new Intent(this, ProductListActivity.class));
                return true;
            case R.id.menu_logout:
                session.setsessionid(null);
                startActivity(new Intent(this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}