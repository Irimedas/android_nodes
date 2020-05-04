package com.irimedas.notifyme.firebase;

import com.google.firebase.firestore.DocumentSnapshot;

public class Container {

    private DocumentSnapshot document;

    public Container(DocumentSnapshot document) {
        this.document = document;
    }

    public DocumentSnapshot getDocument() {
        return document;
    }

    public void setDocument(DocumentSnapshot document) {
        this.document = document;
    }
}
