package com.example.novigradv2.classes;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {

    private String id;
    private String title;
    private String price;
    private List<String> textFields;
    private List<String> numericalFields;
    private List<String> documentFields;

    public Service(String id, String title, String price, List<String> textFields, List<String> numericalFields, List<String> documentFields) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.textFields = textFields;
        this.numericalFields = numericalFields;
        this.documentFields = documentFields;
    }

    public Service() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getTextFields() {
        return textFields;
    }

    public void setTextFields(List<String> textFields) {
        this.textFields = textFields;
    }

    public List<String> getNumericalFields() {
        return numericalFields;
    }

    public void setNumericalFields(List<String> numericalFields) {
        this.numericalFields = numericalFields;
    }

    public List<String> getDocumentFields() {
        return documentFields;
    }

    public void setDocumentFields(List<String> documentFields) {
        this.documentFields = documentFields;
    }
}
