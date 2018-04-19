package com.example.aula7.tallerinterneti028114.Parser;

import com.example.aula7.tallerinterneti028114.Models.Persons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonPersons {

    public static List<Persons> getData(String content) throws JSONException {

        JSONArray jsonArray = new JSONArray(content);

        List<Persons> personsList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject item = jsonArray.getJSONObject(i);

            Persons persons = new Persons();
            persons.setNombre(item.getString("nombre"));
            persons.setCodigo(item.getString("codigo"));
            persons.setEdad(item.getString("edad"));
            persons.setCorreo(item.getString("correo"));
            persons.setPass(item.getString("pass"));

            personsList.add(persons);

        }

        return personsList;
    }
}

