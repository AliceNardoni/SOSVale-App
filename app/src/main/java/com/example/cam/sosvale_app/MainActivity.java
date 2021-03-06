package com.example.cam.sosvale_app;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cam.sosvale_app.model.Model;
import com.example.cam.sosvale_app.model.Post;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = new Connection();

        // Pega todos os posts do webservice
        JSONArray jsonArrayPosts = connection.sendGetRequest("/search/post/ApprovedPosts");

        Model model = new Model(connection, jsonArrayPosts/*, jsonArrayUsers*/);
        List<Post> allApprovedPosts = model.getAllApprovedPosts();

        preenchePosts(allApprovedPosts);
    }

    public void preenchePosts(List<Post> allApprovedPosts) {

        // Se a lista não for nula ou o tamanho não for 0, itera sobre a lista adicionando à tela
        if (allApprovedPosts != null && allApprovedPosts.size() > 0) {

            LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);

            ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);


            for (Post p : allApprovedPosts) {

                // Criando novo layout para cada post
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(LLParams);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                // Definindo atributos do post
                TextView titleTextView = new TextView(this);
                titleTextView.setText("Titulo: " +  p.getTitle());
                linearLayout.addView(titleTextView);

                TextView descriptionTextView = new TextView(this);
                descriptionTextView.setText("Descricao: " + p.getDescription());
                linearLayout.addView(descriptionTextView);

                TextView postTypeTextView = new TextView(this);
                postTypeTextView.setText("Categoria: " + p.getPostType());
                linearLayout.addView(postTypeTextView);

                TextView usernameTextView = new TextView(this);
                usernameTextView.setText("Usuário: " + p.getUsername());
                linearLayout.addView(usernameTextView);

                LinearLayout line = new LinearLayout(this);
                line.setMinimumHeight(1);
                line.setBackgroundColor(Color.BLACK);

                mainLinearLayout.addView(linearLayout);
                mainLinearLayout.addView(line);
            }
        }
    }


}
