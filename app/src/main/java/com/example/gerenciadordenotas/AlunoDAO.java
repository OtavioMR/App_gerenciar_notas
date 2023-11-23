package com.example.gerenciadordenotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
public class AlunoDAO extends SQLiteOpenHelper {
    private static String database = "escola";
    private static int versao = 1;
    AlunoDAO(Context c){
        super(c, database, null, versao);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table aluno("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT, "
                + " nota REAL); ";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "drop table if exists aluno";
        db.execSQL(sql);
        this.onCreate(db);
    }
    // salvar
    public void salvar(Aluno a) {
        ContentValues v = new ContentValues();
        v.put("nome", a.getNome());
        v.put("nota", a.getNota());
        getWritableDatabase().insert("Aluno", null, v);
    }
    // listar
    public List<Aluno> listaAlunos() {
        List<Aluno> alunos = new ArrayList<Aluno>();
        String query = "SELECT * FROM ALUNO";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Aluno a = new Aluno();
                a.setId(Integer.parseInt(c.getString(0)));
                a.setNome(c.getString(1));
                a.setNota(Double.valueOf(c.getString(2)));
                alunos.add(a);
            } while (c.moveToNext());
        }
        return alunos;
    }
// alterar

    public void alterar(Aluno a) {
        ContentValues v = new ContentValues();
        v.put("nome", a.getNome());
        v.put("nota", a.getNota());
        v.put("id", a.getId());
        getWritableDatabase().update("Aluno", v, "id=" + a.getId(),
                null);
    }
    // deletar
    public void deletar(Aluno a) {
        this.getWritableDatabase().delete("Aluno", "id=" + a.getId(),
                null);
    }
}
