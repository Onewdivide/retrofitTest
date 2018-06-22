package com.example.onewdivideslaptop.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onewdivideslaptop.retrofittest.responseModel.agendaForClientResponse;
import com.example.onewdivideslaptop.retrofittest.responseModel.checkAuthorityForVoteAgendaResponse;
import com.example.onewdivideslaptop.retrofittest.responseModel.checkAuthorityForVoteAllResponse;
import com.example.onewdivideslaptop.retrofittest.responseModel.getNameResponse;
import com.example.onewdivideslaptop.retrofittest.responseModel.voteAllResponse;
import com.example.onewdivideslaptop.retrofittest.responseModel.voteResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public Button getNameAct
            ,checkAuthorityForVoteAllButton
            ,voteAllButton
            ,getAgendaButton
            ,getEachAgenda
            ,checkVoteButton
            ,voteButton;
    public Retrofit.Builder builder;
    public Retrofit retrofit;
    public shareHolderClient client;
    public int delegate_Id,agendaId;
    public String resultForResponse;
    public String baseURL,voteChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        delegate_Id = 1;
        agendaId = 1;
        baseURL = "http://192.168.137.1:3137/";


        builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

        client = retrofit.create(shareHolderClient.class);

        getNameAct = (Button) findViewById(R.id.getName);
        checkAuthorityForVoteAllButton = (Button) findViewById(R.id.checkAuthorityForVoteAll);
        voteAllButton = (Button) findViewById(R.id.voteAllButton);
        getAgendaButton = (Button) findViewById(R.id.getAgendaButton);
        getEachAgenda = (Button) findViewById(R.id.getEachAgendaButton);
        checkVoteButton = (Button) findViewById(R.id.checkVoteButton);
        voteButton = (Button) findViewById(R.id.voteButton);

        getNameAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //triggered /getName/:delegate_ID
                Call<List<getNameResponse>> call = client.getName(delegate_Id);

                Log.e("Dubug","Before call.enqueue");

                call.enqueue(new Callback<List<getNameResponse>>() {
                    @Override
                    public void onResponse(Call<List<getNameResponse>> call, Response<List<getNameResponse>> response) {
                        Toast.makeText(MainActivity.this
                                ,"This is name "+response.body().get(0).getDelegate_nameth()+" "
                                +response.body().get(0).getDelegate_surnameth()+"\n"
                                +response.body().get(0).getDelegate_nameeng()+" "
                                +response.body().get(0).getDelegate_surnameeng()+"\n"
                                +"this is ID : "+ response.body().get(0).getDelegate_id(),Toast.LENGTH_LONG).show();
                        Log.e("result","success");
                    }

                    @Override
                    public void onFailure(Call<List<getNameResponse>> call, Throwable t) {
                        Log.e("fail",t.toString());
                        Toast.makeText(MainActivity.this,"Something Wrong Guys :(",Toast.LENGTH_LONG).show();
                    }
                }
                );
                Log.e("Debug","After call.enqueue");

            }
        });

        checkAuthorityForVoteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //triggered /checkAuthorityForVoteAll/delegate_Id

                Call<List<checkAuthorityForVoteAllResponse>> call = client.checkAuthorityForVoteAll(delegate_Id);

                call.enqueue(new Callback<List<checkAuthorityForVoteAllResponse>>() {
                    @Override
                    public void onResponse(Call<List<checkAuthorityForVoteAllResponse>> call, Response<List<checkAuthorityForVoteAllResponse>> response) {
                        resultForResponse = "";
                        for (int i=0; i< response.body().size(); i++)
                            resultForResponse+=
                                    "holder.id : "+ response.body().get(i).getId()
                                    +"\nholderTitleTh : "+ response.body().get(i).getHolder_titleth()
                                    +"\nholderNameTh : "+ response.body().get(i).getHolder_nameth()
                                    +"\nholderSurnameTh : "+ response.body().get(i).getHolder_surnameth()
                                    +"\nholderTitleEng : "+ response.body().get(i).getHolder_titleeng()
                                    +"\nholderNameEng : "+ response.body().get(i).getHolder_nameeng()
                                    +"\nholderSurnameEng : "+ response.body().get(i).getHolder_surnameeng()
                                    +"\ncount : "+ response.body().get(i).getCount()
                                    +"\ncheckAuthorityForVoteAll : "+ response.body().get(i).getCheckAuthorityForVoteAll()+"\n\n";

                        Toast.makeText(MainActivity.this,resultForResponse,Toast.LENGTH_LONG).show();

                    }


                    @Override
                    public void onFailure(Call<List<checkAuthorityForVoteAllResponse>> call, Throwable t) {

                    }
                });

            }
        });

        voteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voteAllResponse voteAllResponseObject1 = new voteAllResponse("7777");
                voteAllResponse voteAllResponseObject2 = new voteAllResponse("9999");

                List<voteAllResponse> voteAllResponseArrayList = new ArrayList<voteAllResponse>();
                voteAllResponseArrayList.add(voteAllResponseObject1);
                voteAllResponseArrayList.add(voteAllResponseObject2);

                voteChoice = "NOCOMMENT";

                Call<String> call = client.voteAll(voteChoice,voteAllResponseArrayList);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(MainActivity.this,"VoteAll SUCCESS!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"We have some problem guys :(",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        getAgendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultForResponse = "";

                Call<List<agendaForClientResponse>> call = client.getAllagenda();

                call.enqueue(new Callback<List<agendaForClientResponse>>() {
                    @Override
                    public void onResponse(Call<List<agendaForClientResponse>> call, Response<List<agendaForClientResponse>> response) {
                        for(int i=0; i<response.body().size(); i++){
                            resultForResponse+="Agenda id : "+response.body().get(i).getId()
                                    +"\nAgenda No : "+ response.body().get(i).getAgenda_no()
                                    +"\ndetail : "+response.body().get(i).getDetail()
                                    +"\nfull detail : "+response.body().get(i).getFull_title()+"\n\n";
                        }

                        Toast.makeText(MainActivity.this,resultForResponse,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<List<agendaForClientResponse>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"we have problem!! :(",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        getEachAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultForResponse = "";

                Call<List<agendaForClientResponse>> call = client.getAgenda(1);

                call.enqueue(new Callback<List<agendaForClientResponse>>() {
                    @Override
                    public void onResponse(Call<List<agendaForClientResponse>> call, Response<List<agendaForClientResponse>> response) {
                        resultForResponse+="Agenda id : "+response.body().get(0).getId()
                                +"\nAgenda No : "+ response.body().get(0).getAgenda_no()
                                +"\ndetail : "+response.body().get(0).getDetail()
                                +"\nfull detail : "+response.body().get(0).getFull_title()+"\n\n";

                        Toast.makeText(MainActivity.this,resultForResponse,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<List<agendaForClientResponse>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"we have problem!! :(",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        checkVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<checkAuthorityForVoteAgendaResponse>> call = client.checkAuthorityForVote(agendaId,delegate_Id);

                call.enqueue(new Callback<List<checkAuthorityForVoteAgendaResponse>>() {
                    @Override
                    public void onResponse(Call<List<checkAuthorityForVoteAgendaResponse>> call, Response<List<checkAuthorityForVoteAgendaResponse>> response) {
                        resultForResponse = "";
                        for (int i=0; i< response.body().size(); i++){
                            resultForResponse+="id : "+response.body().get(i).getId()
                            +"\nholder_titleth : "+ response.body().get(i).getHolder_titleth()
                            +"\nholder_nameth : "+ response.body().get(i).getHolder_nameth()
                            +"\nholder_surname : "+ response.body().get(i).getHolder_surnameth()
                            +"\nholder_titleeng : "+response.body().get(i).getHolder_titleeng()
                            +"\nholder_nameeng : "+ response.body().get(i).getHolder_nameeng()
                            +"\nholder_surnameeng : "+response.body().get(i).getHolder_surnameeng()
                            +"\ncheckauthorityforthisagenda"+response.body().get(i).getCheckauthorityforthisagenda()
                            +"\nvotealready : "+ response.body().get(i).getVotealready()+"\n\n";
                        }
                        Toast.makeText(MainActivity.this,resultForResponse,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<List<checkAuthorityForVoteAgendaResponse>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"We have some problem :(",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voteChoice = "YES";
                agendaId = 2;

                voteResponse voteResponseBody1 = new voteResponse("1111");
                voteResponse voteResponseBody2 = new voteResponse("2222");

                List<voteResponse> listVoteResponse = new ArrayList<voteResponse>();
                listVoteResponse.add(voteResponseBody1);
                listVoteResponse.add(voteResponseBody2);

                Call<String> call = client.vote(voteChoice,agendaId,listVoteResponse);
                Log.e("Debug","before enqueue");
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(MainActivity.this,"SUCESS!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"We have problem :(",Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("Debug","after enqueue");
            }
        });


    }
}
