package com.example.gerenciadordenotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    AlunoDAO db = new AlunoDAO(this);
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nome = (EditText) findViewById(R.id.editText1);
        final RatingBar bar = (RatingBar)
                findViewById(R.id.ratingBar);
        final Button b1 = (Button) findViewById(R.id.button);
        final Button b2 = (Button) findViewById(R.id.button2);
        final Button b3 = (Button) findViewById(R.id.button3);
        final Button b4 = (Button) findViewById(R.id.button4);
        lista = (ListView) findViewById(R.id.listView1);
// Botão Cadastrar
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno a = new Aluno();
                a.setNome(nome.getText().toString());
                a.setNota(Double.valueOf(bar.getRating()));
                db = new AlunoDAO(MainActivity.this);
                db.salvar(a);
                Toast.makeText(MainActivity.this, "Gravado",
                                Toast.LENGTH_SHORT)
                        .show();
                listarAlunos();
            }
        });
//Botão Listar
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarAlunos();
            }
        });

        // botão alterar
        b3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub
                Aluno a = new Aluno();
                a.setId(Integer.parseInt(id));
                a.setNome(nome.getText().toString());
                a.setNota(Double.valueOf(bar.getRating()));
                db = new AlunoDAO(MainActivity.this);
                db.alterar(a);
                listarAlunos();
                Toast.makeText(MainActivity.this, "Alterado",
                                Toast.LENGTH_SHORT)
                        .show();
                listarAlunos();
            }
        });

        // botão deletar
        b4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub
                Aluno a = new Aluno();
                a.setId(Integer.parseInt(id));
                db = new AlunoDAO(MainActivity.this);
                db.deletar(a);
                listarAlunos();
                Toast.makeText(MainActivity.this, "Excluído",
                                Toast.LENGTH_SHORT)
                        .show();
                listarAlunos();
            }
        });

        // método para clicar e retornar os valores para os campos
        lista.setOnItemClickListener(new
                                             AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> arg0, View arg1,
                                                                         int position, long arg3) {
                                                     String conteudo = (String)
                                                             lista.getItemAtPosition(position);
                                                     String palavra[] = conteudo.split(" - ");
                                                     id = palavra[0];
                                                     nome.setText(palavra[1]);
                                                     bar.setRating(Float.valueOf(palavra[2]));
                                                 }
                                             });
    }

    //Fora do onCreate
    public void listarAlunos() {
        List<Aluno> alunos = db.listaAlunos();
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, arrayList);
        lista.setAdapter(adapter);
        for (Aluno a : alunos) {

            arrayList.add(a.getId() + " - " + a.getNome() + " - " +
                    a.getNota());
            adapter.notifyDataSetChanged();
        }
    }
}


