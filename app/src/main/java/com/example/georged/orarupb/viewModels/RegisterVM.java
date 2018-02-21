package com.example.georged.orarupb.viewModels;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.georged.orarupb.R;
import com.example.georged.orarupb.RegisterActivity;
import com.example.georged.orarupb.webApiClient.models.Grupa;
import com.example.georged.orarupb.webApiClient.models.Student;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by George D on 23-Jun-17.
 */

public class RegisterVM {
    private Validator validator;

    private ArrayList<Grupa> grupe;

    private Spinner anSpinner;
    private Spinner numarSpinner;

    private ArrayAdapter<String> anAdapter;
    private ArrayAdapter<String> numarAdapter;

    private final ArrayList<String> ani = new ArrayList<>();
    private final ArrayList<String> numere = new ArrayList<>();

    private final HashMap<Integer, ArrayList<String>> grupeHashMap = new HashMap<>();

    @NotEmpty
    @Email
    private EditText email;

    @NotEmpty
    private EditText nume;

    @NotEmpty
    private EditText prenume;

    @NotEmpty
    private EditText parola;

    private EditText telefon;
    private Button register;

    public RegisterVM(Activity activity, final ArrayList<Grupa> grupe) {
        validator = new Validator(this);

        this.grupe = grupe;

        anSpinner = (Spinner) activity.findViewById(R.id.spinnerAn);
        numarSpinner = (Spinner) activity.findViewById(R.id.spinnerGrupa);

        anAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, ani);
        numarAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, numere);

        anSpinner.setAdapter(anAdapter);
        numarSpinner.setAdapter(numarAdapter);

        email = (EditText) activity.findViewById(R.id.etEmail);
        nume = (EditText) activity.findViewById(R.id.etNume);
        prenume = (EditText) activity.findViewById(R.id.etPrenume);
        telefon = (EditText) activity.findViewById(R.id.etTelefon);
        parola = (EditText) activity.findViewById(R.id.etParola);

        register = (Button) activity.findViewById(R.id.btnSaveInfo);

        anSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedAn = Integer.parseInt(ani.get(i));
                setNumere(selectedAn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setNumere(new ArrayList<String>());
            }
        });

        setGrupeHashMap(grupe);
        setAni(getAni());
    }

    private void setGrupeHashMap(ArrayList<Grupa> grupeHashMap) {
        for (Grupa grupa : grupeHashMap) {
            if(this.grupeHashMap.containsKey(grupa.an)) {
                this.grupeHashMap.get(grupa.an).add(grupa.numar);
            }
            else {
                ArrayList<String> numere = new ArrayList<>();
                numere.add(grupa.numar);
                this.grupeHashMap.put(grupa.an, numere);
            }
        }
    }

    private ArrayList<String> getAni() {
        ArrayList<String> ani = new ArrayList<>();
        for (Integer an : grupeHashMap.keySet()) {
            ani.add(an.toString());
        }
        return ani;
    }
    private void setAni(ArrayList<String> ani) {
        Collections.sort(ani);

        this.ani.clear();
        this.ani.addAll(ani);
        this.anAdapter.notifyDataSetChanged();
    }

    private void setNumere(int an)  {
        setNumere(grupeHashMap.get(an));
    }
    private void setNumere(ArrayList<String> numere) {
        this.numere.clear();
        this.numere.addAll(numere);
        this.numarAdapter.notifyDataSetChanged();
    }

    public Grupa getGrupa() {
        for (Grupa grupa : grupe) {
            if (grupa.an.toString().equals(anSpinner.getSelectedItem()) && grupa.numar.equals(numarSpinner.getSelectedItem())) {
                return grupa;
            }
        }
        return null;
    }

    private String getNume() {
        return nume.getText().toString();
    }

    private String getPrenume() {
        return prenume.getText().toString();
    }

    private String getEmail() {
        return email.getText().toString();
    }

    private String getTelefon() {
        return telefon.getText().toString();
    }

    public void setBtnSave() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public String getParola() {
        return parola.getText().toString();
    }

    public Student toEntity() {
        Student student = new Student();
        student.nume = this.getNume();
        student.prenume = this.getPrenume();
        student.email = this.getEmail();
        student.telefon = this.getTelefon();
        student.groupId = this.getGrupa().grupaId;
        student.password = this.getParola();
        student.groupNumber = this.getGrupa().numar;

        return student;
    }

    public Student toEntity(int id) {
        Student student = this.toEntity();
        student.id = id;

        return student;
    }

    public void setValidator(RegisterActivity value) {
        validator.setValidationListener(value);
    }
}
